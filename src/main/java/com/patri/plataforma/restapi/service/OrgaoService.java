package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.mapper.OrgaoMapper;
import com.patri.plataforma.restapi.model.Orgao;
import com.patri.plataforma.restapi.repository.OrgaoRepository;
import com.patri.plataforma.restapi.restmodel.RestOrgao;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrgaoService extends com.patri.plataforma.restapi.service.Service<Orgao, RestOrgao>
{
	private final OrgaoRepository orgaoRepository;
	
	@Autowired
	public OrgaoService(OrgaoRepository orgaoRepository)
	{
		super();
		this.orgaoRepository = orgaoRepository;
	}

	public RestOrgao buscarPorId(String id)
	{
		return super.getById(id);
	}

	public Collection<RestOrgao> pesquisarOrgaos(String param)
	{
		Collection<Orgao> orgaos = orgaoRepository.pesquisarOrgaos(param);
		
		return orgaos.stream().map(o -> OrgaoMapper.INSTANCE.convertToRest(o)).collect(Collectors.toList());
	}
	
	public Collection<RestOrgao> pesquisarOrgaosPorUnidadeNegocio(String id)
	{
		Collection<Orgao> orgaos = orgaoRepository.pesquisarOrgaoPorUnidadeNegocio(UtilSecurity.decryptId(id));
		
		return orgaos
				.stream()
				.map(OrgaoMapper.INSTANCE::convertToRest)
				.collect(Collectors.toList());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public synchronized void novo(RestOrgao restOrgao)
	{
		Collection<Orgao> all = (Collection<Orgao>) orgaoRepository.findAll();

		Optional<Orgao> maiorOrdem = ultimoOrgaoAtivo(all);

		if(maiorOrdem.isEmpty())
		{
			restOrgao.setOrdem(1);
		} else if(restOrgao.getOrdem() == null || restOrgao.getOrdem() > maiorOrdem.get().getOrdem())
		{
			restOrgao.setOrdem(maiorOrdem.get().getOrdem() + 1);
		}

		all.stream()
				.filter(o -> o.getOrdem() >= restOrgao.getOrdem())
				.forEach(o -> o.setOrdem(o.getOrdem() + 1));

		orgaoRepository.saveAll(all);

		super.create(restOrgao);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public synchronized void alterar(RestOrgao restOrgao, String id)
	{
		Collection<Orgao> all = (Collection<Orgao>) orgaoRepository.findAll();
		RestOrgao oldOrgao = buscarPorId(id);

		if(restOrgao.getOrdem() == null)
		{
			restOrgao.setOrdem(oldOrgao.getOrdem());
		}

		Optional<Orgao> maiorOrdem = ultimoOrgaoAtivo(all);

		if(maiorOrdem.isEmpty())
		{
			restOrgao.setOrdem(1);
		} else if(restOrgao.getOrdem() > maiorOrdem.get().getOrdem())
		{
			restOrgao.setOrdem(maiorOrdem.get().getOrdem());
		}

		if(restOrgao.getOrdem() != null)
		{
			if (restOrgao.getOrdem() < oldOrgao.getOrdem())
			{
				all.stream()
						.filter(o -> o.getOrdem() >= restOrgao.getOrdem() && o.getOrdem() < oldOrgao.getOrdem())
						.forEach(orgao -> orgao.setOrdem(orgao.getOrdem() + 1));
			} else
			{
				all.stream()
						.filter(o -> o.getOrdem() <= restOrgao.getOrdem() && o.getOrdem() > oldOrgao.getOrdem())
						.forEach(orgao -> orgao.setOrdem(orgao.getOrdem() - 1));
			}

			orgaoRepository.saveAll(all);
		}

		restOrgao.setId(oldOrgao.getId());

		super.update(restOrgao);
	}

	public synchronized void ativarDesativarOrgao(String id, Boolean status)
	{
		RestOrgao restOrgao = buscarPorId(id);
		Collection<Orgao> all = (Collection<Orgao>) orgaoRepository.findAll();

		all.stream()
				.filter(o -> o.getOrdem() >= restOrgao.getOrdem())
				.forEach(o -> o.setOrdem(o.getOrdem() - 1));

		restOrgao.setAtivo(status);
		restOrgao.setOrdem(all.size());

		orgaoRepository.saveAll(all);
		orgaoRepository.save(restOrgao.restParaModel());
	}

	private Optional<Orgao> ultimoOrgaoAtivo(Collection<Orgao> orgaos)
	{
		return orgaos.stream().filter(Orgao::isAtivo).max(Comparator.comparing(Orgao::getOrdem));
	}

	public Collection<RestOrgao> listaSimples(){
		Collection<Orgao> orgaos = orgaoRepository.listaSimples();
		return orgaos.stream().map(Orgao::modelParaRest).collect(Collectors.toList());
	}

	@Override
	protected CrudRepository<Orgao, Long> getRepository()
	{
		return orgaoRepository;
	}

}
