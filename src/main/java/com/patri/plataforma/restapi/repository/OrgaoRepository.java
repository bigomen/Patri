package com.patri.plataforma.restapi.repository;

import java.util.Collection;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Orgao;
import com.patri.plataforma.restapi.repository.custom.OrgaoRepositoryCustom;

@Repository
public interface OrgaoRepository extends CrudRepository<Orgao, Long>, OrgaoRepositoryCustom
{
	@Query(value = "select o from Orgao o join fetch o.unidadeNegocio un where un.id = :id")
	public Collection<Orgao> pesquisarOrgaoPorUnidadeNegocio(@Param(value = "id")Long idUnidade);

	@Query("select new Orgao(o.id, o.nome) from Orgao o where o.ativo = true order by o.nome")
	Collection<Orgao> listaSimples();
}
