package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.ReportContexto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportContextoRepository extends CrudRepository<ReportContexto, Long>
{

}
