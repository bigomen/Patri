package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GRUPO_BACKOFFICE_PERMISSAO")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GrupoBackOfficePermissao implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BACKOFFICE_PERMISSAO")
	@SequenceGenerator(name = "SEQ_BACKOFFICE_PERMISSAO", sequenceName = "SEQ_GRUPO_BACKOFFICE_PERMISSAO", allocationSize = 1)
	@Column(name = "GBP_ID", nullable = false)
	@EqualsAndHashCode.Include
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GBO_ID")
	private GrupoBackOffice grupo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PER_ID")
	private Permissao permissao;
}
