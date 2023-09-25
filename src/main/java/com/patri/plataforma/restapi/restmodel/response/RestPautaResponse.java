package com.patri.plataforma.restapi.restmodel.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.model.enums.TipoStatusPauta;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestModel;
import com.patri.plataforma.restapi.restmodel.RestOrgao;
import com.patri.plataforma.restapi.restmodel.RestStatusPauta;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Objeto response provedor da classe Pauta", name = "RestPautaResponse")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestPautaResponse extends RestModel<Pauta> implements Serializable
{
	private static final long serialVersionUID = 1L;

    @Schema(name = "horaInicio", description = "Hora inicial da pauta.", pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty(value = "horaInicio")
    private String horaIni;

    @Schema(name = "horaFim", description = "Hora final da pauta.", pattern = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty(value = "horaFim")
    private String horaFim;

    @JsonProperty(value = "dataInicio")
    private LocalDate dataIni;

    @JsonProperty(value = "dataFim")
    private LocalDate dataFim;

    @JsonProperty(value = "titulo")
    private String titulo;

    @JsonProperty(value = "unidadeNegocio")
    private RestUnidadeNegocio unidadeNegocio;

    @Schema(name = "orgao", description = "Orgao da pauta.")
    @JsonProperty(value = "orgao")
    private RestOrgao orgao;

    @Schema(name = "localidades", description = "Localidades da pauta.")
    @JsonProperty(value = "localidades")
    private Collection<RestLocalidade> localidades;

    @Schema(name = "reports", title = "Reports da Pauta", description = "Reports vinculados à pauta")
    @JsonProperty(value = "reports")
    private Collection<RestReportResponse> reports;

    @Schema(name = "descricao", description = "Descricao da pauta.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "tipo")
    private TipoProduto tipo;

    @JsonProperty(value = "urgente")
    private boolean urgente;

    @JsonProperty(value = "statusPauta")
    private RestStatusPauta statusPauta;

    @JsonProperty(value = "status")
    private TipoStatusPauta tipoStatus;
    
    @Schema(name = "assinantes", title = "Assinantes", description = "Assinantes vinculados à Pauta.")
    @JsonProperty(value = "assinantes")
    private Collection<RestAssinante> assinantes;
    
    @Schema(name = "usuarios", title = "Usuários", description = "Usuários assinantes vinculados à Pauta.")
    @JsonProperty(value = "usuarios")
    private Collection<RestUsuarioAssinante> usuarios;
    
    @Schema(name = "topicos", title = "Tópicos", description = "Tópicos vinculados à Pauta.")
    @JsonProperty(value = "topicos")
    private Collection<RestTopico> topicos;


    @Override
    public Pauta restParaModel()
    {
        throw new IllegalCallerException();
    }
}
