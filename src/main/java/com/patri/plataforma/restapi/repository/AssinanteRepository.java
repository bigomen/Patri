package com.patri.plataforma.restapi.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import com.patri.plataforma.restapi.repository.custom.AssinanteRepositoryCustom;

@Repository
public interface AssinanteRepository extends CrudRepository<Assinante, Long>, AssinanteRepositoryCustom
{
    @Query("select a from Assinante a where a.id = :id or lower(a.nome) like lower(concat('%',:chave,'%')) ")
    public Collection<Assinante> pesquisarAssinantes(@Param("chave") String chave, @Param("id") Long id);
    
    @Query("select a from Assinante a join fetch a.plano p where :unidade MEMBER OF p.unidades")
    public Collection<Assinante> pesquisarAssinantesPorUnidadeNegocio(@Param("unidade") UnidadeNegocio unidade);
    
    @Query("select new Assinante(a.id, a.nome) from Assinante a join a.pautas p where p.id = :pauta ")
    public Collection<Assinante> pesquisarAssinantesPorPauta(@Param("pauta") Long pauta);

    @Query("select new Assinante(a.id, a.nome) from Assinante a join a.status s where s.id = 1L order by a.nome")
    Collection<Assinante> listaSimples();
}
