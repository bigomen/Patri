package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.StatusPlanoMapper;
import com.patri.plataforma.restapi.model.StatusPlano;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Objeto provedor da classe StatusPlano.", name = "RestStatusPlano")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class RestStatusPlano extends RestModel<StatusPlano> implements Serializable
{

    @Schema(name = "descricao", description = "Descrição do estado atual do plano.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Override
    public StatusPlano restParaModel()
    {
        return StatusPlanoMapper.INSTANCE.convertToModel(this);
    }
}
