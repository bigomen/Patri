package com.patri.plataforma.restapi.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TOPICO_USUARIO_ASSINANTE")
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TopicoUsuarioAssinante
{
    @EmbeddedId
    @AttributeOverrides(value =
    {
        @AttributeOverride(name = "idAssinante", column = @Column(name = "ASN_ID")),
        @AttributeOverride(name = "idTopico", column = @Column(name = "TOP_ID")),
        @AttributeOverride(name = "idUsuario", column = @Column(name = "UAS_ID"))
    })
    private TopicoUsuarioAssinanteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASN_ID", insertable = false, updatable = false)
    private Assinante assinante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOP_ID", insertable = false, updatable = false)
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UAS_ID", insertable = false, updatable = false)
    private UsuarioAssinante usuarioAssinante;

    public TopicoUsuarioAssinante(Assinante assinante, Topico topico, UsuarioAssinante usuarioAssinante)
    {
        this.assinante = assinante;
        this.topico = topico;
        this.usuarioAssinante = usuarioAssinante;
        this.id = new TopicoUsuarioAssinanteId(assinante.getId(), topico.getId(),usuarioAssinante.getId());
    }
}
