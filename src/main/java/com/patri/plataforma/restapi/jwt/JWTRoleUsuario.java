
package com.patri.plataforma.restapi.jwt;

import org.springframework.security.core.GrantedAuthority;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;

/**
 *
 * @author rcerqueira
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JWTRoleUsuario implements GrantedAuthority
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Include
	private String role;
	
	public JWTRoleUsuario (String role, TipoPermissao tipo)
	{
		this.role = role + tipo.name();
	}

	@Override
	public String getAuthority()
	{
		return this.role;
	}
}
