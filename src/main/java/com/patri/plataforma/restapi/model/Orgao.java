package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.OrgaoMapper;
import com.patri.plataforma.restapi.restmodel.RestOrgao;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORGAO")
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Orgao extends Model<RestOrgao>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORGAO")
	@SequenceGenerator(name = "SEQ_ORGAO", sequenceName = "SEQ_ORGAO", allocationSize = 1)
	@Column(name = "ORG_ID")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "ORG_DESCRICAO")
	private String nome;
	
	@Column(name = "ORG_ORDEM")
	private Integer ordem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNE_ID", referencedColumnName = "UNE_ID")
	private UnidadeNegocio unidadeNegocio;
	
	@Column(name = "ORG_DT_ALTERACAO")
	@Setter(value = AccessLevel.NONE)
	private LocalDateTime dataAlteracao;
	
	@Column(name = "ORG_ATIVO")
	private boolean ativo;

	public Orgao(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@PrePersist
	private void prePersist()
	{
		this.ativo = true;
		this.dataAlteracao = LocalDateTime.now();
	}
	
	@PreUpdate
	private void preUpdate()
	{
		this.dataAlteracao = LocalDateTime.now();
	}

	@Override
	public RestOrgao modelParaRest()
	{
		return OrgaoMapper.INSTANCE.convertToRest(this);
	}
}