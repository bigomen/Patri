package com.patri.plataforma.restapi.restmodel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.restmodel.RestModel;
import com.patri.plataforma.restapi.restmodel.RestTemplate;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "Objeto response provedor da classe Item dos reports.", name = "RestItemReportResponse")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestItemReportResponse  extends RestModel<Item> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "texto", description = "Texto do item")
	@JsonProperty(value = "texto")
	private String texto;
	
	@Schema(name = "ordem", description = "Ordem de exibição do item")
	@JsonProperty(value = "ordem")
	private Integer ordem;

	@Schema(name = "Data atualização", description = "Data da última atualização do item")
	@JsonProperty(value = "dataAtualizacao")
	private LocalDateTime dataAtualizacao;
	
	@Schema(name = "errata", description = "Flag indicativa se o item é ou não uma errata")
	@JsonProperty(value = "errata")
	private Boolean errata;
	
	@Schema(name = "template", description = "Template utilizado")
	@JsonProperty(value = "template")
	private RestTemplate template;
	
	@Schema(name = "usuario", description = "Usuário responsável pelas informações")
	@JsonProperty(value = "usuario")
	private RestUsuarioBackOffice usuario;

	@Override
	public Item restParaModel()
	{
		throw new RuntimeException("NOT IMPLEMENTED");
	}
}
