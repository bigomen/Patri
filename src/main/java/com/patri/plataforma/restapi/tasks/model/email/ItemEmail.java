package com.patri.plataforma.restapi.tasks.model.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemEmail
{
    @JsonProperty(value = "texto")
    private String texto;

    @JsonProperty(value = "template")
    private TemplateEmail template;
}
