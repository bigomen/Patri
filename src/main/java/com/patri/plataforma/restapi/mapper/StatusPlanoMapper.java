package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.StatusPlano;
import com.patri.plataforma.restapi.restmodel.RestStatusPlano;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface StatusPlanoMapper
{
    StatusPlanoMapper INSTANCE = Mappers.getMapper(StatusPlanoMapper.class);

    public RestStatusPlano convertToRest(StatusPlano statusPlano);

    public StatusPlano convertToModel(RestStatusPlano restStatusPlano);
}
