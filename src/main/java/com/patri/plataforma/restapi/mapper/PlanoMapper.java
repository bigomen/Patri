package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Plano;
import com.patri.plataforma.restapi.restmodel.RestPlano;

@Mapper(uses = {UtilSecurity.class, UnidadeNegocioMapper.class, StatusPlanoMapper.class})
public interface PlanoMapper 
{
	PlanoMapper INSTANCE = Mappers.getMapper(PlanoMapper.class);

	public RestPlano convertToRest(Plano plano);

	@Mappings(value = {
			@Mapping(target = "unidades", ignore = true)
	})
	public RestPlano convertToSimpleRest(Plano plano);

	@Mapping(source = "id", target = "id")
	public Plano convertToModel(RestPlano restPlano);
}
