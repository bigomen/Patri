package com.patri.plataforma.restapi.repository;

import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.repository.custom.PautaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>, PautaRepositoryCustom
{

}
