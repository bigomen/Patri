package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.patri.plataforma.restapi.mapper.PlanoMapper;
import com.patri.plataforma.restapi.restmodel.RestPlano;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PLANO")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Plano extends Model<RestPlano> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO")
	@SequenceGenerator(name = "SEQ_PLANO", sequenceName = "SEQ_PLANO", allocationSize = 1)
	@Column(name = "PLA_ID", nullable = false)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "PLA_DESCRICAO")
	private String descricao;

	@Column(name = "PLA_QTD_LOCALIDADE")
	private Integer qtdLocalidades;

	@Column(name = "PLA_QTD_USUARIO_ASSINANTE")
	private Integer qtdUsuarios;

	@Column(name = "PLA_REPORT")
	private Boolean report;

	@Column(name = "PLA_AGENDA")
	private Boolean agenda;

	@Column(name = "PLA_SINTESE")
	private Boolean sintese;

	@Column(name = "PLA_NOME")
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPL_ID")
	private StatusPlano status;

	@ManyToMany
	@JoinTable(
			name = "PLANO_UNIDADE_NEGOCIO",
			joinColumns = @JoinColumn(name = "PLA_ID"),
			inverseJoinColumns = @JoinColumn(name = "UNE_ID")
	)
	private Collection<UnidadeNegocio> unidades;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plano")
	private Collection<Assinante> assinantes;

	@Column(name = "PLA_DT_ALTERACAO")
	private LocalDateTime ultimaDataAlteracao;

	@PrePersist
	private void prePersist()
	{
		this.status = new StatusPlano(1L);
	}

	@PreUpdate
	private void preUpdate()
	{
		this.ultimaDataAlteracao = LocalDateTime.now();
	}

	public Boolean adicionaUnidade(UnidadeNegocio unidade)
	{
		if(this.unidades == null)
		{
			this.unidades = new HashSet<>();
		}

		return this.unidades.add(unidade);
	}

	@Override
	public RestPlano modelParaRest()
	{
		return PlanoMapper.INSTANCE.convertToRest(this);
	}
}
