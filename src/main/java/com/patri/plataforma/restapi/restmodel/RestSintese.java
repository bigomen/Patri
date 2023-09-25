package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.model.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;

@Schema(description = "Objeto provedor da classe Sintese.", name = "RestSintese")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestSintese extends BaseRestModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Schema(name = "titulo", description = "Titulo da sintese.", required = true)
    @JsonProperty(value = "titulo")
    private String titulo;

    @Schema(name = "texto", description = "Texto da sintese.")
    @JsonProperty(value = "texto")
    private String texto;

    @Schema(name = "reports", description = "Reports referentes a sintese.")
    private Collection<Report> reports;
}
