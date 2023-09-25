package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.TopicoMapper;
import com.patri.plataforma.restapi.model.Topico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Schema(description = "Objeto provedor da classe Tópico.", name = "RestTopico")
@Data
@EqualsAndHashCode(callSuper = false , onlyExplicitlyIncluded = true)
public class RestTopico extends RestModel<Topico> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(name = "descricao", description = "Texto descritivo do tópico.", required = true)
    @NotBlank(message = "{PTR-022}")
    @Size(max = 128, message = "{PTR-020}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Schema(name = "topicoPai", description = "Id do tópico pai, utilizado no cadastro de subtópicos")
    @JsonProperty(value = "topicoPai")
    private String topicoPai;
    
    @Schema(name = "subTopicos", description = "Sub-Tópicos do tópico")
    @JsonProperty(value = "subTopicos")
    private Collection<RestTopico> subTopicos;
    
    @Schema(name = "qtdSubTopicos", description = "Quantidade de subtópicos de um tópico", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(value = "qtdSubTopicos")
    private Long qtdSubTopicos;

    @Override
    public Topico restParaModel()
    {
        return TopicoMapper.INSTANCE.convertToModel(this);
    }
}
