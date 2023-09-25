package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Localidades", name = "RestLocalidade")
@Data
public class RestLocalidade
{
	@Schema(name = "id", description = "Código identificador da localidade", required = true)
	private String id;
	
	@Schema(name = "nome", description = "Nome do estado ou município")
	private String nome;
	
	@Schema(name = "tipo", description = "Tipo da localidade.", required = true, allowableValues = "{MUN, UF}")
	private TipoLocalidade tipo;
}
