package com.patri.plataforma.restapi.service;

import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.model.GrupoBackOffice;
import com.patri.plataforma.restapi.repository.GrupoBackOfficeRepository;
import com.patri.plataforma.restapi.restmodel.RestGrupoBackOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GrupoBackOfficeService extends com.patri.plataforma.restapi.service.Service<GrupoBackOffice, RestGrupoBackOffice>
{

    private final GrupoBackOfficeRepository grupoBackOfficeRepository;

    @Autowired
    public GrupoBackOfficeService(GrupoBackOfficeRepository grupoBackOfficeRepository)
    {
        super();
        this.grupoBackOfficeRepository = grupoBackOfficeRepository;
    }

    public Collection<RestGrupoBackOffice> listar()
    {
        return super.getAll();
    }

    public Collection<RestGrupoBackOffice> pesquisar(String param)
    {
        Collection<GrupoBackOffice> grupoBackOffices = new HashSet<>();
        Collection<RestGrupoBackOffice> restGrupoBackOffices = new HashSet<>();

        try
        {
            Long newId = UtilSecurity.decryptId(param);
            Optional<GrupoBackOffice> byId = grupoBackOfficeRepository.findById(newId);
            grupoBackOffices.add(byId.orElseThrow(() -> new ObjectNotFoundException("Grupo Back Office")));
        } catch (Exception e)
        {
            grupoBackOffices = grupoBackOfficeRepository.listaGrupo(param);
        }

        grupoBackOffices.forEach(a -> restGrupoBackOffices.add(a.modelParaRest()));

        return restGrupoBackOffices;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestGrupoBackOffice restGrupoBackOffice)
    {
        super.create(restGrupoBackOffice);
    }

    public RestGrupoBackOffice obterPorId(String id) {
        return super.getById(id);
    }

    public void alterar(RestGrupoBackOffice restGrupoBackOffice, String id)
    {
        restGrupoBackOffice.setId(id);
        super.update(restGrupoBackOffice);
    }

    public void deletar(String id)
    {
        super.delete(id);
    }

    @Override
    protected CrudRepository getRepository()
    {
        return grupoBackOfficeRepository;
    }
    
    
}
