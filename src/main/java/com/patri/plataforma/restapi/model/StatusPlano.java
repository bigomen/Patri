package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.StatusPlanoMapper;
import com.patri.plataforma.restapi.restmodel.RestStatusPlano;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATUS_PLANO")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StatusPlano extends Model<RestStatusPlano> implements Serializable
{
    @Id
    @Column(name = "SPL_ID", nullable = false)
    private Long id;

    @Column(name = "SPL_DESCRICAO")
    private String descricao;

    public StatusPlano(Long id)
    {
        this.id = id;
    }

    @Override
    public RestStatusPlano modelParaRest()
    {
        return StatusPlanoMapper.INSTANCE.convertToRest(this);
    }

}
