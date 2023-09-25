package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.mapper.LocalidadeAssinanteMapper;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "Objeto provedor da classe LocalidadeAssinante", name = "RestLocalidadeAssinante")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestLocalidadeAssinante extends RestModel<LocalidadeAssinante> {

    @Schema(name = "assinante", description = "Assinante referente a Localidade do Assinante.", required = true)
    @NotNull(message = "{PTR-021}")
    private RestAssinante assinante;

    @Schema(name = "municipio", description = "Municipio referente a Localidade do Assinante.")
    private RestMunicipio municipio;

    @Schema(name = "uf", description = "UF referente a Localidade do Assinante.")
    private RestUF uf;

    @Override
    public LocalidadeAssinante restParaModel() {
        return LocalidadeAssinanteMapper.INSTANCE.convertToModel(this);
    }
}
