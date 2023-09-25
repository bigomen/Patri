package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.StatusReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusProdutoRepository extends CrudRepository<StatusReport, Long>
{

}
