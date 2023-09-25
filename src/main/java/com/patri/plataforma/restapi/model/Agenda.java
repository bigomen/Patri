package com.patri.plataforma.restapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "AGENDA")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Agenda extends BaseModel implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AGENDA")
    @SequenceGenerator(name = "SEQ_AGENDA", sequenceName = "SEQ_AGENDA", allocationSize = 1)
    @Column(name = "AGE_ID", nullable = false)
    private Long id;

    @Column(name = "AGE_TITULO")
    private String titulo;

    @Column(name = "AGE_TEXTO")
    private String texto;

    @ManyToMany
    @JoinTable(name = "AGENDA_PAUTA",
               joinColumns = @JoinColumn(name = "AGE_ID"),
               inverseJoinColumns = @JoinColumn(name = "PAT_ID")
    )
    private Collection<Pauta> pautas;

}
