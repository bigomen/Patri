package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import org.springframework.data.repository.query.Param;
import com.patri.plataforma.restapi.model.Topico;

public interface TopicoRepositoryCustom
{
	public Collection<Topico> findByAssinanteAndParam(@Param(value = "idAssinante") Long idAssinante,@Param(value = "param") String param);
}
