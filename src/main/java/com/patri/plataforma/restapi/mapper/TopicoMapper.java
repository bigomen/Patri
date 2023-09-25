package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface TopicoMapper
{
    TopicoMapper INSTANCE = Mappers.getMapper(TopicoMapper.class);

    @Named(value = "topico")
    @Mappings({
    	@Mapping(target = "topicoPai", source = "pai.id")
    })
    public RestTopico convertToRest(Topico topico);

    @Mapping(target = "topicoPai", ignore = true)
    public RestTopico convertToSimpleRest(Topico topico);

    public Topico convertToModel(RestTopico restTopico);
}
