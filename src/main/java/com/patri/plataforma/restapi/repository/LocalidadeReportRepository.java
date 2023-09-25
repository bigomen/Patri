package com.patri.plataforma.restapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.patri.plataforma.restapi.model.LocalidadeReport;
import com.patri.plataforma.restapi.model.Report;

public interface LocalidadeReportRepository  extends CrudRepository<LocalidadeReport, Long>
{
	public List<LocalidadeReport> findByReport(Report report);
}
