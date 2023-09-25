package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.PlanoMapper;
import com.patri.plataforma.restapi.model.Plano;
import com.patri.plataforma.restapi.model.enums.TipoStatusPlano;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Stream;

@Schema(description = "Objeto provedor da classe Plano.", name = "RestPlano")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestPlano extends RestModel<Plano> implements Serializable, Comparable<RestPlano>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "descricao", description = "Texto descritivo do plano.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Schema(hidden = true)
    @JsonIgnore
    private RestStatusPlano status;

    @Schema(name = "qtdLocal", description = "Quantidade de localidades em um plano.")
    @Max(value = 999, message = "{PTR-020}")
    @JsonProperty(value = "qtdLocal")
    private Integer qtdLocalidades;

    @Schema(name = "qtdUsuarios", description = "Quantidade de usuários do assinante.", required = true)
    @Max(value = 999, message = "{PTR-020}")
    @NotNull(message = "{PTR-083}")
    @JsonProperty(value = "qtdUsuarios")
    private Integer qtdUsuarios;

    @Schema(name = "report", description = "Reports do plano. Por Padrão o valor é falso.", required = true)
    @NotNull(message = "{PTR-080")
    @JsonProperty(value = "report")
    private Boolean report = false;

    @Schema(name = "agenda", description = "Agendas do plano. Por Padrão o valor é falso.", required = true)
    @NotNull(message = "{PTR-081}")
    @JsonProperty(value = "agenda")
    private Boolean agenda = false;

    @Schema(name = "sintese", description = "Sintese do plano. Por Padrão o valor é falso.", required = true)
    @NotNull(message = "{PTR-082")
    @JsonProperty(value = "sintese")
    private Boolean sintese = false;

    @Schema(name = "nome", description = "Nome do plano", required = true)
    @Size(max = 30, message = "{PTR-020}")
    @NotNull(message = "{PTR-051}")
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "status")
    public TipoStatusPlano tipoStatus;

    @Schema(name = "unidades", description = "Unidades de Negócio referentes ao plano.")
    @NotNull(message = "{PTR-054")
    @JsonProperty(value = "unidades")
    private Collection<RestUnidadeNegocio> unidades;

    @Override
	public Plano restParaModel()
	{
		return PlanoMapper.INSTANCE.convertToModel(this);
	}
    
    public TipoStatusPlano getTipoStatus()
    {
    	if(this.status == null)
    	{
    		return null;
    	}
    	
    	Stream<TipoStatusPlano> streamStatus = Stream.of(TipoStatusPlano.values());
    	
    	return streamStatus.filter(st -> st.getEncriptedId()
                .equals(this.status.getId())).findFirst().orElse(null);
    }
    
    public void setTipoStatus(TipoStatusPlano tipoStatus)
    {
    	RestStatusPlano status = new RestStatusPlano();
    	status.setId(tipoStatus.getEncriptedId());
    	status.setDescricao(tipoStatus.getDescricao());
    	this.status = status;
    	this.tipoStatus = tipoStatus;
    }

    @Override
    public int compareTo(RestPlano restPlano)
    {
        return StringUtils.compareIgnoreCase(this.nome, restPlano.getNome());
    }

}
