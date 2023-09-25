package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.StatusReportMapper;
import com.patri.plataforma.restapi.model.StatusReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe StatusProduto.", name = "RestStatusProduto")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestStatusReport extends RestModel<StatusReport> implements Serializable
{
    @Schema(name = "descricao", description = "Descrição do Status do Produto")
    @Size(max = 128, message = "{PTR-020")
    @NotBlank(message = "{PTR-022}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Override
    public StatusReport restParaModel()
    {
        return StatusReportMapper.INSTANCE.convertToModel(this);
    }
}
