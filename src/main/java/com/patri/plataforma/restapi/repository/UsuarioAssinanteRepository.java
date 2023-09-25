/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.model.UsuarioAssinante;

/**
 *
 * @author rcerqueira
 */
@Repository
public interface UsuarioAssinanteRepository extends CrudRepository<UsuarioAssinante,Long> {
    
    UsuarioAssinante findByLogin(@Param("usuLogin") String usu_login);

	@Query("select ua from UsuarioAssinante ua join fetch ua.assinante a left join fetch ua.localidadesAssinantes " +
			"where ua.id = :id")
    Optional<UsuarioAssinante> findById(@Param("id") Long id);

    @Query("select u from UsuarioAssinante u where lower(u.nome) like lower(concat('%',:pesquisa,'%')) " +
            "or lower(u.sobrenome) like lower(concat('%',:pesquisa,'%')) " +
            "or lower(u.login) like lower(concat('%',:pesquisa,'%'))")
    Collection<UsuarioAssinante> findUsuarioByPesquisa(@Param(value = "pesquisa") String pesquisa);

    @Query(value = "select u from UsuarioAssinante u where u.login = :login and u.ativo = :ativo")
    public Optional<Usuario> findByLoginAndAtivo(@Param("login") String login, @Param("ativo") boolean ativo);

    public Collection<UsuarioAssinante> findByLoginContainingIgnoreCase(@Param(value = "param")String param);

    @Query("select u from UsuarioAssinante u join u.assinante a join a.plano p join a.reports r where r = :report and r.unidadeNegocio MEMBER OF p.unidades")
    public Collection<UsuarioAssinante> findUsuariosDoReport(@Param(value = "report") Report report);
    
    @Query(value = "SELECT DISTINCT(UA.*) FROM USUARIO_ASSINANTE UA "
    		+ " INNER JOIN ASSINANTE A ON A.ASN_ID = UA.ASN_ID "
    		+ " INNER JOIN PLANO P ON A.PLA_ID = P.PLA_ID "
    		+ "	INNER JOIN LOCALIDADE_USUARIO_ASSINANTE LUA ON UA.UAS_ID = LUA.UAS_ID "
    		+ "	INNER JOIN LOCALIDADE_ASSINANTE LA ON LA.LAS_ID = LUA.LAS_ID "
    		+ "	LEFT JOIN UF U ON LA.UF_ID = U.UF_ID "
    		+ "	LEFT JOIN MUNICIPIO M ON LA.MUN_ID = M.MUN_ID"
    		+ "	LEFT JOIN LOCALIDADE_REPORT LR ON M.MUN_ID = LR.MUN_ID OR U.UF_ID = LR.UF_ID "
    		+ "	LEFT JOIN REPORT R ON LR.REP_ID = R.REP_ID "
    		+ " WHERE R.REP_ID = :IDREPORT "
    		+ " AND R.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID) ", nativeQuery = true)
    public Collection<UsuarioAssinante> findUsuariosDoReportPorLocalidade(@Param(value = "IDREPORT") Long id);
    
    @Query(value = "SELECT DISTINCT(UA.*) FROM USUARIO_ASSINANTE UA"
    		+ " INNER JOIN ASSINANTE A ON A.ASN_ID = UA.ASN_ID "
    		+ " INNER JOIN PLANO P ON A.PLA_ID = P.PLA_ID "
    		+ "	INNER JOIN TOPICO_USUARIO_ASSINANTE TUA ON UA.UAS_ID = TUA.UAS_ID "
    		+ "	INNER JOIN TOPICO_ASSINANTE TA ON TA.TOP_ID = TUA.TOP_ID AND TA.ASN_ID = TUA.ASN_ID "
    		+ "	INNER JOIN TOPICO T ON T.TOP_ID_PAI = TUA.TOP_ID OR T.TOP_ID = TUA.TOP_ID "
    		+ "	INNER JOIN REPORT_TOPICO RT ON RT.TOP_ID = T.TOP_ID"
    		+ "	INNER JOIN REPORT R ON RT.REP_ID = R.REP_ID "
    		+ " WHERE R.REP_ID = :IDREPORT "
    		+ " AND R.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID)", nativeQuery = true)
    public Collection<UsuarioAssinante> findUsuariosDoReportPorTopico(@Param(value = "IDREPORT") Long id);

	@Query(value = "select u from UsuarioAssinante u " +
			"join u.assinante a " +
			"join a.plano p " +
			"join u.pautas pt " +
			"where pt.id = :idPauta " +
			"and pt.unidadeNegocio MEMBER OF p.unidades")
	public Collection<UsuarioAssinante> findUsuariosPauta(@Param(value = "idPauta") Long id);


	@Query(value = "SELECT DISTINCT(UA.*) FROM USUARIO_ASSINANTE UA "
			+ " INNER JOIN ASSINANTE A ON A.ASN_ID = UA.ASN_ID "
			+ " INNER JOIN PLANO P ON A.PLA_ID = P.PLA_ID "
			+ "	INNER JOIN LOCALIDADE_USUARIO_ASSINANTE LUA ON UA.UAS_ID = LUA.UAS_ID "
			+ "	INNER JOIN LOCALIDADE_ASSINANTE LA ON LA.LAS_ID = LUA.LAS_ID "
			+ "	LEFT JOIN UF U ON LA.UF_ID = U.UF_ID "
			+ "	LEFT JOIN MUNICIPIO M ON LA.MUN_ID = M.MUN_ID"
			+ "	LEFT JOIN LOCALIDADE_PAUTA LP ON M.MUN_ID = LP.MUN_ID OR U.UF_ID = LP.UF_ID "
			+ "	LEFT JOIN PAUTA P ON LP.PAT_ID = P.PAT_ID "
			+ " WHERE P.PAT_ID = :idPauta "
			+ " AND P.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID) ", nativeQuery = true)
	public Collection<UsuarioAssinante> findUsuariosPautaPorLocalidade(@Param(value = "idPauta") Long id);

	@Query(value = "SELECT DISTINCT(UA.*) FROM USUARIO_ASSINANTE UA"
			+ " INNER JOIN ASSINANTE A ON A.ASN_ID = UA.ASN_ID "
			+ " INNER JOIN PLANO P ON A.PLA_ID = P.PLA_ID "
			+ "	INNER JOIN TOPICO_USUARIO_ASSINANTE TUA ON UA.UAS_ID = TUA.UAS_ID "
			+ "	INNER JOIN TOPICO_ASSINANTE TA ON TA.TOP_ID = TUA.TOP_ID AND TA.ASN_ID = TUA.ASN_ID "
			+ "	INNER JOIN TOPICO T ON T.TOP_ID_PAI = TUA.TOP_ID OR T.TOP_ID = TUA.TOP_ID "
			+ "	INNER JOIN PAUTA_TOPICO PT ON PT.TOP_ID = T.TOP_ID"
			+ "	INNER JOIN PAUTA P ON PT.PAT_ID = P.PAT_ID "
			+ " WHERE P.PAT_ID = :idPauta "
			+ " AND R.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID)", nativeQuery = true)
	public Collection<UsuarioAssinante> findUsuarioPautaPorTopico(@Param(value = "idPauta") Long id);

    @Query(value = "SELECT DISTINCT(UA.*) FROM USUARIO_ASSINANTE UA "
    		+ "	INNER JOIN LOCALIDADE_USUARIO_ASSINANTE LUA ON UA.UAS_ID = LUA.UAS_ID "
    		+ "	INNER JOIN LOCALIDADE_ASSINANTE LA ON LA.LAS_ID = LUA.LAS_ID "
    		+ "	INNER JOIN ASSINANTE A ON LA.ASN_ID = A.ASN_ID "
    		+ "	LEFT JOIN UF U ON LA.UF_ID = U.UF_ID "
    		+ "	LEFT JOIN MUNICIPIO M ON LA.MUN_ID = M.MUN_ID "
    		+ "	LEFT JOIN LOCALIDADE_REPORT LR ON M.MUN_ID = LR.MUN_ID OR U.UF_ID = LR.UF_ID "
    		+ "	INNER JOIN TOPICO_USUARIO_ASSINANTE TUA ON UA.UAS_ID = TUA.UAS_ID "
    		+ " INNER JOIN TOPICO_ASSINANTE TA ON TA.TOP_ID = TUA.TOP_ID AND TA.ASN_ID = A1.ASN_ID "
    		+ "	INNER JOIN TOPICO T ON T.TOP_ID = TA.TOP_ID OR T.TOP_ID_PAI = TA.TOP_ID "
    		+ "	INNER JOIN REPORT_TOPICO RT ON RT.TOP_ID = T.TOP_ID "
    		+ "	INNER JOIN REPORT R ON LR.REP_ID = R.REP_ID AND RT.REP_ID = R.REP_ID "
    		+ "	INNER JOIN PLANO P ON P.PLA_ID = A1.PLA_ID OR P.PLA_ID = A2.PLA_ID "
    		+ "WHERE TUA.TOP_ID = :IDTOPICO "
    		+ "	AND LUA.LAS_ID = :IDLOCALIDADE "
    		+ "	AND UA.UAS_NEWSLETTER_DIRETO = 'N'"
    		+ "	AND R.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID) "
    		+ " AND R.REP_ID IN (:REPORTS) ", nativeQuery = true)
	public Collection<UsuarioAssinante> findUsuariosDoReportPorLocalidadeAndTopico(@Param(value = "IDTOPICO")Long idTopico, @Param(value = "IDLOCALIDADE")Long idLocalidade, @Param(value = "REPORTS") List<Long> reports);

    @Query("select new UsuarioAssinante(u.id, u.nome, u.login) from UsuarioAssinante u left join u.pautas p where p.id = :pauta")
	Collection<UsuarioAssinante> pesquisarUsuariosPorPauta(@Param(value = "pauta") Long pauta);
}
