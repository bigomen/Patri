package com.patri.plataforma.restapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRODUTO_CONTEXTO")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReportContexto extends BaseModel implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT_CONTEXTO")
    @SequenceGenerator(name = "SEQ_REPORT_CONTEXTO", sequenceName = "SEQ_REPORT_CONTEXTO", allocationSize = 1)
    @Column(name = "PCO_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "RCO_TEXTO")
    private String texto;

    @Column(name = "RCO_ORDEM")
    private Long ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REP_ID", insertable = false, updatable = false)
    private Report report;

    @Column(name = "REP_ID")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CON_ID", insertable = false, updatable = false)
    private Contexto contexto;

    @Column(name = "RCO_ID")
    private Long contextoId;
}
