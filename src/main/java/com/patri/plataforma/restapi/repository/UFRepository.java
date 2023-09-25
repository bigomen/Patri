package com.patri.plataforma.restapi.repository;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Localidade;
import com.patri.plataforma.restapi.model.UF;

@Repository
public interface UFRepository extends CrudRepository<UF, Long>
{
	public Collection<Localidade> findByDescricaoContainingIgnoreCase(@Param(value = "param") String param);
}
