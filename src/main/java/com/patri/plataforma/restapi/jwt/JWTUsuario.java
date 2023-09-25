package com.patri.plataforma.restapi.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JWTUsuario implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String nome;
	private Long id;
	private String grupo;
	private List<String> roles;
	private TipoPermissao tipo;
	
	public JWTUsuario(Usuario usuario)
	{
		this.login = usuario.getLogin();
		this.password = usuario.getSenha();
		this.nome = usuario.getNome();
		this.id = usuario.getId();
		this.roles = usuario.getPermissoes();
		this.tipo = usuario.getTipoPermissaoAcesso();
		this.grupo = usuario.getGrupoUsuario();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		roles.forEach(r -> authorities.add(new JWTRoleUsuario(r, tipo)));
		
		return authorities;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getUsername()
	{
		return login;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
