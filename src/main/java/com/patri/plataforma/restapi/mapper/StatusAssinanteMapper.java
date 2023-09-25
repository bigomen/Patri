package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.StatusAssinante;
import com.patri.plataforma.restapi.restmodel.RestStatusAssinante;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface StatusAssinanteMapper
{
    StatusAssinanteMapper INSTANCE = Mappers.getMapper(StatusAssinanteMapper.class);

    public RestStatusAssinante convertToRest(StatusAssinante statusAssinante);

    public StatusAssinante convertToModel(RestStatusAssinante restStatusAssinante);
}
