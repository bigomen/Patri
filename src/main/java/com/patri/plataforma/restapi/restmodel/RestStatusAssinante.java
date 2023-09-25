package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.StatusAssinanteMapper;
import com.patri.plataforma.restapi.model.StatusAssinante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Objeto provedor da classe StatusAssinante.", name = "RestStatusStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestStatusAssinante extends RestModel<StatusAssinante> implements Serializable
{
    @Schema(name = "descricao", description = "Descrição do estado atual do assinante.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Override
    public StatusAssinante restParaModel()
    {
        return StatusAssinanteMapper.INSTANCE.convertToModel(this);
    }
}
