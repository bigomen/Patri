package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.UF;
import com.patri.plataforma.restapi.restmodel.RestUF;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface UFMapper {

    UFMapper INSTANCE = Mappers.getMapper(UFMapper.class);

    public RestUF convertToRest(UF uf);

    public UF convertToModel(RestUF restUF);
}
