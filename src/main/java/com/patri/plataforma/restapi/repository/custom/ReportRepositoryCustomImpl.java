package com.patri.plataforma.restapi.repository.custom;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.patri.plataforma.restapi.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.patri.plataforma.restapi.repository.custom.util.UtilQuery;

public class ReportRepositoryCustomImpl implements ReportRepositoryCustom
{
	private EntityManager em;
	
	@Autowired
	public ReportRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<Report> listarReports(String param)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Report> criteriaQuery = builder.createQuery(Report.class);
		Root<Report> root = criteriaQuery.from(Report.class);

		if(StringUtils.isNotBlank(param))
		{
			Predicate likeIdentificador = builder.like(builder.upper(root.get(Report_.IDENTIFICADOR)), UtilQuery.like(param.toUpperCase()));
			Predicate likeTitulo = builder.like(builder.upper(root.get(Report_.TITULO)), UtilQuery.like(param.toUpperCase()));
			Predicate or = builder.or(likeIdentificador, likeTitulo);
			criteriaQuery.where(or);
		}
		
		criteriaQuery.orderBy(builder.asc(root.get(Report_.DATA)), builder.asc(root.get(Report_.ID)));
		TypedQuery<Report> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Optional<Report> pesquisarPorId(Long id)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Report> criteriaQuery = builder.createQuery(Report.class);
		Root<Report> root = criteriaQuery.from(Report.class);
		root.fetch(Report_.ORGAO, JoinType.INNER);
		root.fetch(Report_.UNIDADE_NEGOCIO, JoinType.INNER);
		Join<Object, Object> fetch = (Join<Object, Object>) root.fetch(Report_.ITENS, JoinType.INNER);

		criteriaQuery.select(root);
		
		Predicate equalID = builder.equal(root.get(Report_.ID), id);
		Predicate equalAtivo = builder.equal(fetch.get(Item_.ATIVO), true);
		criteriaQuery.where(builder.and(equalID, equalAtivo));
		
		TypedQuery<Report> typedQuery = em.createQuery(criteriaQuery);
		try
		{
			Report report = typedQuery.getSingleResult();
			return Optional.of(report);
		}catch (NoResultException nex) 
		{
			return Optional.empty();
		}
	}

	@Override
	public Map<UsuarioAssinante, Set<Report>> pesquisarReportsNewsletter()
	{
		String sqlNativo = "SELECT UA.UAS_ID, R.REP_ID "
				+ " FROM USUARIO_ASSINANTE UA "
				+ "	INNER JOIN LOCALIDADE_USUARIO_ASSINANTE LUA ON UA.UAS_ID = LUA.UAS_ID "
				+ "	INNER JOIN LOCALIDADE_ASSINANTE LA ON LA.LAS_ID = LUA.LAS_ID "
				+ "	INNER JOIN ASSINANTE A1 ON LA.ASN_ID = A1.ASN_ID "
				+ "	LEFT JOIN UF U ON LA.UF_ID = U.UF_ID "
				+ "	LEFT JOIN MUNICIPIO M ON LA.MUN_ID = M.MUN_ID "
				+ "	LEFT JOIN LOCALIDADE_REPORT LR ON M.MUN_ID = LR.MUN_ID OR U.UF_ID = LR.UF_ID "
				+ "	INNER JOIN TOPICO_USUARIO_ASSINANTE TUA ON UA.UAS_ID = TUA.UAS_ID "
				+ "	INNER JOIN TOPICO_ASSINANTE TA ON TA.TOP_ID = TUA.TOP_ID AND TA.ASN_ID = A1.ASN_ID "
				+ "	INNER JOIN TOPICO T ON T.TOP_ID = TA.TOP_ID OR T.TOP_ID_PAI = TA.TOP_ID "
				+ "	INNER JOIN REPORT_TOPICO RT ON RT.TOP_ID = T.TOP_ID "
				+ "	INNER JOIN REPORT R ON LR.REP_ID = R.REP_ID AND RT.REP_ID = R.REP_ID "
				+ "	INNER JOIN PLANO P ON P.PLA_ID = A1.PLA_ID "
				+ "WHERE "
				+ "	UA.UAS_NEWSLETTER_DIRETO = 'N' "
				+ "	AND R.UNE_ID IN (SELECT UNP.UNE_ID FROM PLANO_UNIDADE_NEGOCIO UNP WHERE P.PLA_ID = UNP.PLA_ID) "
				+ "	AND R.REP_ENVIO_EMAIL IS FALSE "
				+ "GROUP BY UA.UAS_ID, R.REP_ID ";
		
		Query nativeQuery = em.createNativeQuery(sqlNativo);
		
		List<Object[]> resultList = nativeQuery.getResultList();
		
		Map<UsuarioAssinante, Set<Report>> reports = new HashMap<UsuarioAssinante, Set<Report>>();
		
		for (Object[] object : resultList)
		{
			UsuarioAssinante user = new UsuarioAssinante();
			user.setId(((BigInteger) object[0]).longValue());
			
			if(!reports.containsKey(user))
			{
				reports.put(user, new HashSet<Report>());
			}
			
			Report report = new Report();
			report.setId(((BigInteger) object[1]).longValue());
			
			reports.get(user).add(report);
		}
		
		return reports;
	}
}
