package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoProduto
{
	A("Assinante"),
	T("Tópico");
	
	private String descricao;
}
