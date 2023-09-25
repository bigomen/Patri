
package com.patri.plataforma.restapi.jwt;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import lombok.Getter;

/**
 *
 * @author rcerqueira
 */
@Getter
public class JWTAuthentication extends UsernamePasswordAuthenticationToken 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long idUsuario;
	private final TipoPermissao tipo;
    private String grupo;

//    public JWTAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long idUsuario, TipoPermissao tipo)
//    {
//        super(principal, credentials, authorities);
//        this.idUsuario = idUsuario;
//        this.tipo = tipo;
//    }
    
    public JWTAuthentication(JWTUsuario usuario)
    {
    	 super(usuario.getLogin(), null, usuario.getAuthorities());
    	 this.idUsuario = usuario.getId();
         this.tipo = usuario.getTipo();
         this.grupo = usuario.getGrupo();
    }
}
