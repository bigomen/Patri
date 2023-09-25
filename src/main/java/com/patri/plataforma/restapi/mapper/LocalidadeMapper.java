package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Localidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;

@Mapper(uses = {UtilSecurity.class})
public interface LocalidadeMapper
{
	LocalidadeMapper INSTANCE = Mappers.getMapper(LocalidadeMapper.class);
	
	@Named(value = "localidade")
	@Mappings(
			value = {
					@Mapping(source = "localidade", target = "nome"),
			}
	)
	public RestLocalidade convertToRest(Localidade localidade);
}
