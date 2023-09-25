package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.TemplateMapper;
import com.patri.plataforma.restapi.model.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Objeto provedor da classe Template", name = "RestTemplate")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestTemplate extends RestModel<Template>
{
	@Schema(name = "descricao", description = "Descricao do template")
	@JsonProperty(value = "descricao")
	private String descricao;

	@Override
	public Template restParaModel()
	{
		return TemplateMapper.INSTANCE.convertToModel(this);
	}
}
