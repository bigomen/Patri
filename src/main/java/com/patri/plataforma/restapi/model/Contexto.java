package com.patri.plataforma.restapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "CONTEXTO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contexto extends BaseModel implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTEXTO")
    @SequenceGenerator(name = "SEQ_CONTEXTO", sequenceName = "SEQ_CONTEXTO", allocationSize = 1)
    @Column(name = "CON_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "CON_DESCRICAO")
    private String descricao;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<ReportContexto> reportContextos;

}
