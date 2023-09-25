package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.mapper.LocalidadePautaMapper;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidadePauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOCALIDADE_PAUTA")
@Entity
public class LocalidadePauta extends Model<RestLocalidadePauta> implements Serializable, Localidade
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCALIDADE_PAUTA")
    @SequenceGenerator(name = "SEQ_LOCALIDADE_PAUTA", sequenceName = "SEQ_LOCALIDADE_PAUTA", allocationSize = 1)
    @Column(name = "LOP_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAT_ID")
    private Pauta pauta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MUN_ID")
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UF_ID")
    private UF uf;

    @Override
    public RestLocalidadePauta modelParaRest()
    {
        return LocalidadePautaMapper.INSTANCE.convertToRest(this);
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
