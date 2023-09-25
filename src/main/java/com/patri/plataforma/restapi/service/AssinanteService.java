package com.patri.plataforma.restapi.service;

import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.mapper.AssinanteMapper;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import com.patri.plataforma.restapi.model.StatusAssinante;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import com.patri.plataforma.restapi.model.enums.TipoStatusAssinante;
import com.patri.plataforma.restapi.repository.AssinanteRepository;
import com.patri.plataforma.restapi.repository.StatusAssinanteRepository;
import com.patri.plataforma.restapi.repository.UnidadeNegocioRepository;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AssinanteService extends com.patri.plataforma.restapi.service.Service<Assinante, RestAssinante>
{
	private final AssinanteRepository assinanteRepository;
	private final StatusAssinanteRepository statusAssinanteRepository;
	private final UnidadeNegocioRepository unidadeNegocioRepository;
	private final LocalidadeService localidadeService;
	private final UsuarioAssinanteService usuarioAssinanteService;
	private final TopicoService topicoService;
	private final PlanoService planoService;

	@Autowired
	public AssinanteService(AssinanteRepository assinanteRepository,
							StatusAssinanteRepository statusAssinanteRepository,
							UnidadeNegocioRepository unidadeNegocioRepository,
							LocalidadeService localidadeService,
							UsuarioAssinanteService usuarioAssinanteService,
							TopicoService topicoService,
							PlanoService planoService)
	{
		super();
		this.assinanteRepository = assinanteRepository;
		this.statusAssinanteRepository = statusAssinanteRepository;
		this.unidadeNegocioRepository = unidadeNegocioRepository;
		this.localidadeService = localidadeService;
		this.usuarioAssinanteService = usuarioAssinanteService;
		this.topicoService = topicoService;
		this.planoService = planoService;
	}

	public Collection<RestAssinante> listar(String param)
	{
		Collection<Assinante> assinantes = assinanteRepository.findAll(param);
		
	    return assinantes.stream()
        		.map(AssinanteMapper.INSTANCE ::convertToRest)
        		.collect(Collectors.toList());
	}
	
	public Collection<RestAssinante> pesquisarAssinantesPorUnidade(String idUnidade)
	{
		Optional<UnidadeNegocio> opUnidade = unidadeNegocioRepository.findById(UtilSecurity.decryptId(idUnidade));
		UnidadeNegocio unidadeNegocio = opUnidade.orElseThrow(()-> new ObjectNotFoundException(funcionalidade()));
		
		Collection<Assinante> assinantes = assinanteRepository.pesquisarAssinantesPorUnidadeNegocio(unidadeNegocio);
		return assinantes.stream()
				.map(AssinanteMapper.INSTANCE :: convertToRest)
				.collect(Collectors.toList());
	}

	public RestAssinante acharPorId(String id)
	{
		return super.getById(id);
	}

	public void novo(RestAssinante restAssinante)
	{
		planoService.validaQuantidadeLocalidadesPlano(restAssinante);
		Assinante assinante = restAssinante.restParaModel();
		topicoService.atualizarTopicosAssinantes(assinante);
		assinanteRepository.save(assinante);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(String id, RestAssinante restAssinante) {
		Assinante assinanteById = assinanteRepository
				.findById(UtilSecurity.decryptId(id))
				.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.PTR024));
		planoService.validaQuantidadeLocalidadesPlano(restAssinante);

		Assinante assinante = restAssinante.restParaModel();
		assinante.setId(assinanteById.getId());
		assinante.setStatus(assinanteById.getStatus());

		topicoService.atualizarTopicosAssinantes(assinante);
		localidadeService.atualizarLocalidadesAssinante(assinanteById, assinante);

		assinante.setLocalidades(assinanteById.getLocalidades());
		assinanteRepository.save(assinante);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletar(String id)
	{
		Assinante assinante = assinanteRepository.findById(UtilSecurity.decryptId(id))
				.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.PTR024));

		List<Long> listIdLocalidades = assinante.getLocalidades()
				.stream()
				.mapToLong(LocalidadeAssinante::getId)
				.boxed()
				.collect(Collectors.toList());
		localidadeService.excluirLocalidadesAssinante(listIdLocalidades);

		assinante.getUsuarioAssinantes()
				.forEach(u -> usuarioAssinanteService.deletar(UtilSecurity.encryptId(u.getId())));

		assinanteRepository.delete(assinante);
	}

	public void update(String id, TipoStatusAssinante status)
	{
		StatusAssinante statusAssinante = statusAssinanteRepository.findById(status.getId())
				.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

		Assinante assinante = getRepository().findById(UtilSecurity.decryptId(id))
				.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

		assinante.setStatus(statusAssinante);

		boolean podeAtualizar;

		if(status.equals(TipoStatusAssinante.I))
		{
			podeAtualizar = false;
		} else
		{
			podeAtualizar = true;
		}

		assinante.getUsuarioAssinantes().forEach(a -> a.setAtivo(podeAtualizar));

		getRepository().save(assinante);
	}

	public Collection<RestAssinante> listaSimples(){
		Collection<Assinante> assinantes = assinanteRepository.listaSimples();
		return assinantes.stream().map(Assinante::modelParaRest).collect(Collectors.toList());
	}

	@Override
	protected CrudRepository<Assinante, Long> getRepository()
	{
		return assinanteRepository;
	}

	@Override
	protected String funcionalidade()
	{
		return "Assinante";
	}
}
