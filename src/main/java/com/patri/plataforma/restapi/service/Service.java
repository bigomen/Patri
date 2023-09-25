package com.patri.plataforma.restapi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.model.Model;
import com.patri.plataforma.restapi.restmodel.RestModel;

public abstract class Service<D extends Model<R>, R extends RestModel<D>>
{
	protected abstract CrudRepository<D, Long> getRepository();

	protected Collection<R> getAll()
	{
		Collection<R> allRest = new ArrayList<>();
		Iterable<D> findAll = getRepository().findAll();
		findAll.forEach(d -> allRest.add(d.modelParaRest()));
		return allRest;
	}

	protected Long getCount()
	{
		return getRepository().count();
	}

	protected R getById(String idCrypt)
	{
		Long decryptId = UtilSecurity.decryptId(idCrypt);
		Optional<D> entity = getRepository().findById(decryptId);
		
		D object = entity.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
		
		return object.modelParaRest();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	protected R create(R object)
	{
		D entity = object.restParaModel();
		entity = (D) getRepository().save(entity);
		return entity.modelParaRest();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	protected R update(R object)
	{
		D entity = object.restParaModel();
		entity = (D) getRepository().save(entity);
		
		return entity.modelParaRest();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	protected String delete(String idCrypt)
	{
		Long id = UtilSecurity.decryptId(idCrypt);
		Optional<D> findById = getRepository().findById(id);
		D d = findById.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
		getRepository().delete(d);
		return idCrypt;
	}

	protected Collection<R> sort(Collection<R> itens)
	{
		return itens.stream().sorted().collect(Collectors.toList());
	}
	
	protected String funcionalidade()
	{
		return "";
	}
}
