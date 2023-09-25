package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.LocalidadePauta;
import com.patri.plataforma.restapi.restmodel.RestLocalidadePauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class, MunicipioMapper.class, UFMapper.class})
public interface LocalidadePautaMapper
{
    LocalidadePautaMapper INSTANCE = Mappers.getMapper(LocalidadePautaMapper.class);

    @Mapping(target = "pauta", ignore = true)
    public RestLocalidadePauta convertToRest(LocalidadePauta localidadePautaMapper);

    @Mapping(target = "pauta", ignore = true)
    public LocalidadePauta convertToModel(RestLocalidadePauta restLocalidadePauta);
}
