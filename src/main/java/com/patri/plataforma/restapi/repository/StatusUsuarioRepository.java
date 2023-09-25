package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.StatusPlano;
import com.patri.plataforma.restapi.model.StatusUsuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusUsuarioRepository extends CrudRepository<StatusUsuario, Long>
{

}
