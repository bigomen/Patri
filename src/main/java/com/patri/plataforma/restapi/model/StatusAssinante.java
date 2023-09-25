package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.StatusAssinanteMapper;
import com.patri.plataforma.restapi.restmodel.RestStatusAssinante;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "STATUS_ASSINANTE")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StatusAssinante extends Model<RestStatusAssinante> implements Serializable
{

    @Id
    @Column(name = "SAS_ID", nullable = false)
    private Long id;

    @Column(name = "SAS_DESCRICAO")
    private String descricao;

    public StatusAssinante(Long id)
    {
        this.id = id;
    }

    @Override
    public RestStatusAssinante modelParaRest()
    {
        return StatusAssinanteMapper.INSTANCE.convertToRest(this);
    }

}
