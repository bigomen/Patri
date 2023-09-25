package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.OrgaoMapper;
import com.patri.plataforma.restapi.model.Orgao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe Orgão", name = "RestOrgao")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestOrgao extends RestModel<Orgao> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Schema(name = "nome", description = "Nome do orgão", required = true)
	@Size(max = 128, message ="{PTR-063}")
	@NotBlank(message = "{PTR-051}")
	@JsonProperty(value = "nome")
	private String nome;

	@Schema(name = "ordem", description = "Ordem de prioridade do orgão")
	@Min(value = 1, message = "{PTR-075}")
	@JsonProperty(value = "ordem")
	private Integer ordem;

	@Schema(name = "unidadeNegocio", description = "Unidade de negócio referente ao orgão", required = true)
	@NotNull(message = "{PTR-054}")
	private RestUnidadeNegocio unidadeNegocio;

	@Schema(name = "ativo", description = "Status do orgão")
	private boolean ativo;

	@Override
	public Orgao restParaModel()
	{
		return OrgaoMapper.INSTANCE.convertToModel(this);
	}
}
