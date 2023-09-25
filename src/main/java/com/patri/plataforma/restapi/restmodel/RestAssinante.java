package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.StatusAssinanteMapper;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.LocalidadeAssinante;
import com.patri.plataforma.restapi.model.Municipio;
import com.patri.plataforma.restapi.model.UF;
import com.patri.plataforma.restapi.model.enums.TipoAssinante;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.model.enums.TipoStatusAssinante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Schema(description = "Objeto provedor da classe Assinante", name = "RestAssinante")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestAssinante extends RestModel<Assinante> implements Serializable, Comparable<RestAssinante>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(name = "nome", description = "Nome razão social do Assinante", required = true)
	@Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
	@Size(max = 128, message ="{PTR-063}")
	@NotBlank(message = "{PTR-201}")
	@JsonProperty(value = "nome")
	private String nome;

	@Schema(name = "documento", description = "Documento de identificação do assinante")
	@Size(max = 14, message = "{PTR-020}")
	@JsonProperty(value = "documento")
	private String documento;

	@Schema(name = "tipo", description = "Tipo de assinante", allowableValues = "J: Jurídico, F: Físico", required = true)
	@NotNull(message = "{PTR-040}")
	@JsonProperty(value = "tipo")
	private TipoAssinante tipo;

	@Schema(hidden = true)
	@JsonIgnore
	private RestStatusAssinante status;

	@Schema(name = "descricao", description = "Descricao do assinante", required = true)
	@Size(max = 256, message = "{PTR-065}")
	@JsonProperty(value = "descricao")
	private String descricao;

	@JsonProperty(value = "status")
	public TipoStatusAssinante tipoStatus;

	@Schema(name = "data", description = "Última data de alteração", required = true)
	@JsonProperty(value = "data")
	private LocalDateTime ultimaDataAlteracao;

	@Schema(name = "plano", description = "Plano referente ao assinante.", required = true)
	@NotNull(message = "Obrigatório informar um plano")
	@JsonProperty(value = "plano")
	private RestPlano plano;

	@Schema(name = "localidades", description = "Número limite de localidades para o assinante")
	@JsonProperty("localidades")
	private Collection<RestLocalidade> localidades;

	@Schema(name = "topicos", description = "Tópicos referentes ao assinante")
	@JsonProperty(value = "topicos")
	private Collection<RestTopico> topicos;

	@Schema(name = "usuarios", description = "Usuários pertencentes ao assinante.")
	@JsonProperty(value = "usuarios")
	private Collection<RestUsuarioAssinante> usuarios;

	public TipoStatusAssinante getTipoStatus()
	{
		Stream<TipoStatusAssinante> streamStatus = Stream.of(TipoStatusAssinante.values());
		
		if(this.status == null)
		{
			return null;
		}

		return streamStatus.filter(st -> st.getEncriptedId().equals(this.status.getId())).findFirst().orElse(null);
	}

	public void setTipoStatus(TipoStatusAssinante tipoStatus)
	{
		RestStatusAssinante status = new RestStatusAssinante();
		status.setId(tipoStatus.getEncriptedId());
		status.setDescricao(tipoStatus.getDescricao());
		this.status = status;
		this.tipoStatus = tipoStatus;
	}

	@Override
	public Assinante restParaModel() 
	{
		Assinante assinante = new Assinante();
		assinante.setId(UtilSecurity.decryptId(getId()));
		assinante.setDescricao(descricao);
		assinante.setDocumento(documento);

		if (this.usuarios != null) {
			assinante.setUsuarioAssinantes(this.usuarios.stream()
					.map(RestUsuarioAssinante::restParaModel)
					.collect(Collectors.toList()));
			assinante.getUsuarioAssinantes().forEach(usuarioAssinante -> usuarioAssinante.setAssinante(assinante));
		}

		if(this.localidades != null)
		{
			assinante.setLocalidades(this.localidades.stream().map(this::convertToLocalidade).collect(Collectors.toList()));
			assinante.getLocalidades().forEach(localidade -> localidade.setAssinante(assinante));
		}
		
		assinante.setNome(nome);
		if (plano != null) {
			assinante.setPlano(plano.restParaModel());
		}
		if(this.topicos != null)
		{
			assinante.setTopicos(this.topicos.stream().map(t -> t.restParaModel()).collect(Collectors.toSet()));
		}

		assinante.setTipo(tipo);
		assinante.setStatus(StatusAssinanteMapper.INSTANCE.convertToModel(this.status));
		return assinante;
	}

	@Override
	public int compareTo(RestAssinante restAssinante)
	{
		return this.getNome().compareTo(restAssinante.getNome());
	}
	
	private LocalidadeAssinante convertToLocalidade(RestLocalidade localidade)
	{
		Long id = UtilSecurity.decryptId(localidade.getId());
		
		if(localidade.getTipo().equals(TipoLocalidade.MUN))
		{
			Municipio municipio = new Municipio();
			municipio.setId(id);
			return new LocalidadeAssinante(null, null, municipio, null, null);
		}
		
		UF uf = new UF();
		uf.setId(id);
		return new LocalidadeAssinante(null, null, null, uf, null);
	}
}
