package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.*;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import com.patri.plataforma.restapi.restmodel.request.RestReportRequest;
import com.patri.plataforma.restapi.restmodel.response.RestReportResponse;
import com.patri.plataforma.restapi.tasks.model.email.ReportEmail;

@Mapper(uses = {UtilSecurity.class, LocalidadeMapper.class, UnidadeNegocioMapper.class, StatusReportMapper.class,
		TopicoMapper.class, TemplateMapper.class, ItemReportMapper.class, MunicipioMapper.class, UFMapper.class,
		OrgaoMapper.class, AssinanteMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ReportMapper
{
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);
    
    @Mappings(value = {
    		@Mapping(target = "orgao.unidadeNegocio", ignore = true),
			@Mapping(target = "itens", qualifiedByName = "simpleItens"),
			@Mapping(target = "assinantes", qualifiedByName = "toSimpleRest"),
    		@Mapping(source = "unidadeNegocio", target = "unidade", qualifiedByName = "mapUnidade")
    })
    public RestReportResponse convertToRest(Report report);
    
    @Named(value = "toSimpleReport")
    @Mappings(value = {
    		@Mapping(target = "orgao", ignore = true),
    		@Mapping(target = "unidade", ignore = true),
    		@Mapping(target = "itens", ignore = true),
    		@Mapping(target = "topicos", ignore = true),
    		@Mapping(target = "localidades", ignore = true),
    		@Mapping(target = "assinantes", ignore = true),
    })
    public RestReportResponse convertToSimpleRest(Report report);

    @Mappings(value = {
    		@Mapping(target = "localidades", ignore = true),
    		@Mapping(target = "topicos", ignore = true),
    		@Mapping(target = "itens", ignore = true),
    		@Mapping(target = "assinantes", ignore = true),
    		@Mapping(target = "ultimaDataAlteracao", ignore = true),
    		@Mapping(target = "status", ignore = true)
    })
    public Report convertToModel(RestReportRequest request);
    
    @Named(value = "mapUnidade")
    @Mappings(value = {
    		@Mapping(target = "logo", ignore = true),
    		@Mapping(target = "corApresentacao", ignore = true),
    		@Mapping(target = "descricao", ignore = true),
    		@Mapping(target = "unidadeAtiva", ignore = true)
    })
    RestUnidadeNegocio mapUnidade(UnidadeNegocio unidade);

	ReportEmail convertToTemplateEmail(Report report);
}
