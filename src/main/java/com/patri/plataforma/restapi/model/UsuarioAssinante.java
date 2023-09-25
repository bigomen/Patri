/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.patri.plataforma.restapi.mapper.StatusAssinanteMapper;
import com.patri.plataforma.restapi.mapper.TopicoMapper;
import com.patri.plataforma.restapi.model.enums.TipoEnvioEmail;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author rcerqueira
 */
@Entity
@Table(name = "USUARIO_ASSINANTE")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@DynamicInsert
@DynamicUpdate
public class UsuarioAssinante extends Model<RestUsuarioAssinante> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO_ASSINANTE")
	@SequenceGenerator(name = "SEQ_USUARIO_ASSINANTE", sequenceName = "SEQ_USUARIO_ASSINANTE", allocationSize = 1)
	@Column(name = "UAS_ID", nullable = false)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "UAS_NOME")
	private String nome;

	@Column(name = "UAS_SOBRENOME")
	private String sobrenome;

	@Column(name = "UAS_LOGIN")
	private String login;

	@Column(name = "UAS_SENHA")
	private String senha;

	@Column(name = "UAS_SETOR")
	private String setor;
	
	@Column(name = "UAS_CARGO")
	private String cargo;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASN_ID")
	private Assinante assinante;

	@Column(name = "UAS_ULTIMO_LOGIN")
	private LocalDateTime ultimoAcesso;

	@Column(name = "UAS_VALIDADE_TOKEN_RESET")
	private LocalDateTime validadeTokenReset;

	@Column(name = "UAS_TOKEN_RESET")
	private String tokenReset;

	@Column(name = "UAS_ATIVO")
	private boolean ativo;

	@Column(name = "UAS_DT_ALTERACAO")
	private LocalDateTime ultimaDataAlteracao;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "UAS_NEWSLETTER_DIRETO")
    private TipoEnvioEmail envioEmail;

	@ManyToMany
	@JoinTable( name = "LOCALIDADE_USUARIO_ASSINANTE",
				joinColumns = @JoinColumn(name = "UAS_ID"),
				inverseJoinColumns = @JoinColumn(name = "LAS_ID"))
	private Collection<LocalidadeAssinante> localidadesAssinantes;

	@OneToMany(mappedBy = "usuarioAssinante", cascade = CascadeType.MERGE)
	private Collection<TopicoUsuarioAssinante> topicos;

	@ManyToMany(mappedBy = "assinantes")
	private Collection<Report> reports;

	@ManyToMany(mappedBy = "usuarios")
	private Collection<Pauta> pautas;
	
	public UsuarioAssinante()
	{
		super();
	}
	
	public UsuarioAssinante(Long id, String nome, String login)
	{
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
	}

	public boolean adicionaTopico(Topico topico, Assinante assinante, UsuarioAssinante usuario)
	{
		if(this.topicos == null)
		{
			this.topicos = new ArrayList<>();
		}

		return this.topicos.add(new TopicoUsuarioAssinante(assinante, topico,  usuario));
	}

	@PrePersist
	private void insert()
	{
		this.ativo = false;
		this.senha = " ";
	}

	@PreUpdate
	protected void dataUltimoAcesso()
	{
		this.ultimaDataAlteracao = LocalDateTime.now();
	}

	@Override
	public RestUsuarioAssinante modelParaRest() {
		RestUsuarioAssinante restUsuarioAssinante = new RestUsuarioAssinante();
		restUsuarioAssinante.setId(UtilSecurity.encryptId(this.getId()));
		restUsuarioAssinante.setLogin(this.login);
		restUsuarioAssinante.setNome(this.nome);
		restUsuarioAssinante.setSobrenome(this.sobrenome);
		restUsuarioAssinante.setCargo(this.cargo);
		restUsuarioAssinante.setSetor(this.setor);
		restUsuarioAssinante.setEnvioEmail(this.envioEmail);
		restUsuarioAssinante.setAtivo(this.ativo);

		RestAssinante restAssinante = new RestAssinante();
		restAssinante.setId(UtilSecurity.encryptId(this.assinante.getId()));
		restAssinante.setNome(this.assinante.getNome());
		restAssinante.setDocumento(this.assinante.getDocumento());
		restAssinante.setTipo(this.assinante.getTipo());
		if (this.assinante.getDescricao() != null) {
			restAssinante.setDescricao(this.assinante.getDescricao());
		}
		restAssinante.setStatus(StatusAssinanteMapper.INSTANCE.convertToRest(this.assinante.getStatus()));
		restUsuarioAssinante.setAssinante(restAssinante);

		
		List<RestTopico> listaTopicos = new ArrayList<>();

		for (TopicoUsuarioAssinante topicoUsuario : this.topicos)
		{
			RestTopico restTopico = TopicoMapper.INSTANCE.convertToSimpleRest(topicoUsuario.getTopico());
			listaTopicos.add(restTopico);
		}
		restUsuarioAssinante.setTopicos(listaTopicos);

		return restUsuarioAssinante;
	}

	public RestUsuarioAssinante simpleModelParaRest() {
		RestUsuarioAssinante restUsuarioAssinante = new RestUsuarioAssinante();
		restUsuarioAssinante.setId(UtilSecurity.encryptId(this.getId()));
		restUsuarioAssinante.setLogin(this.login);
		restUsuarioAssinante.setNome(this.nome);
		restUsuarioAssinante.setSobrenome(this.sobrenome);
		restUsuarioAssinante.setCargo(this.cargo);
		restUsuarioAssinante.setSetor(this.setor);
		restUsuarioAssinante.setAtivo(this.ativo);
		restUsuarioAssinante.setUltimaAlteracao(this.ultimaDataAlteracao);
		restUsuarioAssinante.setEnvioEmail(this.envioEmail);

		return restUsuarioAssinante;
	}


	public TipoPermissao getTipoPermissaoAcesso()
	{
		return TipoPermissao.C;
	}
}

