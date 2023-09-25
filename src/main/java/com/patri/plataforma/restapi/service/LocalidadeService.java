package com.patri.plataforma.restapi.service;

import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.model.*;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.repository.LocalidadeAssinanteRepository;
import com.patri.plataforma.restapi.repository.MunicipioRepository;
import com.patri.plataforma.restapi.repository.UFRepository;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LocalidadeService
{
	private final MunicipioRepository municipioRepository;
	private final UFRepository ufRepository;
	private final LocalidadeAssinanteRepository localidadeAssinanteRepository;
	
	@Autowired
	public LocalidadeService(MunicipioRepository municipioRepository, UFRepository ufRepository, LocalidadeAssinanteRepository localidadeAssinanteRepository)
	{
		super();
		this.municipioRepository = municipioRepository;
		this.ufRepository = ufRepository;
		this.localidadeAssinanteRepository = localidadeAssinanteRepository;
	}
	
	public Municipio consultarMunicipioPorId(String id)
	{
		return municipioRepository.findById(UtilSecurity.decryptId(id)).orElseThrow(() -> new ObjectNotFoundException("Município"));
	}
	
	public Collection<Municipio> consultarMunicipiosPorId(Collection<String> ids)
	{
		List<Long> decriptedIds = ids.stream().map(id -> UtilSecurity.decryptId(id)).collect(Collectors.toList());
		Collection<Municipio> municipios = new ArrayList<>();
		
		Iterable<Municipio> findAllById = municipioRepository.findAllById(decriptedIds);
		findAllById.forEach(municipios :: add);
		
		return municipios;
	}
	
	public UF consultarUFPorId(String id)
	{
		return ufRepository.findById(UtilSecurity.decryptId(id)).orElseThrow(() -> new ObjectNotFoundException("UF"));
	}
	
	public Collection<UF> consultarUFsPorId(Collection<String> ids)
	{
		List<Long> decriptedIds = ids.stream().map(id -> UtilSecurity.decryptId(id)).collect(Collectors.toList());
		Collection<UF> ufs = new ArrayList<>();
		
		Iterable<UF> findAllById = ufRepository.findAllById(decriptedIds);
		findAllById.forEach(ufs :: add);
		
		return ufs;
	}
	
	public Collection<RestLocalidade> consultarLocalidades(String parametro)
	{
		Collection<Localidade> municipios = municipioRepository.findByNomeContainingIgnoreCase(parametro);
		Collection<Localidade> ufs = ufRepository.findByDescricaoContainingIgnoreCase(parametro);

		Stream<RestLocalidade> streamMunicipios = municipios.stream().map(mun -> LocalidadeMapper.INSTANCE.convertToRest(mun));
		Stream<RestLocalidade> streamUfs = ufs.stream().map(uf -> LocalidadeMapper.INSTANCE.convertToRest(uf));
		List<RestLocalidade> restLocalidades = Stream.concat(streamMunicipios, streamUfs).collect(Collectors.toList());
		
		return restLocalidades;
	}
	
	public Collection<RestLocalidade> consultaLocalidades(String idAssinante, String param)
	{
		Collection<LocalidadeAssinante> localidades = localidadeAssinanteRepository.findByAssinante(UtilSecurity.decryptId(idAssinante), param);
		
		Stream<RestLocalidade> municipios = localidades.stream()
				.filter(loc -> loc.getMunicipio() != null)
				.map(loc -> LocalidadeMapper.INSTANCE.convertToRest(loc.getMunicipio()));
		
		Stream<RestLocalidade> ufs = localidades.stream()
				.filter(loc -> loc.getUf() != null)
				.map(loc -> LocalidadeMapper.INSTANCE.convertToRest(loc.getUf()));
		
		return Stream.concat(municipios, ufs).collect(Collectors.toList());
	}

	public void atualizarLocalidadesAssinante(Assinante assinanteById, Assinante assinante) {
		Collection<LocalidadeAssinante> excluirLocalidades = localidadesDiferentes(assinanteById.getLocalidades(), assinante.getLocalidades());
		Collection<LocalidadeAssinante> novasLocalidades = localidadesDiferentes(assinante.getLocalidades(), assinanteById.getLocalidades());
		if (!excluirLocalidades.isEmpty()) {
			excluirLocalidadesAssinante(excluirLocalidades.stream().map(LocalidadeAssinante::getId).collect(Collectors.toList()));
			assinanteById.getLocalidades().removeAll(excluirLocalidades);
		}
		if (!novasLocalidades.isEmpty()) {
			novasLocalidades.forEach(l -> l.setAssinante(assinanteById));
			assinanteById.getLocalidades().addAll(novasLocalidades);
		}
	}

	private Collection<LocalidadeAssinante> localidadesDiferentes(Collection<LocalidadeAssinante> from, Collection<LocalidadeAssinante> to) {
		if (from == null) {
			return Collections.EMPTY_LIST;
		}
		if (to == null) {
			return from;
		}
		return from.stream()
				.filter(l -> !to.contains(l))
				.collect(Collectors.toList());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void excluirLocalidadesAssinante(List<Long> listIdLocalidades) {
		listIdLocalidades.stream().forEach(idLocalidade -> localidadeAssinanteRepository.deleteById(idLocalidade));
	}

	public Collection<RestLocalidade> listaSimples(){
		List<RestLocalidade> rest = new ArrayList<>();
		Collection<Municipio> municipios = municipioRepository.listaSimples();
		municipios.forEach(m -> {
			RestLocalidade restLocalidade = new RestLocalidade();
			restLocalidade.setId(UtilSecurity.encryptId(m.getId()));
			restLocalidade.setNome(m.getNome());
			restLocalidade.setTipo(TipoLocalidade.MUN);
			rest.add(restLocalidade);
		});
		Collection<UF> ufs = (Collection<UF>) ufRepository.findAll();
		ufs.forEach(u -> {
			RestLocalidade restLocalidade = new RestLocalidade();
			restLocalidade.setId(UtilSecurity.encryptId(u.getId()));
			restLocalidade.setNome(u.getDescricao());
			restLocalidade.setTipo(TipoLocalidade.UF);
			rest.add(restLocalidade);
		});
		return rest;
	}
}
