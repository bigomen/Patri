package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.restmodel.request.RestPautaRequest;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;
import com.patri.plataforma.restapi.tasks.model.email.PautaEmail;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UnidadeNegocioMapper.class, ReportMapper.class, StatusPautaMapper.class, TopicoMapper.class, OrgaoMapper.class,
        LocalidadeMapper.class, UsuarioAssinanteMapper.class, AssinanteMapper.class, UtilSecurity.class})
public interface PautaMapper
{
    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);

    @Mappings(value = {
    		@Mapping(target = "id", expression = "java(String.valueOf(pauta.getId()))"),
    		@Mapping(target = "reports", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, qualifiedByName = "toSimpleReport"),
    		@Mapping(target = "unidadeNegocio.logo", ignore = true),
    		@Mapping(target = "unidadeNegocio.corApresentacao", ignore = true),
    		@Mapping(target = "orgao.unidadeNegocio", ignore = true)
    })
    public RestPautaResponse convertToRest(Pauta pauta);

    @Mappings(value = {
    		@Mapping(target = "id", expression = "java(String.valueOf(pauta.getId()))"),
            @Mapping(target = "unidadeNegocio", ignore = true),
    		@Mapping(target = "orgao", ignore = true),
    		@Mapping(target = "localidades", ignore = true),
    		@Mapping(target = "reports", ignore = true),
    		@Mapping(target = "statusPauta", ignore = true),
    		@Mapping(target = "assinantes", ignore = true),
    		@Mapping(target = "topicos", ignore = true)
    })
    public RestPautaResponse convertToSimpleRest(Pauta pauta);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "toPautaId"),
            @Mapping(target = "localidades", ignore = true),
            @Mapping(target = "topicos", ignore = true),
            @Mapping(target = "assinantes", ignore = true),
            @Mapping(target = "usuarios", ignore = true)
    })
    public Pauta convertToModel(RestPautaRequest rest);

    PautaEmail convertToTemplateEmail(Pauta pauta);
    
    @Named(value = "toPautaId")
    default Long toPautaId(String id)
    {
    	if(StringUtils.isNotBlank(id))
    	{
    		return Long.parseLong(id);
    	}
    	
    	return null;
    }
}
