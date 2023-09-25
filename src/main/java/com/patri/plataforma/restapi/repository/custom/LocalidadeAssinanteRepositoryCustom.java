package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import org.springframework.data.repository.query.Param;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;

public interface LocalidadeAssinanteRepositoryCustom
{
	public Collection<LocalidadeAssinante>findByAssinante(@Param(value = "idAssinante")Long idAssinante, @Param(value = "param") String parametro);
}
