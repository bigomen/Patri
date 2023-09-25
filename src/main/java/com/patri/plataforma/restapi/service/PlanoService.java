package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import com.patri.plataforma.restapi.mapper.PlanoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.model.Plano;
import com.patri.plataforma.restapi.model.StatusPlano;
import com.patri.plataforma.restapi.model.enums.TipoStatusPlano;
import com.patri.plataforma.restapi.repository.PlanoRepository;
import com.patri.plataforma.restapi.repository.StatusPlanoRepository;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.restmodel.RestPlano;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PlanoService extends com.patri.plataforma.restapi.service.Service<Plano, RestPlano>
{
    private final PlanoRepository planoRepository;
    private final StatusPlanoRepository statusPlanoRepository;

    @Autowired
    public PlanoService(PlanoRepository planoRepository, StatusPlanoRepository statusPlanoRepository)
    {
        super();
        this.planoRepository = planoRepository;
        this.statusPlanoRepository = statusPlanoRepository;
    }

    public Collection<RestPlano> listar()
    {
        Collection<Plano> planos = planoRepository.listaAllPlanos();
        Collection<RestPlano> restPlanos = new HashSet<>();
        planos.forEach(plano -> restPlanos.add(PlanoMapper.INSTANCE.convertToSimpleRest(plano)));
        return super.sort(restPlanos);

    }

    public Collection<RestPlano> listarAtivos()
    {
        Collection<Plano> planos = planoRepository.listaPlanosAtivos();
        Collection<RestPlano> restPlanos = new HashSet<>();

        planos.forEach(plano -> restPlanos.add(PlanoMapper.INSTANCE.convertToSimpleRest(plano)));

        return super.sort(restPlanos);

    }

    public Collection<RestPlano> pesquisar(String param)
    {
        Collection<Plano> planos = new HashSet<>();
        Collection<RestPlano> restPlanos = new HashSet<>();
        
        try
        {
            Long newId = UtilSecurity.decryptId(param);
            Optional<Plano> byId = planoRepository.findById(newId);
            planos.add(byId.orElseThrow(() -> new ObjectNotFoundException("Plano")));
        } catch (Exception e)
        {
            planos = planoRepository.listaPlano(param);
        }

        planos.forEach(a -> restPlanos.add(a.modelParaRest()));

        return super.sort(restPlanos);
    }

    public void novo(RestPlano restPlano)
    {
        validarPlano(restPlano);

        super.create(restPlano);
    }

    public RestPlano encontrarPorId(String id)
    {
        return super.getById(id);
    }

    public void update(String id, RestPlano updatedPlano)
    {
        validarPlano(updatedPlano);
        updatedPlano.setId(id);
        
        super.update(updatedPlano);
    }

    public void deletar(String id)
    {

    }

    private void validarPlano(RestPlano rest)
    {
        if (!rest.getAgenda() && !rest.getReport() && !rest.getSintese())
        {
            throw new PatriRuntimeException(HttpStatus.BAD_REQUEST,MensagensID.PTR050);
        }
    }

    private void validarVinculoAssinante(Plano plano)
    {
        Long contador = plano.getAssinantes()
                .stream()
                .filter(assinante -> assinante.getStatus().getId().equals(1L))
                .count();

        if (contador > 0)
        {
            throw new PatriRuntimeException(HttpStatus.BAD_REQUEST,MensagensID.PTR059);
        }
    }

    public void update(String id, TipoStatusPlano status)
    {
        StatusPlano statusPlano = statusPlanoRepository.findById(status.getId())
                .orElseThrow(() -> new ObjectNotFoundException(MensagensID.PTR060));

        Plano byId = getRepository().findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException("Plano"));

        validarVinculoAssinante(byId);

        byId.setStatus(statusPlano);

        getRepository().save(byId);
    }

    public void validaQuantidadeLocalidadesPlano(RestAssinante restAssinante) {
        Integer qtdLocalidades = planoRepository.quantidadeLocalidades(UtilSecurity.decryptId(restAssinante.getPlano().getId()));
        if (restAssinante.getLocalidades() != null && (restAssinante.getLocalidades().size() > qtdLocalidades)) {
            throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR209);
        }
    }

    @Override
    protected CrudRepository<Plano, Long> getRepository()
    {
        return planoRepository;
    }

}
