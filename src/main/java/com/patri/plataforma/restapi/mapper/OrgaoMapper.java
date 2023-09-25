package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Orgao;
import com.patri.plataforma.restapi.restmodel.RestOrgao;

@Mapper(uses = {UtilSecurity.class, UnidadeNegocioMapper.class})
public interface OrgaoMapper
{
	OrgaoMapper INSTANCE = Mappers.getMapper(OrgaoMapper.class);
	
	@Mappings(value = {
			@Mapping(target = "unidadeNegocio.logo", ignore = true),
			@Mapping(target = "unidadeNegocio.descricao", ignore = true),
			@Mapping(target = "unidadeNegocio.unidadeAtiva", ignore = true),
			@Mapping(target = "unidadeNegocio.corApresentacao", ignore = true)
	})
	public RestOrgao convertToRest(Orgao orgao);
	 
    public Orgao convertToModel(RestOrgao restOrgao);
}
