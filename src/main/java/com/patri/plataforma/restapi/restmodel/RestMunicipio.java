package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.MunicipioMapper;
import com.patri.plataforma.restapi.model.Municipio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Schema(description = "Objeto provedor da classe Municipio", name = "RestMunicipio")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestMunicipio extends RestModel<Municipio> {

    @Schema(name = "descricao", description = "Descrição do municipio")
    @Size(max = 128, message = "{PTR-020}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Schema(name = "uf", description = "UF referente ao municipio")
    private RestUF uf;

    @Override
    public Municipio restParaModel() {
        return MunicipioMapper.INSTANCE.convertToModel(this);
    }
}
