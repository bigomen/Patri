package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.TopicoUsuarioAssinante;
import com.patri.plataforma.restapi.restmodel.RestTopicoUsuario;

@Mapper(uses = {UtilSecurity.class, TopicoMapper.class})
public interface TopicoUsuarioMapper
{
	TopicoUsuarioMapper INSTANCE = Mappers.getMapper(TopicoUsuarioMapper.class);

	@Mappings({
		@Mapping(source = "assinante.id", target = "assinante"),
		@Mapping(source = "usuarioAssinante.id", target = "usuario"),
		@Mapping(source = "topico.id", target = "topico")
	})
    public RestTopicoUsuario convertToRest(TopicoUsuarioAssinante topico);

	@Mappings({
		@Mapping(target = "assinante.id", source = "assinante"),
		@Mapping(target = "usuarioAssinante.id", source = "usuario"),
		@Mapping(target = "topico.id", source = "topico")
	})
    public TopicoUsuarioAssinante convertToModel(RestTopicoUsuario restTopico);

}
