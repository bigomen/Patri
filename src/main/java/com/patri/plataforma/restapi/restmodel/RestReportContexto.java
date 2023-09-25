package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe ReportContexto.", name = "RestReportContexto")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestReportContexto extends BaseRestModel implements Serializable
{
    @Schema(name = "texto", description = "Texto do Produto_Contexto")
    @Size(max = 128, message = "{PTR-020}")
    @NotBlank(message = "{PTR-030}")
    @JsonProperty(value = "texto")
    private String texto;

    @Schema(name = "ordem", description = "Ordem do Produto_Contexto")
    @Max(value = 99, message = "{PTR-020}")
    @NotNull(message = "{PTR-031}")
    @JsonProperty(value = "ordem")
    private Long ordem;

    @NotNull(message = "{PTR-032}")
    @JsonProperty(value = "report")
    private String reportId;

    @NotNull(message = "{PTR-033}")
    @JsonProperty(value = "contexto")
    private String contextoId;
}
