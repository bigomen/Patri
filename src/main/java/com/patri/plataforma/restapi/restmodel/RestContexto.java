package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe Contexto", name = "RestContexto")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestContexto extends BaseRestModel implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "descricao", description = "Texto descritivo do contexto", required = true )
    @Size(max = 128, message = "{PTR-020}")
    @NotBlank(message = "{PTR-022}")
    @JsonProperty(value = "descricao")
    private String descricao;
}
