package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.LocalidadeReport;
import com.patri.plataforma.restapi.restmodel.RestLocalidadeReport;

@Mapper(uses = {UtilSecurity.class, MunicipioMapper.class, UFMapper.class})
public interface LocalidadeReportMapper {

    LocalidadeReportMapper INSTANCE = Mappers.getMapper(LocalidadeReportMapper.class);

    public RestLocalidadeReport convertToRest(LocalidadeReport localidadeReport);

    public LocalidadeReport convertToModel(RestLocalidadeReport restLocalidadeReport);
}
