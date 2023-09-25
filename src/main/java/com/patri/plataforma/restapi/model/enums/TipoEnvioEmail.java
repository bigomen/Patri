package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoEnvioEmail
{
	D("Direto"),
	N("Newsletter");
	
	@Getter
	private String descricao;
}
