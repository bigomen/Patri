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
import com.patri.plataforma.restapi.model.Assinante_;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import com.patri.plataforma.restapi.model.LocalidadeAssinante_;
import com.patri.plataforma.restapi.model.Municipio;
import com.patri.plataforma.restapi.model.Municipio_;
import com.patri.plataforma.restapi.model.UF;
import com.patri.plataforma.restapi.model.UF_;
import com.patri.plataforma.restapi.repository.custom.util.UtilQuery;

public class LocalidadeAssinanteRepositoryCustomImpl implements LocalidadeAssinanteRepositoryCustom
{
	private final EntityManager em;
	
	@Autowired
	public LocalidadeAssinanteRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<LocalidadeAssinante> findByAssinante(Long assinante, String parametro)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<LocalidadeAssinante> query = builder.createQuery(LocalidadeAssinante.class);
		Root<LocalidadeAssinante> root = query.from(LocalidadeAssinante.class);
		
		Predicate equalIdAssinante = builder.equal(root.get(LocalidadeAssinante_.ASSINANTE).get(Assinante_.ID), assinante);
		Predicate where = builder.and(equalIdAssinante);
		
		if(StringUtils.isNotBlank(parametro))
		{
			Join<LocalidadeAssinante, Municipio> joinMunicipio = root.join(LocalidadeAssinante_.MUNICIPIO, JoinType.LEFT);
			Join<LocalidadeAssinante, UF> joinUF = root.join(LocalidadeAssinante_.UF, JoinType.LEFT);
			
			Predicate likeMunicipio = builder.like(joinMunicipio.get(Municipio_.NOME), UtilQuery.like(parametro));
			Predicate likeUF = builder.like(joinUF.get(UF_.DESCRICAO), UtilQuery.like(parametro));
			Predicate or = builder.or(likeMunicipio, likeUF);
			where = builder.and(where, or);
		}
		
		query.where(where);
		
		TypedQuery<LocalidadeAssinante> tp = em.createQuery(query);
		return tp.getResultList();
	}
}
