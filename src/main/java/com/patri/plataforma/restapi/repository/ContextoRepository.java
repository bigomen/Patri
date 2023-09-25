package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Contexto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextoRepository extends CrudRepository<Contexto, Long>
{
}
