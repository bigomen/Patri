package com.patri.plataforma.restapi.repository.custom;

import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.model.Item_;
import com.patri.plataforma.restapi.model.Report_;
import com.patri.plataforma.restapi.model.StatusReport_;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom
{
	private final EntityManager em;

	@Autowired
	public ItemRepositoryCustomImpl(EntityManager em)
	{
		super();
		this.em = em;
	}

	@Override
	public Collection<Item> historicoItens(Long idReport) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = builder.createQuery(Item.class);
		Root<Item> root = criteriaQuery.from(Item.class);
		Predicate where = builder.equal(root.get(Item_.ATIVO), false);

		if(idReport != null) {
			Predicate equalId = builder.equal(root.get(Item_.REPORT), idReport);
			where = builder.and(where, equalId);
		}

		criteriaQuery.where(where);
		criteriaQuery.orderBy(builder.desc(root.get(Item_.DATA)), builder.asc(root.get(Item_.TEMPLATE)));
		TypedQuery<Item> typedQuery = em.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
}
