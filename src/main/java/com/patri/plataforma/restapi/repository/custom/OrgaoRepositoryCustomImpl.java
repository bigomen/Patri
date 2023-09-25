package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.patri.plataforma.restapi.model.Orgao;
import com.patri.plataforma.restapi.model.Orgao_;
import com.patri.plataforma.restapi.repository.custom.util.UtilQuery;

public class OrgaoRepositoryCustomImpl implements OrgaoRepositoryCustom
{
	private final EntityManager em;
	
	@Autowired
	public OrgaoRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<Orgao> pesquisarOrgaos(String param)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Orgao> query = builder.createQuery(Orgao.class);
		Root<Orgao> root = query.from(Orgao.class);
		root.fetch(Orgao_.UNIDADE_NEGOCIO);

		if(StringUtils.isNotBlank(param)) 
		{
			Predicate likeNome = builder.like(builder.upper(root.get(Orgao_.NOME)), UtilQuery.like(param.toUpperCase()));
			query.where(likeNome);
		}

		query.orderBy(builder.asc(root.get(Orgao_.ordem)));

		TypedQuery<Orgao> tp = em.createQuery(query);
		return tp.getResultList();
	}
}
