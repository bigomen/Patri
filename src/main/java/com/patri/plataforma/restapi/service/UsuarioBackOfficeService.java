package com.patri.plataforma.restapi.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.patri.plataforma.restapi.mapper.UsuarioBackOfficeMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.jwt.TokenAuthenticationService;
import com.patri.plataforma.restapi.model.UsuarioBackOffice;
import com.patri.plataforma.restapi.repository.UsuarioBackOfficeRepository;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import com.patri.plataforma.restapi.utility.EnviaEmail;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UsuarioBackOfficeService extends com.patri.plataforma.restapi.service.Service<UsuarioBackOffice, RestUsuarioBackOffice>
{
    private final UsuarioBackOfficeRepository usuarioBackOfficeRepository;
    private final EnviaEmail enviaEmail;
    private final PasswordEncoder passwordEncoder;
    private final TokenAuthenticationService tks;
    
    @Value(value = "${api.frontend.url}")
    private String URL_FRONT;

    @Autowired
    public UsuarioBackOfficeService(UsuarioBackOfficeRepository usuarioBackOfficeRepository,
                                    EnviaEmail enviaEmail,
                                    PasswordEncoder passwordEncoder, TokenAuthenticationService tks)
    {
        super();
        this.usuarioBackOfficeRepository = usuarioBackOfficeRepository;
        this.enviaEmail = enviaEmail;
        this.passwordEncoder = passwordEncoder;
		this.tks = tks;
    }

    public Collection<RestUsuarioBackOffice> listar()
    {
        Collection<RestUsuarioBackOffice> allRest = new ArrayList<>();
        Iterable<UsuarioBackOffice> usuariosBackOffices = getRepository().findAll();
        usuariosBackOffices.forEach(u -> allRest.add(UsuarioBackOfficeMapper.INSTANCE.convertToSimpleRest(u)));
        return allRest;
    }

    public Collection<RestUsuarioBackOffice> pesquisar(String param)
    {
        Collection<UsuarioBackOffice> usuarios = new HashSet<>();
        Collection<RestUsuarioBackOffice> restUsuarios = new HashSet<>();

        try
        {
            Long newId = UtilSecurity.decryptId(param);
            Optional<UsuarioBackOffice> byId = usuarioBackOfficeRepository.findById(newId);
            usuarios.add(byId.orElseThrow(() -> new ObjectNotFoundException(funcionalidade())));
        } catch (Exception e)
        {
            usuarios = usuarioBackOfficeRepository.listaUsuario(param);
        }

        usuarios.forEach(a -> restUsuarios.add(a.modelParaRest()));

        return restUsuarios;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestUsuarioBackOffice restUsuarioBackOffice)
    {
    	UsuarioBackOffice usuarioBackOffice = restUsuarioBackOffice.restParaModel();
    	usuarioBackOffice = usuarioBackOfficeRepository.save(usuarioBackOffice);
    	
    	URL url = generateURL(usuarioBackOffice, TokenAuthenticationService.VALIDADE_TOKEN_CADASTRO_SENHA);
    	
        enviaEmail.enviarEmailUsuario(usuarioBackOffice, url, true);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
	public void esqueceuSenha(String login)
	{
		 UsuarioBackOffice usuario = usuarioBackOfficeRepository.findByLoginAndAtivoTrue(login)
	                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
         usuario.setAtivo(false);
         usuario.setSenha(" ");
         usuarioBackOfficeRepository.save(usuario);
		 URL url = generateURL(usuario, TokenAuthenticationService.VALIDADE_TOKEN_RESET_SENHA);
		 enviaEmail.enviarEmailUsuario(usuario, url, false);
	}

    public RestUsuarioBackOffice pesquisarPorId(String id)
    {
        return super.getById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void alterar(String id, RestUsuarioBackOffice updatedUsuarioBackOffice)
    {
        UsuarioBackOffice usuario = getRepository().findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

        updatedUsuarioBackOffice.setId(id);

        UsuarioBackOffice usuarioBackOffice = updatedUsuarioBackOffice.restParaModel();
        usuarioBackOffice.setId(usuario.getId());
        usuarioBackOffice.setSenha(usuario.getSenha());
        usuarioBackOffice.setAtivo(usuario.getAtivo());

        getRepository().save(usuarioBackOffice);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void deletar(String id)
    {
        super.delete(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void alterar(String id, Boolean status)
    {
        UsuarioBackOffice usuario = getRepository().findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

        usuario.setAtivo(status);

        getRepository().save(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novaSenha(String id, String token, String novaSenha)
    {
    	tks.validateToken(token, false);
    	
    	if(!novaSenha.matches(Constantes.PATTERN_SENHA))
    	{
    		throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.INVALID_PASSWORD);
    	}
    	
    	UsuarioBackOffice usuario = usuarioBackOfficeRepository.findByIdAndAtivoFalse(UtilSecurity.decryptId(id))
                 .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
    	
    	
        usuario.setAtivo(true);
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioBackOfficeRepository.save(usuario);
    }

    @Override
    protected CrudRepository<UsuarioBackOffice, Long> getRepository()
    {
        return usuarioBackOfficeRepository;
    }
    
    private URL generateURL(UsuarioBackOffice usuario, Long expires)
    {
    	String token = tks.generateToken(usuario, expires);
    	
    	try
		{
			URIBuilder builder = new URIBuilder(URL_FRONT.concat("nova-senha"));
			builder.addParameter("id", UtilSecurity.encryptId(usuario.getId()));
			builder.addParameter("tipo", usuario.getTipoPermissaoAcesso().name());
			builder.addParameter("token", token);
			
			return builder.build().toURL();
		} catch (URISyntaxException | MalformedURLException e1)
		{
			return null;
		}
    }

	@Override
	protected String funcionalidade()
	{
		return "Usuário Back Office";
	}
    
    
}
