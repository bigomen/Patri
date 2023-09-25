package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.StatusUsuario;
import com.patri.plataforma.restapi.restmodel.RestStatusUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface StatusUsuarioMapper
{
    StatusUsuarioMapper INSTANCE = Mappers.getMapper(StatusUsuarioMapper.class);

    public RestStatusUsuario convertToRest(StatusUsuario statusUsuario);

    public StatusUsuario convertToModel(RestStatusUsuario restStatusUsuario);
}
