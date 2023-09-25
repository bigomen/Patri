package com.patri.plataforma.restapi.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.StatusReport;
import com.patri.plataforma.restapi.repository.custom.ReportRepositoryCustom;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom
{
//	@Query(value = "select r from Report r left join fetch r.topicos t left join fetch r.localidades l where r.status = :status and r.enviado is false")
    public Collection<Report> findByStatusAndEnviadoFalse(StatusReport status);
    
    @Query(value = "select r from Report r join fetch r.unidadeNegocio un join fetch r.orgao o where r.status = :status and r.enviado is false order by o.ordem, r.data desc")
    public Collection<Report> pesquisarReports(StatusReport status);
    
	Collection<Report> findByPautaId(Long id);
	
	@Modifying
	@Query("update Report r set r.pauta =:pauta where r in (:reports)")
	void vincularReportsAPauta(@Param(value = "pauta")Pauta pauta,@Param(value = "reports") Collection<Report> reports);
	
	@Modifying
	@Query("update Report r set r.pauta = null where r.pauta = :pauta")
	void desvincularReportsPauta(@Param(value = "pauta") Pauta pauta);
}
