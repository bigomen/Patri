package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.UsuarioAssinante;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;

@Mapper(uses = {UtilSecurity.class, LocalidadeAssinanteMapper.class})
public interface UsuarioAssinanteMapper
{
	UsuarioAssinanteMapper INSTANCE = Mappers.getMapper(UsuarioAssinanteMapper.class);

	@Mappings(value = {
			@Mapping(target = "topicos", ignore = true),
			@Mapping(target = "assinante", ignore = true),
			@Mapping(target = "localidadesAssinantes", ignore = true),
			@Mapping(target = "login", ignore = true),
			@Mapping(target = "nome", ignore = true),
			@Mapping(target = "sobrenome", ignore = true),
			@Mapping(target = "cargo", ignore = true),
			@Mapping(target = "setor", ignore = true)
	})
    public RestUsuarioAssinante convertToRest(UsuarioAssinante usuario);
	
	@Mappings(value = {
			@Mapping(target = "topicos", ignore = true),
			@Mapping(target = "assinante", ignore = true)
	})
	public UsuarioAssinante convertToModel(RestUsuarioAssinante restUsuario);

}
