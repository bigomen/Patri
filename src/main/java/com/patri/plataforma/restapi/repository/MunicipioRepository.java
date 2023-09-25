package com.patri.plataforma.restapi.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.patri.plataforma.restapi.model.Localidade;
import com.patri.plataforma.restapi.model.Municipio;

@Repository
public interface MunicipioRepository extends CrudRepository<Municipio, Long>
{
	public Collection<Localidade> findByNomeContainingIgnoreCase(@Param(value = "param")String param);

	@Query("select new Municipio(m.id, m.nome) from Municipio m order by m.nome")
	Collection<Municipio> listaSimples();
}
