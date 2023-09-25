package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.repository.custom.TopicoRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TopicoRepository extends CrudRepository<Topico, Long>, TopicoRepositoryCustom
{
    @Query("select t from Topico t where t.pai is null order by t.descricao")
	public Collection<Topico> listarTopicos();

    @Query("select t from Topico t where t.pai.id = :idPai order by t.descricao")
    public Collection<Topico> listarSubTopicos(@Param(value = "idPai") Long id);
    
    public Collection<Topico> findByPaiOrderByDescricao(@Param(value = "pai") Topico pai);

    @Query(value = "select t from Topico t where lower(t.descricao) like lower(concat('%', :param, '%')) order by t.descricao")
    public Collection<Topico> pesquisar(@Param("param") String param);
    
    @Query(value = "SELECT DISTINCT(T.*) FROM TOPICO T "
    		+ "INNER JOIN REPORT_TOPICO RT ON T.TOP_ID = RT.TOP_ID OR T.TOP_ID_PAI = RT.TOP_ID "
    		+ "INNER JOIN REPORT R ON RT.REP_ID = R.REP_ID "
    		+ "WHERE R.REP_ID = :IDREPORT", nativeQuery = true)
    public List<Topico> pesquisarTopicosPorReport(@Param(value = "IDREPORT") Long id);

    @Modifying
    @Query(value = "DELETE FROM TOPICO_ASSINANTE WHERE TOP_ID = :id", nativeQuery = true)
    public void excluirTopicoAssinante(@Param(value = "id") Long id);
    
    @Query("select t from Topico t join t.pautas p where p.id = :pauta")
    Collection<Topico> pesquisarTopicosPorPauta(@Param(value = "pauta") Long pauta);

    @Query("select new Topico(t.id, t.descricao) from Topico t where t.ativo = true order by t.descricao")
    Collection<Topico> listaSimples();
}
