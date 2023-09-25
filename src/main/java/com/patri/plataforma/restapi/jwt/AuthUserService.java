
package com.patri.plataforma.restapi.jwt;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.repository.UsuarioAssinanteRepository;
import com.patri.plataforma.restapi.repository.UsuarioBackOfficeRepository;

@Service
public class AuthUserService implements UserDetailsService {

    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final UsuarioBackOfficeRepository usuarioBackOfficeRepository;
    
    @Autowired
    public AuthUserService(
			UsuarioAssinanteRepository usuarioAssinanteRepository,
			UsuarioBackOfficeRepository usuarioBackOfficeRepository)
	{
		super();
		this.usuarioAssinanteRepository = usuarioAssinanteRepository;
		this.usuarioBackOfficeRepository = usuarioBackOfficeRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException 
    {
		Optional<Usuario> opUsuarioBackOffice = usuarioBackOfficeRepository.findByLoginAndAtivo(login, true);
		
		Usuario usuario = opUsuarioBackOffice.orElseGet(() -> usuarioAssinanteRepository.findByLoginAndAtivo(login, true)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + login)));
		
		return new JWTUsuario(usuario);
    }
}
