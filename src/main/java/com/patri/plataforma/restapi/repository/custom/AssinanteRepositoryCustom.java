package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import org.springframework.data.repository.query.Param;
import com.patri.plataforma.restapi.model.Assinante;

public interface AssinanteRepositoryCustom
{
	public Collection<Assinante> findAll(@Param(value = "param") String param);
}
