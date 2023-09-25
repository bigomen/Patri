package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoLocalidade
{
	MUN("Municipio"),
	UF("Estado");
	
	private String descricao;
}
