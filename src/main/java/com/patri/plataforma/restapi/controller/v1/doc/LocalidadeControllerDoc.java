package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Collection;

public interface LocalidadeControllerDoc
{
	@Operation
	(
			method = "GET",
			tags = SwaggerConstantes.LOCALIDADE_TAG,
			summary = "Lista todas as localidades",
			description = "Retorna as informações de municípios e estados cadastrados na base de dados."
	)
	public Collection<RestLocalidade> consultarLocalidades(String parametro);
	
	@Operation
	(
			method = "GET",
			tags = SwaggerConstantes.LOCALIDADE_TAG,
			summary = "Lista todas as localidades",
			description = "Retorna as informações de municípios e estados vinculados a um assinante. Pode também ser informado um texto para pesquisa pela descrição da localidade"
	)
	public Collection<RestLocalidade> consultarLocalidadesAssinante(String idAssinante, String param);
}
