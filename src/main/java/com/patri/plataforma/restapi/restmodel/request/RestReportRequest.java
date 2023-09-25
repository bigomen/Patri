package com.patri.plataforma.restapi.restmodel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.LocalidadeReport;
import com.patri.plataforma.restapi.model.Municipio;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.UF;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.model.enums.TipoStatusReport;
import com.patri.plataforma.restapi.restmodel.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

@Schema(description = "Objeto request provedor da classe Report.", name = "RestReportRequest")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestReportRequest extends RestModel<Report> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "titulo", description = "Título do report", required = true)
	@Size(max = 128, message ="{PTR-063}")
	@JsonProperty(value = "titulo")
    private String titulo;
    
	@Schema(name = "identificador", description = "Identificador do report", required = true)
	@Size(max = 128, message ="{PTR-063}")
	@JsonProperty(value = "identificador")
    private String identificador;

	@Schema(name = "newsletter", required = true)
	@JsonProperty(value = "newsletter")
    private Boolean newsletter;

	@Schema(name = "uri", description = "URI do report", required = true)
	@Size(max = 128, message ="{PTR-063}")
	@JsonProperty(value = "uri")
    private String uri;
    
	@Schema(name = "tipo", description = "Tipo do report", required = true, allowableValues = "{A, T}")
	@JsonProperty(value = "tipo")
    private TipoProduto tipo;
    
	@Schema(name = "urgente", required = true)
	@JsonProperty(value = "urgente")
    private boolean urgente = false;
	
	@Schema(name = "justificativa")
	private String justificativa;
	
	@Schema(name = "topicos")
	@JsonProperty(value = "topicos")
	private Collection<RestTopico> topicos;

	@Schema(name = "assinantes")
	@JsonProperty(value = "assinantes")
    private Collection<RestAssinante> assinantes;
    
	@Schema(name = "localidades")
	@JsonProperty(value = "localidades")
    private Collection<RestLocalidade> localidades;
    
	@Schema(name = "itens", description = "Itens do report", required = true)
	@JsonProperty(value = "itens")
    private Collection<RestItemReportRequest> itens;
    
	@Schema(name = "status", required = true)
	@JsonProperty(value = "status")
    private TipoStatusReport status;
    
	@Schema(name = "unidadeNegocio")
	@JsonProperty(value = "unidadeNegocio")
    private RestUnidadeNegocio unidadeNegocio;
    
	@Schema(name = "orgao")
	@JsonProperty(value = "orgao")
    private RestOrgao orgao;

	@Override
	public Report restParaModel()
	{
		Report report = ReportMapper.INSTANCE.convertToModel(this);

		this.itens.stream()
			.map(RestItemReportRequest::restParaModel)
			.collect(Collectors.toList())
			.forEach(report::adicionaItem);
		
		if(this.localidades != null)
		{
			report.setLocalidades(this.localidades.stream()
					.map(this::convertToLocalidade)
					.collect(Collectors.toList()));
			report.getLocalidades().forEach(localidadeReport -> localidadeReport.setReport(report));
		}
		
		if(this.assinantes != null)
		{
			this.assinantes.stream()
			.map(RestAssinante::restParaModel)
			.collect(Collectors.toList())
			.forEach(report::adicionaAssinante);
		}
		
		if(this.topicos != null)
		{
			this.topicos.stream()
			.map(RestTopico::restParaModel)
			.collect(Collectors.toList())
			.forEach(report::adcionaTopico);
		}
		
		return report;
	}

	private LocalidadeReport convertToLocalidade(RestLocalidade localidade)
	{
		Long id = UtilSecurity.decryptId(localidade.getId());

		if(localidade.getTipo().equals(TipoLocalidade.MUN))
		{
			Municipio municipio = new Municipio();
			municipio.setId(id);
			return new LocalidadeReport(null, null, municipio, null);
		}

		UF uf = new UF();
		uf.setId(id);
		return new LocalidadeReport(null, null, null, uf);
	}
}
