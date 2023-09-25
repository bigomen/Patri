package com.patri.plataforma.restapi.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.model.StatusReport;
import com.patri.plataforma.restapi.repository.StatusProdutoRepository;
import com.patri.plataforma.restapi.restmodel.RestStatusReport;
import com.patri.plataforma.restapi.utility.RestSecurity;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StatusProdutoService extends BaseService<StatusReport, RestStatusReport>
{
    private final StatusProdutoRepository statusProdutoRepository;
    private final Environment env;

    @Autowired
    public StatusProdutoService(StatusProdutoRepository statusProdutoRepository,
                                Environment env)
    {
        super();
        this.statusProdutoRepository = statusProdutoRepository;
        this.env = env;
    }

    public Collection<RestStatusReport> listar()
    {
        return super.getAll();
    }

    public RestStatusReport acharPorId(String id)
    {
        return super.getById(id);
    }

    public void novo(RestStatusReport restStatusReport)
    {
        super.create(restStatusReport);
    }

    public void update(String id, RestStatusReport restStatusReport)
    {
        restStatusReport.setId(id);
        super.update(restStatusReport);
    }

    public void deletar(String id)
    {
        super.delete(id);
    }

    @Override
    protected CrudRepository getRepository()
    {
        return statusProdutoRepository;
    }

    @Override
    protected RestSecurity<StatusReport, RestStatusReport> getRestSecurity()
    {
        return new RestSecurity<>(StatusReport.class, RestStatusReport.class, env);
    }

}
