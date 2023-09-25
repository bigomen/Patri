package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.StatusPauta;
import com.patri.plataforma.restapi.restmodel.RestStatusPauta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface StatusPautaMapper
{
    StatusPautaMapper INSTANCE = Mappers.getMapper(StatusPautaMapper.class);

    public RestStatusPauta convertToRest(StatusPauta statusPauta);

    public StatusPauta convertToModel(RestStatusPauta restStatusPauta);
}
