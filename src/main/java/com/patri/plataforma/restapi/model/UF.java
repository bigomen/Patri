package com.patri.plataforma.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.mapper.UFMapper;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestUF;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "UF")
@Immutable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UF extends Model<RestUF> implements Localidade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "UF_ID")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "UF_SIGLA")
	private String sigla;
	
	@Column(name = "UF_DESCRICAO")
	private String descricao;

	@Override
	public RestUF modelParaRest() {
		return UFMapper.INSTANCE.convertToRest(this);
	}

	@Override
	public String getLocalidade()
	{
		return this.descricao;
	}

	@Override
	public TipoLocalidade getTipo()
	{
		return TipoLocalidade.UF;
	}

	@Override
	public RestLocalidade toRestLocalidade()
	{
		return LocalidadeMapper.INSTANCE.convertToRest(this);
	}
}
