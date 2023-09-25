package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.StatusAssinante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusAssinanteRepository extends CrudRepository<StatusAssinante, Long>
{

}
