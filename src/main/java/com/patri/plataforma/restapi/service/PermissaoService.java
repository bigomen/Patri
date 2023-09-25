package com.patri.plataforma.restapi.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.patri.plataforma.restapi.model.Permissao;
import com.patri.plataforma.restapi.repository.GrupoBackOfficeRepository;
import com.patri.plataforma.restapi.repository.PermissaoRepository;
import com.patri.plataforma.restapi.restmodel.RestPermissao;
import com.patri.plataforma.restapi.utility.RestSecurity;

@Service
public class PermissaoService extends BaseService<Permissao, RestPermissao>
{

    private final PermissaoRepository permissaoRepository;
    private final GrupoBackOfficeRepository grupoBackOfficeRepository;
    private final Environment env;

    @Autowired
    public PermissaoService(PermissaoRepository permissaoRepository,
                            GrupoBackOfficeRepository grupoBackOfficeRepository,
                            Environment env)
    {
        this.permissaoRepository = permissaoRepository;
        this.grupoBackOfficeRepository = grupoBackOfficeRepository;
        this.env = env;
    }

    @java.lang.Override
    protected CrudRepository getRepository()
    {
        return this.permissaoRepository;
    }

    @java.lang.Override
    protected RestSecurity<Permissao, RestPermissao> getRestSecurity()
    {
        return new RestSecurity<>(Permissao.class, RestPermissao.class, this.env);
    }

    public Collection<RestPermissao> findAll()
    {
        return super.getAll();
    }

    public RestPermissao find(String id)
    {
        return super.getById(id);
    }


    public String delete(String id)
    {
        return super.delete(id);
    }

    public void update(String id, RestPermissao updatedPermissao)
    {
        RestPermissao rp = find(id);
        updatedPermissao.setId(id);

        if (updatedPermissao.getRota() == null)
        {
            updatedPermissao.setRota(rp.getRota());
        }

        super.update(updatedPermissao);
    }
}
