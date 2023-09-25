package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.model.TopicoUsuarioAssinante;
import com.patri.plataforma.restapi.model.TopicoUsuarioAssinanteId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TopicoUsuarioAssinanteRepository  extends CrudRepository<TopicoUsuarioAssinante, TopicoUsuarioAssinanteId> 
{
    public Collection<TopicoUsuarioAssinante> findByIdIdTopico(Long idTopico);
    public Collection<TopicoUsuarioAssinante> findByIdIdUsuario(Long idUsuarioAssinante);

    @Query(value = "SELECT T FROM TopicoUsuarioAssinante T JOIN FETCH T.topico top WHERE T.id.idAssinante = :idAssinante")
    public Collection<TopicoUsuarioAssinante> findByAssinante(Long idAssinante);

    @Query(value = "SELECT T FROM TopicoUsuarioAssinante T JOIN FETCH T.topico top WHERE T.id.idUsuario = :idUsuarioAssinante")
    public Collection<TopicoUsuarioAssinante> findByUsuarioAssinante(Long idUsuarioAssinante);

    @Modifying
    @Query(value = "DELETE FROM TopicoUsuarioAssinante T WHERE T.topico IN (:TOPICOS)")
    public void excluirTopicos(@Param(value = "TOPICOS") Collection<Topico> topicos);
}
