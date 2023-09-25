package com.patri.plataforma.restapi.mapper;

import com.patri.plataforma.restapi.model.UsuarioBackOffice;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface UsuarioBackOfficeMapper {

    UsuarioBackOfficeMapper INSTANCE = Mappers.getMapper(UsuarioBackOfficeMapper.class);

    @Mappings(value = {
            @Mapping(target = "grupo.permissoes", ignore = true),
    })
    public RestUsuarioBackOffice convertToRest(UsuarioBackOffice usuario);

    @Mappings(value = {
            @Mapping(target = "grupo.descricao", ignore = true),
            @Mapping(target = "grupo.permissoes", ignore = true),
            @Mapping(target = "unidadeNegocio.descricao", ignore = true),
            @Mapping(target = "unidadeNegocio.corApresentacao", ignore = true),
            @Mapping(target = "unidadeNegocio.logo", ignore = true),
            @Mapping(target = "unidadeNegocio.unidadeAtiva", ignore = true)
    })
    public RestUsuarioBackOffice convertToSimpleRest(UsuarioBackOffice usuario);

    public UsuarioBackOffice convertToModel(RestUsuarioBackOffice restUsuario);
}
