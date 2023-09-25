package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.PlanoMapper;
import com.patri.plataforma.restapi.model.enums.TipoAssinante;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "ASSINANTE")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Assinante extends Model<RestAssinante>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ASSINANTE")
	@SequenceGenerator(name = "SEQ_ASSINANTE", sequenceName = "SEQ_ASSINANTE", allocationSize = 1)
	@Column(name = "ASN_ID")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "ASN_NOME_RAZAO_SOCIAL")
	private String nome;

	@Column(name = "ASN_DOCUMENTO")
	private String documento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ASN_TIPO")
	private TipoAssinante tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLA_ID")
	private Plano plano;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assinante")
	private Collection<UsuarioAssinante> usuarioAssinantes;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ASN_ID", referencedColumnName = "ASN_ID")
	private Collection<LocalidadeAssinante> localidades = new ArrayList<>();

	@Column(name = "ASN_DESCRICAO")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SAS_ID")
	private StatusAssinante status;

	@Column(name = "ASN_DT_ALTERACAO")
	private LocalDateTime ultimaDataAlteracao;

	@ManyToMany
	@JoinTable( name = "TOPICO_ASSINANTE",
				joinColumns = @JoinColumn(name = "ASN_ID"),
				inverseJoinColumns = @JoinColumn(name = "TOP_ID"))
	private Set<Topico> topicos;
	
	@ManyToMany(mappedBy = "assinantes")
	private Set<Report> reports;
	
	@ManyToMany(mappedBy = "assinantes")
	private Set<Pauta> pautas;
	
	public Assinante(Long id, String nome)
	{
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public Assinante (Long id, String nome, Long status)
	{
		this.id = id;
		this.nome = nome;
		this.status = new StatusAssinante(status);
	}

	@PrePersist
	private void preInsert()
	{
		this.status = new StatusAssinante(1L);
		this.ultimaDataAlteracao = LocalDateTime.now();
	}

	@PreUpdate
	private void preUpdate()
	{
		this.ultimaDataAlteracao = LocalDateTime.now();
	}

	@Override
	public RestAssinante modelParaRest() 
	{
		RestAssinante restAssinante = new RestAssinante();
		restAssinante.setId(UtilSecurity.encryptId(this.id));
		restAssinante.setDescricao(this.descricao);
		restAssinante.setDocumento(this.documento);
		restAssinante.setNome(nome);
		restAssinante.setPlano(PlanoMapper.INSTANCE.convertToSimpleRest(this.plano));
		restAssinante.setStatus(this.status.modelParaRest());
		restAssinante.setUltimaDataAlteracao(this.ultimaDataAlteracao);
		restAssinante.setTipo(tipo);
		
		if(this.usuarioAssinantes != null)
		{
			restAssinante.setUsuarios(this.usuarioAssinantes.stream()
					.map(u -> u.simpleModelParaRest())
					.collect(Collectors.toList()));
		}

		if(this.topicos != null)
		{
			restAssinante.setTopicos(this.topicos.stream().map(t -> t.modelParaRest()).collect(Collectors.toList()));
		}
		
		if(this.localidades != null)
		{
			restAssinante.setLocalidades(this.localidades.stream().map(LocalidadeAssinante::toRestLocalidade).collect(Collectors.toSet()));
		}
		
		return restAssinante;
	}

}
