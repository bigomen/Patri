package com.patri.plataforma.restapi.tasks.model.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TemplateEmail
{
    @JsonProperty(value = "descricao")
    private String descricao;
}
