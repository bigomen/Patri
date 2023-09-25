package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.StatusUsuarioMapper;
import com.patri.plataforma.restapi.model.StatusUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Objeto provedor da classe StatusUsuario.", name = "RestStatusUsuario")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class RestStatusUsuario extends RestModel<StatusUsuario> implements Serializable
{

    @Schema(name = "descricao", description = "Descrição do estado atual do usuario.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Override
    public StatusUsuario restParaModel()
    {
        return StatusUsuarioMapper.INSTANCE.convertToModel(this);
    }
}
