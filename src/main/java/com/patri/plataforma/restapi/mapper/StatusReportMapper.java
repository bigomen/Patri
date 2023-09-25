package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.StatusReport;
import com.patri.plataforma.restapi.restmodel.RestStatusReport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface StatusReportMapper
{
    StatusReportMapper INSTANCE = Mappers.getMapper(StatusReportMapper.class);

    public RestStatusReport convertToRest(StatusReport statusReport);

    public StatusReport convertToModel(RestStatusReport restStatusReport);
}
