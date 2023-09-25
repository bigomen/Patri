package com.patri.plataforma.restapi.service;

import com.patri.plataforma.restapi.model.ReportContexto;
import com.patri.plataforma.restapi.repository.ReportContextoRepository;
import com.patri.plataforma.restapi.restmodel.RestReportContexto;
import com.patri.plataforma.restapi.utility.RestSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ReportContextoService extends BaseService<ReportContexto, RestReportContexto>
{
    private final ReportContextoRepository reportContextoRepository;
    private final Environment env;

    @Autowired
    public ReportContextoService(ReportContextoRepository reportContextoRepository, Environment env)
    {
        super();
        this.reportContextoRepository = reportContextoRepository;
        this.env = env;
    }

    public Collection<RestReportContexto> listar()
    {
        return super.getAll();
    }

    public RestReportContexto acharPorId(String id)
    {
        return super.getById(id);
    }

    public void novo(RestReportContexto restReportContexto)
    {
        super.create(restReportContexto);
    }

    public void update(String id, RestReportContexto restReportContexto)
    {
        restReportContexto.setId(id);
        super.update(restReportContexto);
    }

    public void deletar(String id)
    {
        super.delete(id);
    }

    @Override
    protected CrudRepository getRepository()
    {
        return reportContextoRepository;
    }

    @Override
    protected RestSecurity<ReportContexto, RestReportContexto> getRestSecurity()
    {
        return new RestSecurity<>(ReportContexto.class, RestReportContexto.class, env);
    }

}
