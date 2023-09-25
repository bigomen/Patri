package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.patri.plataforma.restapi.mapper.UsuarioBackOfficeMapper;
import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIO_BACKOFFICE")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UsuarioBackOffice extends Model<RestUsuarioBackOffice> implements Serializable, Usuario
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO_BACKOFFICE")
    @SequenceGenerator(name = "SEQ_USUARIO_BACKOFFICE", sequenceName = "SEQ_USUARIO_BACKOFFICE", allocationSize = 1)
    @Column(name = "USB_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "USB_NOME")
    private String nome;

    @Column(name = "USB_SOBRENOME")
    private String sobrenome;

    @Column(name = "USB_LOGIN")
    private String login;

    @Column(name = "USB_SENHA")
    private String senha;

    @ManyToOne()
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "GBO_ID")
    private GrupoBackOffice grupo;

    @ManyToOne()
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "UNE_ID")
    private UnidadeNegocio unidadeNegocio;

    @Column(name = "USB_ATIVO")
    private Boolean ativo;

    @Column(name = "USB_DT_ALTERACAO")
    private LocalDateTime ultimaDataAlteracao;
    
	public UsuarioBackOffice(Long id)
	{
		super();
		this.id = id;
	}

    @PrePersist
    private void preInsert()
    {
    	this.ativo = false;
    	this.senha = " ";
    }

    @PreUpdate
    private void preUpdate()
    {
        this.ultimaDataAlteracao = LocalDateTime.now();
    }

    @Override
    public RestUsuarioBackOffice modelParaRest()
    {
        return UsuarioBackOfficeMapper.INSTANCE.convertToRest(this);
    }

	@Override
	public String getGrupoUsuario()
	{
		return this.grupo.getNome();
	}
	
	@Override
	public Long getGrupoId()
	{
		return this.grupo.getId();
	}

	@Override
	public List<String> getPermissoes()
	{
		return this.grupo.getPermissoes().stream()
				.map(p -> String.valueOf(p.getId()))
				.collect(Collectors.toList());
	}
	
	@Override
	public TipoPermissao getTipoPermissaoAcesso()
	{
		return TipoPermissao.B;
	}
}

