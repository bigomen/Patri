package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.hibernate.annotations.Where;
import com.patri.plataforma.restapi.mapper.PautaMapper;
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PAUTA")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Where(clause = "SPA_ID <> 3")
public class Pauta extends Model<RestPautaResponse> implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAUTA")
    @SequenceGenerator(name = "SEQ_PAUTA", sequenceName = "SEQ_PAUTA", allocationSize = 1)
    @Column(name = "PAT_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "PAT_DESCRICAO")
    private String descricao;

    @Column(name = "PAT_HORA_INICIO")
    private LocalTime horaIni;

    @Column(name = "PAT_HORA_FIM")
    private LocalTime horaFim;

    @Column(name = "PAT_DATA_INICIO")
    private LocalDate dataIni;

    @Column(name = "PAT_DATA_FIM")
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNE_ID")
    private UnidadeNegocio unidadeNegocio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pauta")
    private Collection<Report> reports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPA_ID")
    private StatusPauta statusPauta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID")
    private Orgao orgao;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAT_ASSINANTE_TOPICO")
    private TipoProduto tipo;

    @Column(name = "PAT_TITULO")
    private String titulo;

    @ManyToMany
    @JoinTable(name = "USUARIO_ASSINANTE_PAUTA",
               joinColumns = @JoinColumn(name = "PAT_ID"),
               inverseJoinColumns = @JoinColumn(name = "UAS_ID"))
    private Set<UsuarioAssinante> usuarios;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "pauta")
    private Collection<LocalidadePauta> localidades;

    @ManyToMany
    @JoinTable( name = "PAUTA_TOPICO",
                joinColumns = @JoinColumn(name = "PAT_ID"),
                inverseJoinColumns = @JoinColumn(name = "TOP_ID"))
    private Set<Topico> topicos;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ASSINANTE_PAUTA",
            joinColumns = @JoinColumn(name = "PAT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ASN_ID"))
    private Set<Assinante> assinantes;

    @Column(name = "PAT_DT_ALTERACAO")
    private LocalDateTime ultimaDataAlteracao;

    @Column(name = "PAT_URGENTE")
    private boolean urgente;

    @PrePersist
    protected void preInsert()
    {
        this.statusPauta = new StatusPauta(2L);
        this.ultimaDataAlteracao = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate()
    {
        this.ultimaDataAlteracao = LocalDateTime.now();
    }

    public void adicionaLocalidade(LocalidadePauta localidadePauta)
    {
        if(this.localidades == null)
        {
            this.localidades = new ArrayList<>();
        }

        localidadePauta.setPauta(this);
        this.localidades.add(localidadePauta);
    }

    @Override
    public RestPautaResponse modelParaRest()
    {
        RestPautaResponse restPautaResponse = PautaMapper.INSTANCE.convertToRest(this);

        if (this.localidades != null) {
            restPautaResponse.setLocalidades(this.localidades.stream().map(LocalidadePauta::toRestLocalidade).collect(Collectors.toSet()));
        }

        return restPautaResponse;
    }

}
