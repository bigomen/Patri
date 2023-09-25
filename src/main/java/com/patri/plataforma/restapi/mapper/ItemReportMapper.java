package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.restmodel.request.RestItemReportRequest;
import com.patri.plataforma.restapi.restmodel.response.RestItemReportResponse;

@Mapper(uses = {UtilSecurity.class, TemplateMapper.class, UsuarioBackOfficeMapper.class})
public interface ItemReportMapper
{
	ItemReportMapper INSTANCE = Mappers.getMapper(ItemReportMapper.class);

	@Mappings(value = {
			@Mapping(target = "usuario.grupo", ignore = true),
			@Mapping(target = "usuario.unidadeNegocio", ignore = true)
	})
    public RestItemReportResponse convertToRest(Item item);

	@Named(value = "simpleItens")
	@Mappings(value = {
			@Mapping(target = "usuario", ignore = true),
			@Mapping(target = "dataAtualizacao", ignore = true)
	})
	RestItemReportResponse convertItens(Item item);

    public Item convertToModel(RestItemReportRequest request);
}
