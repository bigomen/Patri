package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Permissao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CrudRepository<Permissao, Long>
{
	
}
