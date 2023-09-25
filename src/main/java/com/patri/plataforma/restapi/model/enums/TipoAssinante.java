package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoAssinante
{
	J("Jurídica"),
	F("Física");
	
	private String descricao;
}
