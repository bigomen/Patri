package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.UFMapper;
import com.patri.plataforma.restapi.model.UF;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Schema(description = "Objeto provedor da classe UF", name = "RestUF")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestUF extends RestModel<UF> {

    @Schema(name = "sigla", description = "Sigla da UF")
    @Size(max = 2, message = "{PTR-020}")
    @JsonProperty(value = "sigla")
    private String sigla;

    @Schema(name = "descricao", description = "Descricao da UF")
    @Size(max = 64, message = "{PTR-020}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Override
    public UF restParaModel() {
        return UFMapper.INSTANCE.convertToModel(this);
    }
}
