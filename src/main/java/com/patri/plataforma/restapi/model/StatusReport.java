package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.StatusReportMapper;
import com.patri.plataforma.restapi.restmodel.RestStatusReport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATUS_REPORT")
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class StatusReport extends Model<RestStatusReport> implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SRE_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SRE_DESCRICAO")
    private String descricao;

    public StatusReport(Long id)
    {
        this.id = id;
    }

    @Override
    public RestStatusReport modelParaRest()
    {
        return StatusReportMapper.INSTANCE.convertToRest(this);
    }
}
