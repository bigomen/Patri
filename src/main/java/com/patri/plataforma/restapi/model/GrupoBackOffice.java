package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patri.plataforma.restapi.mapper.GrupoBackOfficeMapper;
import com.patri.plataforma.restapi.restmodel.RestGrupoBackOffice;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "GRUPO_BACKOFFICE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class GrupoBackOffice extends Model<RestGrupoBackOffice> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPO_BACKOFFICE")
	@SequenceGenerator(name = "SEQ_GRUPO_BACKOFFICE", sequenceName = "SEQ_GRUPO_BACKOFFICE", allocationSize = 1)
	@EqualsAndHashCode.Include
	@Column(name = "GBO_ID")
	private Long id;
	
	@Column(name = "GBO_DESCRICAO")
	private String descricao;

	@Column(name = "GBO_NOME")
	private String nome;

	@Column(name = "GBO_DT_ALTERACAO")
	private LocalDateTime ultimaDataAlteracao;

	@ManyToMany
	@JoinTable( name = "GRUPO_BACKOFFICE_PERMISSAO",
			joinColumns = @JoinColumn(name = "GBO_ID"),
			inverseJoinColumns = @JoinColumn(name = "PER_ID"))
	private Collection<Permissao> permissoes;

	@PreUpdate
	private void preUpdate()
	{
		this.ultimaDataAlteracao = LocalDateTime.now();
	}

	@Override
	public RestGrupoBackOffice modelParaRest() {
		return GrupoBackOfficeMapper.INSTANCE.convertToRest(this);
	}
}
