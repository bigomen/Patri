package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.GrupoBackOffice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GrupoBackOfficeRepository extends CrudRepository<GrupoBackOffice, Long>
{
    @Query(value = "select gb from GrupoBackOffice gb where lower(gb.nome) like lower(concat('%', :param, '%'))")
    Collection<GrupoBackOffice> listaGrupo(@Param("param") String param);
}
