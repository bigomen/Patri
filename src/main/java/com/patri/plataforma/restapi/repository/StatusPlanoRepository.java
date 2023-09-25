package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.StatusPlano;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPlanoRepository extends CrudRepository<StatusPlano, Long>
{

}
