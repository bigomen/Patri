package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.StatusPlanoMapper;
import com.patri.plataforma.restapi.mapper.StatusUsuarioMapper;
import com.patri.plataforma.restapi.restmodel.RestStatusPlano;
import com.patri.plataforma.restapi.restmodel.RestStatusUsuario;
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
@Table(name = "STATUS_USUARIO")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StatusUsuario extends Model<RestStatusUsuario> implements Serializable
{
    @Id
    @Column(name = "SPL_ID", nullable = false)
    private Long id;

    @Column(name = "SPL_DESCRICAO")
    private String descricao;

    public StatusUsuario(Long id)
    {
        this.id = id;
    }

    @Override
    public RestStatusUsuario modelParaRest()
    {
        return StatusUsuarioMapper.INSTANCE.convertToRest(this);
    }

}
