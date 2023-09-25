package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;
import com.patri.plataforma.restapi.mapper.TopicoMapper;
import com.patri.plataforma.restapi.restmodel.RestTopico;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "TOPICO")
@Where(clause = "TOP_ATIVO")
public class Topico extends Model<RestTopico> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
    @Column(name = "TOP_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TOPICO")
    @SequenceGenerator(name = "SEQ_TOPICO", sequenceName = "SEQ_TOPICO", allocationSize = 1)
    private Long id;

    @Column(name = "TOP_DESCRICAO")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOP_ID_PAI", referencedColumnName = "TOP_ID")
    private Topico pai;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pai")
    private Collection<Topico> subTopicos;
    
    @Setter(value = AccessLevel.NONE)
    @Formula(value = "(SELECT COUNT(SUB.TOP_ID) FROM TOPICO SUB WHERE SUB.TOP_ID_PAI = TOP_ID)")
    private Long qtdSubTopicos;
    
    @Column(name = "TOP_DT_ALTERACAO")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "TOP_ATIVO")
    private Boolean ativo;
    
    @ManyToMany(mappedBy = "topicos")
    private Set<Assinante> assinantes;
    
    @ManyToMany(mappedBy = "topicos")
    private Set<Pauta> pautas;
    
    public boolean isRoot()
    {
    	return this.pai == null;
    }
    
    public boolean isSubTopico()
    {
    	return this.pai != null;
    }

    public Topico(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @PrePersist
    private void prePersist()
    {
    	this.dataAtualizacao = LocalDateTime.now();
    	this.ativo = true;
    }
    
    @PreUpdate
    private void preUpdate()
    {
    	this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public RestTopico modelParaRest()
    {
    	return TopicoMapper.INSTANCE.convertToRest(this);
    }
}
