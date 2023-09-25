package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.Municipio;
import com.patri.plataforma.restapi.restmodel.RestMunicipio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface MunicipioMapper {

    MunicipioMapper INSTANCE = Mappers.getMapper(MunicipioMapper.class);

    @Mapping(target = "descricao", source = "nome")
    public RestMunicipio convertToRest(Municipio municipio);

    @Mapping(source = "descricao", target = "nome")
    public Municipio convertToModel(RestMunicipio restMunicipio);
}
