package com.patri.plataforma.restapi.tasks.model.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class ReportEmail
{
    @JsonProperty(value = "titulo")
    private String titulo;

    @JsonProperty(value = "itens")
    private Collection<ItemEmail> itens;

    @JsonProperty(value = "justificativa")
    private String justificativa;
    
    @JsonProperty(value = "identificador")
    private String identificador;
}
