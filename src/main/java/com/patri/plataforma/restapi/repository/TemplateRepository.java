package com.patri.plataforma.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Long>
{
}
