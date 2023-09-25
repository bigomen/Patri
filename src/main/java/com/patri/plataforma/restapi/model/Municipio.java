package com.patri.plataforma.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.mapper.MunicipioMapper;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestMunicipio;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "MUNICIPIO")
@Immutable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Municipio extends Model<RestMunicipio> implements Localidade
{
	private static final String SEPARADOR = ", ";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MUN_ID")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "MUN_DESCRICAO")
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UF_ID", referencedColumnName = "UF_ID")
	private UF uf;

	public Municipio(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public RestMunicipio modelParaRest() {
		return MunicipioMapper.INSTANCE.convertToRest(this);
	}

	@Override
	public TipoLocalidade getTipo()
	{
		return TipoLocalidade.MUN;
	}

	@Override
	public String getLocalidade()
	{
		return this.nome.concat(SEPARADOR).concat(this.uf.getSigla());
	}

	@Override
	public RestLocalidade toRestLocalidade()
	{
		return LocalidadeMapper.INSTANCE.convertToRest(this);
	}
}
