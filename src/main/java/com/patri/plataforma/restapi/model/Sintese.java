package com.patri.plataforma.restapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "SINTESE")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Sintese extends BaseModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SINTESE")
    @SequenceGenerator(name = "SEQ_SINTESE", sequenceName = "SEQ_SINTESE", allocationSize = 1)
    @Column(name = "SIN_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SIN_TITULO")
    private String titulo;

    @Column(name = "SIN_TEXTO")
    private String texto;

    @ManyToMany
    @JoinTable( name = "SINTESE_REPORT",
                joinColumns = @JoinColumn(name = "SIN_ID"),
                inverseJoinColumns = @JoinColumn(name = "REP_ID")
    )
    private Collection<Report> reports;
}
