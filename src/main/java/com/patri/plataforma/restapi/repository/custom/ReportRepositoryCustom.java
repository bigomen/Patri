package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.UsuarioAssinante;

public interface ReportRepositoryCustom
{
	public Collection<Report> listarReports(String param);
	public Optional<Report> pesquisarPorId(Long id);
	public Map<UsuarioAssinante, Set<Report>> pesquisarReportsNewsletter();
}
