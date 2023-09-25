package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.mapper.LocalidadePautaMapper;
import com.patri.plataforma.restapi.model.LocalidadePauta;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Schema(description = "Objeto provedor da classe LocalidadePauta", name = "RestLocalidaPauta")
@Data
@EqualsAndHashCode(callSuper = true)
public class RestLocalidadePauta extends RestModel<LocalidadePauta> implements Serializable
{

    @Schema(name = "pauta", description = "Assinante referente a Localidade da Pauta.", required = true)
    private RestPautaResponse pauta;

    @Schema(name = "municipio", description = "Municipio referente a Localidade da Pauta.")
    private RestMunicipio municipio;

    @Schema(name = "uf", description = "UF referente a Localidade da Pauta.")
    private RestUF uf;

    @Override
    public LocalidadePauta restParaModel()
    {
        return LocalidadePautaMapper.INSTANCE.convertToModel(this);
    }
}
