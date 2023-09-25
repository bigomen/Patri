package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.UnidadeNegocioMapper;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Schema(description = "Objeto provedor da classe UnidadeNegocio", name = "RestUnidadeNegocio")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestUnidadeNegocio extends RestModel<UnidadeNegocio> implements Serializable, Comparable<RestUnidadeNegocio>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Schema(name = "descricao", description = "Texto descritivo da Unidade de Negócio", required = true)
    @Size(max = 128, message = "{PTR-063}")
    @NotBlank(message = "{PTR-047}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Schema(name = "nome", description = "Nome da Unidade de Negócio.", required = true)
    @NotBlank(message = "{PTR-051}")
    @Size(max = 40, message = "{PTR-072}")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "cor", description = "Cor de apresentação da Unidade de Negócio.", required = true)
    @Size(max = 7, message = "{PTR-020}")
    @NotBlank(message = "{PTR-057}")
    @JsonProperty(value = "cor")
    private String corApresentacao;

    @Schema(name = "logo", description = "Logo da Unidade de Negócio.", required = true)
    @NotNull(message = "{PTR-058}")
    @JsonProperty(value = "logo")
    private byte[] logo;

    @Schema(name = "uneAtivada", description = "Campo que informa se o plano está ativo ou inativo. Por padrão o plano está ativo.")
    @JsonProperty(value = "uneAtivada")
    private Boolean unidadeAtiva;
    
    @Override
    public UnidadeNegocio restParaModel()
    {
        return UnidadeNegocioMapper.INSTANCE.convertToModel(this);
    }

    @Override
    public int compareTo(RestUnidadeNegocio rest)
    {
        return StringUtils.compareIgnoreCase(this.nome, rest.getNome());
    }
}
