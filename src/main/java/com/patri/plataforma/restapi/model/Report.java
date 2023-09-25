package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
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
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.restmodel.response.RestReportResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REPORT")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Where(clause = "SRE_ID <> 3")
public class Report extends Model<RestReportResponse> implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT")
    @SequenceGenerator(name = "SEQ_REPORT", sequenceName = "SEQ_REPORT", allocationSize = 1)
    @Column(name = "REP_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "REP_TITULO")
    private String titulo;
    
    @Column(name = "REP_IDENTIFICADOR")
    private String identificador;

    @Column(name = "REP_NEWSLETTER")
    private Boolean newsletter;

    @Column(name = "REP_DATA", updatable = false)
    private LocalDateTime data;

    @Column(name = "REP_URI")
    private String uri;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "REP_ASSINANTE_TOPICO")
    private TipoProduto tipo;
    
    @Column(name = "REP_URGENTE")
    private boolean urgente;
    
    //APÓS REALIZADA QUALQUER ALTERAÇÃO NO REPORT QUE SEJA JUSTIFICADA NESSE CAMPO, HÁ UMA TRIGGER NO BANCO QUE LIMPA A FLAG 
    //QUE O REPORT JÁ FOI ENVIADO POR EMAIL E MUDA O STATUS DO REPORT PARA EM ATUALIZAÇÃO
    @Column(name = "REP_JUSTIFICATIVA_ALTERACAO")
    private String justificativa;

    @Column(name = "REP_DT_ALTERACAO", insertable = false)
    private LocalDateTime ultimaDataAlteracao;

    @ManyToMany
    @JoinTable( name = "REPORT_TOPICO",
                joinColumns = @JoinColumn(name = "REP_ID"),
                inverseJoinColumns = @JoinColumn(name = "TOP_ID"))
    private List<Topico> topicos;
    
    @ManyToMany
    @JoinTable( name = "ASSINANTE_REPORT",
            joinColumns = @JoinColumn(name = "REP_ID"),
            inverseJoinColumns = @JoinColumn(name = "ASN_ID")
    )
    private Set<Assinante> assinantes;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "report")
    private Collection<LocalidadeReport> localidades;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "report")
    private List<Item> itens;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SRE_ID")
    private StatusReport status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNE_ID")
    private UnidadeNegocio unidadeNegocio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID")
    private Orgao orgao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAT_ID")
    private Pauta pauta;

    @Column(name = "REP_ENVIO_EMAIL")
    private boolean enviado;
    
    @Column(name = "REP_DT_ENVIO_EMAIL")
    private LocalDateTime dataEnvioEmail;
    
    @PrePersist
    protected void preInsert()
    {
        this.data = LocalDateTime.now();
        this.enviado = false;
        this.status = new StatusReport(1L);
    }
    
    @PreUpdate
    protected void preUpdate()
    {
    	this.ultimaDataAlteracao = LocalDateTime.now();
    }
    
    public void adicionaItem(Item item)
    {
    	if(this.itens == null)
    	{
    		this.itens = new CopyOnWriteArrayList<>();
    	}
    	
    	item.setReport(this);
    	this.itens.add(item);
    }
    
    public void adicionaLocalidade(LocalidadeReport localidade)
    {
    	if(this.localidades == null)
    	{
    		this.localidades = new ArrayList<>();
    	}
    	
    	localidade.setReport(this);
    	this.localidades.add(localidade);
    }
    
    public void adcionaTopico(Topico topico)
    {
    	if(this.topicos == null)
    	{
    		this.topicos = new ArrayList<>();
    	}
    	
    	this.topicos.add(topico);
    }
    
    public void adicionaAssinante(Assinante assinante)
    {
    	if(this.assinantes == null)
    	{
    		this.assinantes = new HashSet<>();
    	}
    	
    	this.assinantes.add(assinante);
    }
    
    public boolean isAtivo()
    {
    	return this.status.equals(new StatusReport(1L));
    }

    @Override
    public RestReportResponse modelParaRest()
    {
        RestReportResponse response = ReportMapper.INSTANCE.convertToRest(this);

        if (this.localidades != null) {
            response.setLocalidades(this.localidades.stream().map(LocalidadeReport::toRestLocalidade).collect(Collectors.toSet()));
        }

        return response;
    }
}
