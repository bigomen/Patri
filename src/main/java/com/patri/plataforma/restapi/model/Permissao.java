package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PERMISSAO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Immutable
public class Permissao extends BaseModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISSAO")
	@SequenceGenerator(name = "SEQ_PERMISSAO", sequenceName = "SEQ_PERMISSAO", allocationSize = 1)
	@Column(name = "PER_ID")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "PER_DESCRICAO")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PER_TIPO")
	private TipoPermissao tipo;
	
	@Column(name = "PER_ROTA")
	private String rota;
}
