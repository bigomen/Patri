package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.mapper.LocalidadeReportMapper;
import com.patri.plataforma.restapi.model.LocalidadeReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Objeto provedor da classe Localidade Report", name = "RestLocalidadeReport")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestLocalidadeReport extends RestModel<LocalidadeReport>
{
    @Schema(name = "municipio", description = "Municipio referente a Localidade do Assinante.")
    private RestMunicipio municipio;

    @Schema(name = "uf", description = "UF referente a Localidade do Assinante.")
    private RestUF uf;

    @Override
    public LocalidadeReport restParaModel() {
        return LocalidadeReportMapper.INSTANCE.convertToModel(this);
    }
}

