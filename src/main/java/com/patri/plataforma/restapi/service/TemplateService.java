package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.patri.plataforma.restapi.model.Template;
import com.patri.plataforma.restapi.repository.TemplateRepository;
import com.patri.plataforma.restapi.restmodel.RestTemplate;

@Service
public class TemplateService  extends com.patri.plataforma.restapi.service.Service<Template, RestTemplate>
{
	private final TemplateRepository repository;
	
	@Autowired
	public TemplateService(TemplateRepository repository)
	{
		super();
		this.repository = repository;
	}
	
	public Collection<RestTemplate> pesquisarTemplates()
	{
		return super.getAll();
	}

	public Collection<RestTemplate> listaSimples(){
		Collection<Template> templates = (Collection<Template>) repository.findAll();
		return templates.stream().map(Template::modelParaRest).collect(Collectors.toList());
	}

	@Override
	protected CrudRepository<Template, Long> getRepository()
	{
		return repository;
	}
}
