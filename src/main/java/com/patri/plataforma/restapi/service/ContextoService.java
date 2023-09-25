package com.patri.plataforma.restapi.service;

import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.model.Contexto;
import com.patri.plataforma.restapi.repository.ContextoRepository;
import com.patri.plataforma.restapi.restmodel.RestContexto;
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
public class ContextoService extends BaseService<Contexto, RestContexto>
{

    private final ContextoRepository contextoRepository;
    private final Environment env;

    @Autowired
    public ContextoService(ContextoRepository contextoRepository, Environment env)
    {
        super();
        this.contextoRepository = contextoRepository;
        this.env = env;
    }

    public Collection<RestContexto> listar()
    {
        return super.getAll();
    }

    public RestContexto acharPorId(String id)
    {
        return super.getById(id);
    }

    public void novo(RestContexto restContexto)
    {
        super.create(restContexto);
    }

    public void update(String id, RestContexto updatedRestContexto) throws Exception
    {
       contextoRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException("Contexto"));
        updatedRestContexto.setId(id);
        super.update(updatedRestContexto);
    }

    public void deletar(String id)
    {
        contextoRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException("Contexto"));
        super.delete(id);
    }

    @Override
    protected CrudRepository getRepository()
    {
        return contextoRepository;
    }

    @Override
    protected RestSecurity<Contexto, RestContexto> getRestSecurity()
    {
        return new RestSecurity<>(Contexto.class, RestContexto.class, this.env);
    }
}

