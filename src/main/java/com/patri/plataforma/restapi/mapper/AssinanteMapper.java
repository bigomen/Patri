package com.patri.plataforma.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.Plano;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.restmodel.RestPlano;

@Mapper(uses = {UtilSecurity.class, PlanoMapper.class, StatusAssinanteMapper.class, TopicoUsuarioMapper.class, TopicoMapper.class, LocalidadeMapper.class})
public interface AssinanteMapper {

    AssinanteMapper INSTANCE = Mappers.getMapper(AssinanteMapper.class);

    @Mappings(value = {
    		@Mapping(target = "ultimaDataAlteracao", ignore = true),
    		@Mapping(target = "localidades", ignore = true),
    		@Mapping(target = "topicos", ignore = true),
    		@Mapping(target = "usuarios", ignore = true),
    		@Mapping(target = "plano", qualifiedByName = "plano")
    })
    public RestAssinante convertToRest(Assinante assinante);

	@Named("toSimpleRest")
    @Mappings(value = {
    		@Mapping(target = "ultimaDataAlteracao", ignore = true),
    		@Mapping(target = "localidades", ignore = true),
    		@Mapping(target = "topicos", ignore = true),
    		@Mapping(target = "usuarios", ignore = true),
    		@Mapping(target = "plano", ignore = true)
    })
    public RestAssinante convertToSimpleRest(Assinante assinante);

    public Assinante convertToModel(RestAssinante restAssinante);

	@Named(value = "plano")
	@Mappings(value = {
			@Mapping(target = "descricao", ignore = true),
			@Mapping(target = "qtdLocalidades", ignore = true),
			@Mapping(target = "report", ignore = true),
			@Mapping(target = "agenda", ignore = true),
			@Mapping(target = "sintese", ignore = true),
			@Mapping(source = "qtdUsuarios", target = "qtdUsuarios"),
			@Mapping(target = "status", ignore = true),
			@Mapping(target = "unidades", ignore = true)
	})
	RestPlano convertePlano(Plano plano);

}
