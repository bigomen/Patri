package com.patri.plataforma.restapi.tasks.model.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PautaEmail
{
    @JsonProperty(value = "titulo")
    private String titulo;

    @JsonProperty(value = "descricao")
    private String descricao;
}
