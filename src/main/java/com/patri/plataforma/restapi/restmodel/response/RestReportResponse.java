package com.patri.plataforma.restapi.restmodel.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.enums.TipoStatusReport;
import com.patri.plataforma.restapi.restmodel.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

@Schema(description = "Objeto response provedor da classe Report.", name = "RestReportResponse")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestReportResponse extends RestModel<Report> implements Serializable, Comparable<RestReportResponse>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "Titulo do Report", required = true)
    @JsonProperty(value = "titulo")
    private String titulo;
    
    @Schema(name = "Identificador do report")
    private String identificador;
    
    @Schema(name = "Justificativa do report")
    private String justificativa;
    
    @Schema(name = "Indicador de priorização do report")
    private boolean urgente;
    
    @Schema(name = "Estado atual do report")
    @JsonIgnore
    private RestStatusReport status;

    @JsonProperty(value = "status")
    private TipoStatusReport tipoStatus;
    
    @JsonProperty(value = "orgao")
    private RestOrgao orgao;
    
    @JsonProperty(value = "unidadeNegocio")
    private RestUnidadeNegocio unidade;
    
    @JsonProperty(value = "itens")
    private Collection<RestItemReportResponse> itens;
    
    @JsonProperty(value = "topicos")
    private Collection<RestTopico> topicos;
    
    @JsonProperty(value = "localidades")
    private Collection<RestLocalidade> localidades;
    
    @JsonProperty(value = "assinantes")
    private Collection<RestAssinante> assinantes;

    public TipoStatusReport getTipoStatus()
    {
        Stream<TipoStatusReport> streamValues = Stream.of(TipoStatusReport.values());

        return streamValues.filter(st -> st.getEncriptedId()
                .equals(this.status.getId())).findFirst().orElse(null);
    }

    public void setTipoStatus(TipoStatusReport tipoStatus)
    {
        RestStatusReport status = new RestStatusReport();

        status.setId(tipoStatus.getEncriptedId());
        status.setDescricao(tipoStatus.getDescricao());

        this.status = status;
        this.tipoStatus = tipoStatus;
    }

    @Override
    public Report restParaModel()
    {
       throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public int compareTo(RestReportResponse rest)
    {
        return StringUtils.compareIgnoreCase(this.titulo, rest.getTitulo());
    }
}
