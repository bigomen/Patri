package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.StatusPautaMapper;
import com.patri.plataforma.restapi.model.StatusPauta;
import com.patri.plataforma.restapi.model.enums.TipoStatusPauta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.stream.Stream;

@Schema(description = "Objeto provedor da classe StatusPauta.", name = "RestStatusPauta")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestStatusPauta extends RestModel<StatusPauta> implements Serializable
{
    @Schema(name = "descricao", description = "Descrição do Status da Pauta.")
    @Size(max = 64, message = "{PTR-020}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonIgnore
    private RestStatusPauta statusPauta;

    @JsonProperty(value = "status")
    private TipoStatusPauta tipoStatus;

    public TipoStatusPauta getTipoStatus()
    {
        Stream<TipoStatusPauta> values = Stream.of(TipoStatusPauta.values());

        return values.filter(st -> st.getEncriptedId().equals(this.getId())).findFirst().orElse(null);
    }

    public void setTipoStatus(TipoStatusPauta tipoStatus)
    {
        RestStatusPauta status = new RestStatusPauta();

        status.setId(tipoStatus.getEncriptedId());
        status.setDescricao(status.getDescricao());

        this.statusPauta = status;
        this.tipoStatus = tipoStatus;
    }

    @Override
    public StatusPauta restParaModel()
    {
        return StatusPautaMapper.INSTANCE.convertToModel(this);
    }
}
