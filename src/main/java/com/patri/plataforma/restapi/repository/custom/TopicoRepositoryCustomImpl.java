package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.Assinante_;
import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.model.Topico_;
import com.patri.plataforma.restapi.repository.custom.util.UtilQuery;

public class TopicoRepositoryCustomImpl implements TopicoRepositoryCustom
{
	private final EntityManager em;
	
	@Autowired
	public TopicoRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<Topico> findByAssinanteAndParam(Long idAssinante, String param)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Topico> query = builder.createQuery(Topico.class);
		Root<Topico> root = query.from(Topico.class);
		Join<Topico, Assinante> joinAssinante = root.join(Topico_.ASSINANTES, JoinType.INNER);
		Predicate equalIdAssinante = builder.equal(joinAssinante.get(Assinante_.ID), idAssinante);
		Predicate where = builder.and(equalIdAssinante);
		
		if(StringUtils.isNotBlank(param))
		{
			Predicate likeTopico = builder.like(builder.upper(root.get(Topico_.DESCRICAO)), UtilQuery.like(param.toUpperCase()));
			where = builder.and(where, likeTopico);
		}
		
		query.where(where);
		query.orderBy(builder.asc(root.get(Topico_.DESCRICAO)));
		TypedQuery<Topico> tp = em.createQuery(query);
		return tp.getResultList();
	}
}
