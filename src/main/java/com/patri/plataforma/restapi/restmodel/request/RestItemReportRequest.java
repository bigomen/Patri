package com.patri.plataforma.restapi.restmodel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.ItemReportMapper;
import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.model.UsuarioBackOffice;
import com.patri.plataforma.restapi.restmodel.RestModel;
import com.patri.plataforma.restapi.restmodel.RestTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Schema(description = "Objeto request provedor da classe Item de reports.", name = "RestItemReportRequest")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestItemReportRequest extends RestModel<Item> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(name = "errata", required = true)
	@JsonProperty(value = "errata")
	private Boolean errata;
	
	@Schema(name = "template", required = true)
	@JsonProperty(value = "template")
	private RestTemplate template;
	
	@Schema(name = "texto", description = "Texto para o template")
	@JsonProperty(value = "texto")
	private String texto;
	
	@Schema(name = "ordem", description = "Ordem de exibição do template")
	@JsonProperty(value = "ordem")
	private Integer ordem;

	@Override
	public Item restParaModel()
	{
		Item item = ItemReportMapper.INSTANCE.convertToModel(this);
		item.setUsuario(new UsuarioBackOffice(UtilSecurity.getIdUsuario()));
		return item;
	}
}
