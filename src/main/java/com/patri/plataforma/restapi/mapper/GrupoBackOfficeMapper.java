package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.GrupoBackOffice;
import com.patri.plataforma.restapi.restmodel.RestGrupoBackOffice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface GrupoBackOfficeMapper {

    GrupoBackOfficeMapper INSTANCE = Mappers.getMapper(GrupoBackOfficeMapper.class);


    @Mapping(target = "permissoes", ignore = true)
    public RestGrupoBackOffice convertToRest(GrupoBackOffice grupo);

    public GrupoBackOffice convertToModel(RestGrupoBackOffice restGrupo);
}
