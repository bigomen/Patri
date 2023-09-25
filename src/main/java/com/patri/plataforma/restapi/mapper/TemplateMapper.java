package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Template;
import com.patri.plataforma.restapi.restmodel.RestTemplate;

@Mapper(uses = {UtilSecurity.class})
public interface TemplateMapper
{
	TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    public RestTemplate convertToRest(Template template);

    public Template convertToModel(RestTemplate restTemplate);
}
