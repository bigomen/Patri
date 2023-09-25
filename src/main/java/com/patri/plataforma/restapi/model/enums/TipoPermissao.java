package com.patri.plataforma.restapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TipoPermissao
{
	B("BackOffice"),
	C("Cliente");
	
	private String descricao;
}
