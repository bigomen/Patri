package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Plano;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PlanoRepository extends CrudRepository<Plano, Long>
{
    @Query(value = "select p from Plano p where lower(p.nome) like lower(concat('%', :param, '%'))")
    public Collection<Plano> listaPlano(@Param("param") String param);

    @Query(value = "select p from Plano p join fetch p.unidades un")
    public Set<Plano> listaAllPlanos();

    @Query(value = "select p from Plano p join fetch p.unidades un where p.status = 1")
    public Set<Plano> listaPlanosAtivos();

    @Query(value = "select p.qtdLocalidades from Plano p where p.id = :id")
    public Integer quantidadeLocalidades(@Param("id") Long id);
}
