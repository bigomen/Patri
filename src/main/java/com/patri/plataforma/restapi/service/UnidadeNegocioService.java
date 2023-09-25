package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import com.patri.plataforma.restapi.repository.UnidadeNegocioRepository;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UnidadeNegocioService extends com.patri.plataforma.restapi.service.Service<UnidadeNegocio, RestUnidadeNegocio>
{
    private final UnidadeNegocioRepository unidadeNegocioRepository;

    @Autowired
    public UnidadeNegocioService(
            UnidadeNegocioRepository unidadeNegocioRepository)
    {
        super();
        this.unidadeNegocioRepository = unidadeNegocioRepository;
    }

    public Collection<RestUnidadeNegocio> listar()
    {
        return super.sort(super.getAll());
    }

    public Collection<RestUnidadeNegocio> pesquisar(String param)
    {
        Collection<UnidadeNegocio> unidades = new HashSet<>();
        Collection<RestUnidadeNegocio> unidadesRest = new HashSet<>();

        try
        {
            Long newId = UtilSecurity.decryptId(param);
            Optional<UnidadeNegocio> byId = unidadeNegocioRepository.findById(newId);
            unidades.add(byId.orElseThrow(() -> new ObjectNotFoundException(funcionalidade())));
        } catch (Exception e)
        {
            unidades = unidadeNegocioRepository.listaUnidade(param);
        }

        unidades.forEach(a -> unidadesRest.add(a.modelParaRest()));

        return super.sort(unidadesRest);
    }

    public RestUnidadeNegocio acharPorId(String id)
    {
        return super.getById(id);
    }

    public void novo(RestUnidadeNegocio restUnidadeNegocio)
    {
        super.create(restUnidadeNegocio);
    }

    public void alterar(String id, RestUnidadeNegocio restUnidadeNegocio)
    {
        restUnidadeNegocio.setId(id);
        super.update(restUnidadeNegocio);
    }

    public void alterar(String id, Boolean status)
    {
        UnidadeNegocio unidade = acharPorId(id).restParaModel();

        unidade.setUnidadeAtiva(status);

        getRepository().save(unidade);
    }

    public void deletar(String id)
    {
        super.delete(id);
    }

    public Collection<RestUnidadeNegocio> listaSimples(){
        Collection<UnidadeNegocio> unidades = unidadeNegocioRepository.listaSimples();
        return unidades.stream().map(UnidadeNegocio::modelParaRest).collect(Collectors.toList());
    }

    @Override
    protected CrudRepository getRepository()
    {
        return unidadeNegocioRepository;
    }

	@Override
	protected String funcionalidade()
	{
		return "Unidade Negócio";
	}
}
