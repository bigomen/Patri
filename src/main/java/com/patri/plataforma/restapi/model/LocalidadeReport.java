package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.mapper.LocalidadeReportMapper;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidadeReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOCALIDADE_REPORT")
public class LocalidadeReport extends Model<RestLocalidadeReport> implements Serializable, Localidade
{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCALIDADE_REPORT")
    @SequenceGenerator(name = "SEQ_LOCALIDADE_REPORT", sequenceName = "SEQ_LOCALIDADE_REPORT", allocationSize = 1)
    @Column(name = "LOR_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REP_ID")
    private Report report;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MUN_ID")
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UF_ID")
    private UF uf;

	@Override
	public RestLocalidadeReport modelParaRest()
	{
		return LocalidadeReportMapper.INSTANCE.convertToRest(this);
	}

    @Override
    public String getLocalidade() {
        if (getTipo().equals(TipoLocalidade.MUN)) {
            return this.municipio.getLocalidade();
        }
        return this.uf.getLocalidade();
    }

    @Override
    public TipoLocalidade getTipo() {
        if (this.municipio != null) {
            return TipoLocalidade.MUN;
        }
        return TipoLocalidade.UF;
    }

    @Override
    public RestLocalidade toRestLocalidade() {
        if (this.getTipo().equals(TipoLocalidade.MUN)) {
            return LocalidadeMapper.INSTANCE.convertToRest(this.getMunicipio());
        }
        return LocalidadeMapper.INSTANCE.convertToRest(this.getUf());
    }
}
