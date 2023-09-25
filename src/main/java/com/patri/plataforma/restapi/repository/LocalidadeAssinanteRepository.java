package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import com.patri.plataforma.restapi.repository.custom.LocalidadeAssinanteRepositoryCustom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LocalidadeAssinanteRepository extends CrudRepository<LocalidadeAssinante, Long>, LocalidadeAssinanteRepositoryCustom 
{
    Collection<LocalidadeAssinante> findByAssinante(Assinante assinante);
}
