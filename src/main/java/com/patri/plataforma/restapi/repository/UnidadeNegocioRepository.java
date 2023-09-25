package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.UnidadeNegocio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UnidadeNegocioRepository extends CrudRepository<UnidadeNegocio, Long>
{
    @Query(value = "select un from UnidadeNegocio un where lower(un.nome) like lower(concat('%', :param, '%'))")
    public Collection<UnidadeNegocio> listaUnidade(@Param("param") String param);

    @Query("select new UnidadeNegocio(u.id, u.nome) from UnidadeNegocio u where u.unidadeAtiva = true order by u.nome")
    Collection<UnidadeNegocio> listaSimples();
}
