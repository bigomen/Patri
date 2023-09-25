package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;

@Mapper(uses = {UtilSecurity.class})
public interface UnidadeNegocioMapper 
{
	UnidadeNegocioMapper INSTANCE = Mappers.getMapper(UnidadeNegocioMapper.class);

	public RestUnidadeNegocio convertToRest(UnidadeNegocio unidadeNegocio);

	public UnidadeNegocio convertToModel(RestUnidadeNegocio restUnidadeNegocio);
}
