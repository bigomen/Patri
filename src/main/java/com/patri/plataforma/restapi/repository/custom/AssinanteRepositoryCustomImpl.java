package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.Assinante_;
import com.patri.plataforma.restapi.model.Plano;
import com.patri.plataforma.restapi.model.Plano_;
import com.patri.plataforma.restapi.model.StatusAssinante_;
import com.patri.plataforma.restapi.repository.custom.util.UtilQuery;

public class AssinanteRepositoryCustomImpl	implements AssinanteRepositoryCustom
{
	private final EntityManager em;
	
	@Autowired
	public AssinanteRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<Assinante> findAll(String param)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Assinante> criteriaQuery = builder.createQuery(Assinante.class);
		Root<Assinante> root = criteriaQuery.from(Assinante.class);
		root.fetch(Assinante_.STATUS);

		Path<String> nomeAssinante = root.get(Assinante_.NOME);
		
		if(StringUtils.isNotBlank(param))
		{
			Predicate likeNome = builder.like(builder.upper(nomeAssinante), UtilQuery.like(param.toUpperCase()));
			Predicate and = builder.and(likeNome);
			criteriaQuery.where(and);
		}
		
		criteriaQuery.orderBy(builder.asc(nomeAssinante));
		
		TypedQuery<Assinante> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
