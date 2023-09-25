package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.LocalidadePauta;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadePautaRepository extends CrudRepository<LocalidadePauta, Long> 
{

	Collection<LocalidadePauta> findByPautaId(Long id);
}
