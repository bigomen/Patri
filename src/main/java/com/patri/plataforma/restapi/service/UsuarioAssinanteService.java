package com.patri.plataforma.restapi.service;

import java.util.*;
import java.util.stream.Collectors;

import com.patri.plataforma.restapi.model.*;
import com.patri.plataforma.restapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UsuarioAssinanteService extends com.patri.plataforma.restapi.service.Service<UsuarioAssinante, RestUsuarioAssinante>
{

    private final UsuarioAssinanteRepository usuarioRepository;
    private final AssinanteRepository assinanteRepository;
    private final LocalidadeAssinanteRepository localidadeAssinanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TopicoUsuarioAssinanteRepository topicoUsuarioAssinanteRepository;

    @Autowired
    public UsuarioAssinanteService(UsuarioAssinanteRepository usuarioRepository,
                                   AssinanteRepository assinanteRepository,
                                   LocalidadeAssinanteRepository localidadeAssinanteRepository,
                                   PasswordEncoder passwordEncoder,
                                   TopicoUsuarioAssinanteRepository topicoUsuarioAssinanteRepository)
    {
        super();
        this.usuarioRepository = usuarioRepository;
        this.assinanteRepository = assinanteRepository;
        this.localidadeAssinanteRepository = localidadeAssinanteRepository;
        this.passwordEncoder = passwordEncoder;
        this.topicoUsuarioAssinanteRepository = topicoUsuarioAssinanteRepository;
    }

    public Collection<RestUsuarioAssinante> listar()
    {
        Collection<RestUsuarioAssinante> allRest = new ArrayList<>();
        Iterable<UsuarioAssinante> usuariosAssinantes = getRepository().findAll();
        usuariosAssinantes.forEach(u -> allRest.add(u.simpleModelParaRest()));
        return allRest;
    }

    public RestUsuarioAssinante obterPorId(String id)
    {
        UsuarioAssinante usuarioAssinante = usuarioRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
        return usuarioAssinante.modelParaRest();
    }

    public Collection<RestUsuarioAssinante> pesquisarPorLogin(String login)
    {
        Collection<UsuarioAssinante> usuarios = usuarioRepository.findByLoginContainingIgnoreCase(login);

        return usuarios.stream().map(UsuarioAssinante::modelParaRest).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestUsuarioAssinante restUsuario)
    {
        validarLimiteLocalidade(restUsuario);

        UsuarioAssinante usuario = usuarioRepository.save(restUsuario.restParaModel());

        Collection<LocalidadeAssinante> localidadesByAssinante = localidadeAssinanteRepository.findByAssinante(usuario.getAssinante());
        verificaLocalidadeVinculadaAoAssinante(usuario, localidadesByAssinante);

        usuario.getTopicos().forEach(t -> t.getId().setIdUsuario(usuario.getId()));

        usuarioRepository.save(usuario);
    }

    public void alterar(RestUsuarioAssinante restUsuarioRequest, String id)
    {
        UsuarioAssinante usuarioAssinante = usuarioRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(""));

        restUsuarioRequest.setId(id);

        validarLimiteLocalidade(restUsuarioRequest);

        UsuarioAssinante usuarioAssinanteResponse = restUsuarioRequest.restParaModel();
        usuarioAssinanteResponse.setId(usuarioAssinante.getId());
        usuarioAssinanteResponse.setSenha(usuarioAssinante.getSenha());

        Collection<LocalidadeAssinante> localidadesByAssinante = localidadeAssinanteRepository.findByAssinante(usuarioAssinanteResponse.getAssinante());
        verificaLocalidadeVinculadaAoAssinante(usuarioAssinanteResponse, localidadesByAssinante);

        usuarioRepository.save(usuarioAssinanteResponse);
    }

    public void novaSenha(String id, String novaSenha)
    {
        UsuarioAssinante usuarioAssinante = usuarioRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(""));

        usuarioAssinante.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuarioAssinante);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletar(String id)
    {
        UsuarioAssinante usuarioAssinante = usuarioRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.PTR024));

        Collection<TopicoUsuarioAssinante> topicosUsuariosAssinantes = topicoUsuarioAssinanteRepository.findByIdIdUsuario(usuarioAssinante.getId());
        topicoUsuarioAssinanteRepository.deleteAll(topicosUsuariosAssinantes);

        usuarioRepository.delete(usuarioAssinante);
    }

    public Collection<RestUsuarioAssinante> pesquisar(String param)
    {
        Collection<UsuarioAssinante> usuarios = new HashSet<>();

        try
        {
            Optional<UsuarioAssinante> usuarioById = usuarioRepository.findById(UtilSecurity.decryptId(param));
            if (usuarioById.isPresent())
            {
                UsuarioAssinante usuario = usuarioById.get();
                usuarios.add(usuario);
            }
        } catch (Exception e)
        {
            usuarios = usuarioRepository.findUsuarioByPesquisa(param);
        }

        return usuarios.stream().map(UsuarioAssinante::modelParaRest).collect(Collectors.toList());
    }

    public void alterar(String id, boolean status)
    {
        UsuarioAssinante usuarioAssinante = usuarioRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

        usuarioAssinante.setAtivo(status);

        getRepository().save(usuarioAssinante);
    }


    //FIXME: ajeitar validação
    private void validarLimiteLocalidade(RestUsuarioAssinante usuario)
    {
        int numeroLocalidades = 0;

        Assinante assinante = assinanteRepository.findById(UtilSecurity.decryptId(usuario.getAssinante().getId()))
                .orElseThrow(() -> new ObjectNotFoundException("Assinante"));

        if (usuario.getLocalidadesAssinantes() != null)
        {
            numeroLocalidades = usuario.getLocalidadesAssinantes().size();
        }

        if (numeroLocalidades > assinante.getPlano().getQtdLocalidades())
        {
            throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR070);
        }
    }

    private void verificaLocalidadeVinculadaAoAssinante(UsuarioAssinante usuarioAssinante, Collection<LocalidadeAssinante> localidadesByAssinante) {
        if (usuarioAssinante.getLocalidadesAssinantes() != null) {
            usuarioAssinante.getLocalidadesAssinantes().forEach(localidade -> {
                if (!possuiMunicipio(localidade, localidadesByAssinante) && !possuiUF(localidade, localidadesByAssinante)) {
                    throw new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.PTR079);
                }
            });
        }
    }

    private boolean possuiMunicipio(LocalidadeAssinante localidade, Collection<LocalidadeAssinante> localidadesByAssinante) {
        Optional<LocalidadeAssinante> municipio = localidadesByAssinante.stream()
                .filter(localidadeAssinante -> localidadeAssinante.getMunicipio() != null && localidadeAssinante.getMunicipio().equals(localidade.getMunicipio()))
                .findFirst();
        if (municipio.isPresent()) {
            localidade.setId(municipio.get().getId());
            return true;
        }
        return false;
    }

    private boolean possuiUF( LocalidadeAssinante localidade, Collection<LocalidadeAssinante> localidadesByAssinante) {
        Optional<LocalidadeAssinante> uf = localidadesByAssinante.stream()
                .filter(localidadeAssinante -> localidadeAssinante.getUf()!= null && localidadeAssinante.getUf().equals(localidade.getUf()))
                .findFirst();
        if (uf.isPresent()) {
            localidade.setId(uf.get().getId());
            return true;
        }
        return false;
    }

    @Override
    protected CrudRepository getRepository()
    {
        return usuarioRepository;
    }

	@Override
	protected String funcionalidade()
	{
		return "Usuário Assinante"; 
	}
}
