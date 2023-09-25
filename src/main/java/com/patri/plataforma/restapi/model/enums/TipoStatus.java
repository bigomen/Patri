package com.patri.plataforma.restapi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum TipoStatus 
{
	A("Ativo"),
	B("Bloqueado"),
	C("Cancelado"),
	I("Inativo"),
	P("Pendente");
	
	private String descricao;

//	@JsonCreator
//	public static TipoStatus toString(final String descricao)
//	{
//		return Stream.of(TipoStatus.values()).filter(targetEnum -> targetEnum.descricao.equals(descricao)).findFirst().orElse(null);
//	}
//
//	@JsonValue
//	public String getDescricao()
//	{
//		return descricao;
//	}
//
//	public static TipoStatus toEnum(String descricao) throws IllegalAccessException
//	{
//		if(descricao == null)
//		{
//			return null;
//		}
//
//		for(TipoStatus x : TipoStatus.values())
//		{
//			if(descricao.equals(x.getDescricao()))
//			{
//				return x;
//			}
//		}
//
//		throw new IllegalAccessException("Status inválido: " + descricao);
//	}

}
