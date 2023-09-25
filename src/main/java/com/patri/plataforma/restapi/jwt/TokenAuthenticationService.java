package com.patri.plataforma.restapi.jwt;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenAuthenticationService 
{
	@Value(value = "${security.expiration}")
	private long EXPIRATION_TIME;
	
	@Value(value = "${security.secret}")
	private String SECRET;
	
	@Value(value = "${security.refresh.expiration}")
	private long REFRESH_EXPIRATION_TIME;
	
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	private static final String HEADER_REFRESH = "Refresh";
	public static final Long VALIDADE_TOKEN_CADASTRO_SENHA = 86400000L;
	public static final Long VALIDADE_TOKEN_RESET_SENHA = 28800000L;
	
	public void login(HttpServletResponse response, JWTUsuario usuario)
	{
		String JWT = generateToken(usuario, EXPIRATION_TIME);
		String JWT_REFRESH = generateToken(usuario, REFRESH_EXPIRATION_TIME);
		HashMap<String, Object> map = new HashMap<String, Object>();

		response.setContentType("application/json");
		atualizarHeader(response, JWT, JWT_REFRESH);
		try
		{
			map.put("name", usuario.getNome());
			map.put("grupo", usuario.getGrupo());
			Gson gson = new Gson();
			String json = gson.toJson(map);
			response.getWriter().printf(json);
		} catch (IOException e)
		{
			log.error("ERRO PARSE JSON {}", e);
		}
	}
	
//	public void addAuthentication(HttpServletResponse response, JWTUsuario usuario)
//	{
//		String JWT = generateToken(usuario, EXPIRATION_TIME);
//		String JWT_REFRESH = generateToken(usuario, REFRESH_EXPIRATION_TIME);
//
//		try
//		{
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("name", usuario.getNome());
//			map.put("grupo", usuario.getGrupo());
//			map.put(HEADER_STRING, JWT);
//			map.put(HEADER_REFRESH, JWT_REFRESH);
//			Gson gson = new Gson();
//			String json = gson.toJson(map);
//			response.getWriter().printf(json);
//		} catch (IOException e)
//		{
//			log.error("ERRO PARSE JSON {}", e);
//		}
//		
//		response.setContentType("application/json");
//		response.addHeader(HEADER_STRING, JWT);
//		response.addHeader(HEADER_REFRESH, JWT_REFRESH);
////		response.addCookie(getCookie(JWT_REFRESH));
//	}
	
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws JWTAutenticationException
	{
		boolean refreshToken = isRefreshToken(request);
		String token = request.getHeader(HEADER_STRING);
		Jws<Claims> claim = validateToken(token, refreshToken);

                if (claim == null)
                    return null;

		if(refreshToken)
		{
			return authentication(response, claim);
		}
		
                
		return authentication(claim);
	}
	
	public String generateToken(Usuario usuario, Long expirationTime)
	{
		JWTUsuario jwtUsuario = JWTUsuario.builder()
		.id(usuario.getId())
		.login(usuario.getLogin())
		.nome(usuario.getNome())
		.tipo(usuario.getTipoPermissaoAcesso())
		.build();
		return generateToken(jwtUsuario, expirationTime);
	}
	
	public Jws<Claims> validateToken(String token, boolean isRefresh)
	{
		if(StringUtils.isBlank(token))
		{
			return null;
		}
		
		try
		{
			return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
		}catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
			throw new JWTAutenticationException(MensagensID.INVALID_ACCESS_TOKEN, isRefresh ? HttpStatus.FORBIDDEN : HttpStatus.UNAUTHORIZED);
		}
	}
	
	private void atualizarHeader(HttpServletResponse response, String JWT, String REFRESH)
	{
		response.addHeader(HEADER_STRING, JWT);
		response.addHeader(HEADER_REFRESH, REFRESH);
	}

	private String generateToken(JWTUsuario usuario, long expirationTime)
	{
		String JWT = Jwts.builder().setSubject(usuario.getUsername())
				.setIssuedAt(Calendar.getInstance().getTime())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.claim("usuario", usuario.getNome())
				.claim("id", UtilSecurity.encryptId(usuario.getId()))
				.claim("roles", usuario.getRoles())
				.claim("tipo", usuario.getTipo())
				.claim("grupo", usuario.getGrupo())
				.compact();
		return JWT;
	}
	
	private Authentication authentication(HttpServletResponse response, Jws<Claims> claim)
	{
		JWTUsuario usuario = getUser(claim);
		String JWT = generateToken(usuario, EXPIRATION_TIME);
		String JWTREFRESH = generateToken(usuario, REFRESH_EXPIRATION_TIME);
		atualizarHeader(response, JWT, JWTREFRESH);

		return new JWTAuthentication(usuario);
	}

	private Authentication authentication(Jws<Claims> claim)
	{
		JWTUsuario usuario = getUser(claim);
		return new JWTAuthentication(usuario);
	}
	
	@SuppressWarnings("unchecked")
	private JWTUsuario getUser(Jws<Claims> claim)
	{
		String login = claim.getBody().getSubject();
		Long id = ((Long) UtilSecurity.decryptId((String)claim.getBody().get("id")));
		String nome = (String) claim.getBody().get("usuario");
		List<String> roles = (List<String>) claim.getBody().get("roles");
		TipoPermissao tipo = TipoPermissao.valueOf((String) claim.getBody().get("tipo"));
		String grupo = (String) claim.getBody().get("grupo");
		
		JWTUsuario usuario =JWTUsuario.builder()
				.id(id)
				.login(login)
				.nome(nome)
				.roles(roles)
				.tipo(tipo)
				.grupo(grupo)
				.build();
		
		return usuario;
	}
	
	private boolean isRefreshToken(HttpServletRequest request)
	{
		return StringUtils.endsWith(request.getRequestURI(), Constantes.REFRESH_TOKEN);
	}

	private Cookie getCookie(String refreshToken)
	{
		Cookie cookie = new Cookie(HEADER_REFRESH, refreshToken);
		cookie.setHttpOnly(false);
		cookie.setSecure(false);
		cookie.setPath(refreshToken);
		cookie.setMaxAge(-1); //FIXME: MUDAR ATRIBUTO PARA O ARQUIVO DE PROPRIEDADES
		return cookie;
	}
}
