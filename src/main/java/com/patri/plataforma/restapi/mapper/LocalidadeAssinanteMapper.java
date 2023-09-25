package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import com.patri.plataforma.restapi.restmodel.RestLocalidadeAssinante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class, MunicipioMapper.class, UFMapper.class})
public interface LocalidadeAssinanteMapper {

    LocalidadeAssinanteMapper INSTANCE = Mappers.getMapper(LocalidadeAssinanteMapper.class);

    @Mapping(target = "assinante", ignore = true)
    public RestLocalidadeAssinante convertToRest(LocalidadeAssinante localidadeAssinante);

    @Mapping(target = "assinante", ignore = true)
    public LocalidadeAssinante convertToModel(RestLocalidadeAssinante restLocalidadeAssinante);
}
