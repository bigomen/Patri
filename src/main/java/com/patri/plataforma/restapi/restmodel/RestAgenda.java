package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.model.Pauta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;

@Schema(description = "Objeto provedor da classe Agenda.", name = "RestAgenda")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestAgenda extends BaseRestModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Schema(name = "titulo", description = "Titulo da agenda.", required = true)
    @JsonProperty(value = "titulo")
    private String titulo;

    @Schema(name = "texto", description = "Texto da agenda.")
    @JsonProperty(value = "texto")
    private String texto;

    @Schema(name = "pautas", description = "Pautas referentes a agenda.")
    private Collection<Pauta> pautas;
}
