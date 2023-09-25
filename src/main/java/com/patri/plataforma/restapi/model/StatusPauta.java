package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.StatusPautaMapper;
import com.patri.plataforma.restapi.restmodel.RestStatusPauta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATUS_PAUTA")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StatusPauta extends Model<RestStatusPauta> implements Serializable
{

    @Id
    @Column(name = "SPA_ID", nullable = false)
    private Long id;

    @Column(name = "SPA_DESCRICAO")
    private String descricao;

    public StatusPauta(Long id)
    {
        this.id = id;
    }

    @Override
    public RestStatusPauta modelParaRest()
    {
        return StatusPautaMapper.INSTANCE.convertToRest(this);
    }

}
