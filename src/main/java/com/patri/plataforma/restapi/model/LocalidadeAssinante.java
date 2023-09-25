package com.patri.plataforma.restapi.model;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.patri.plataforma.restapi.mapper.LocalidadeAssinanteMapper;
import com.patri.plataforma.restapi.mapper.LocalidadeMapper;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidadeAssinante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOCALIDADE_ASSINANTE")
public class LocalidadeAssinante extends Model<RestLocalidadeAssinante> implements Localidade {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCALIDADE_ASSINANTE")
    @SequenceGenerator(name = "SEQ_LOCALIDADE_ASSINANTE", sequenceName = "SEQ_LOCALIDADE_ASSINANTE", allocationSize = 1)
    @Column(name = "LAS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASN_ID")
    private Assinante assinante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MUN_ID")
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UF_ID")
    private UF uf;
    
    @ManyToMany(mappedBy = "localidadesAssinantes")
    private Collection<UsuarioAssinante> usuarios;

    @Override
    public RestLocalidadeAssinante modelParaRest() 
    {
        return LocalidadeAssinanteMapper.INSTANCE.convertToRest(this);
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LocalidadeAssinante that = (LocalidadeAssinante) o;
		return Objects.equals(municipio, that.municipio) && Objects.equals(uf, that.uf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(municipio, uf);
	}

	@Override
	public String getLocalidade()
	{
		if(getTipo().equals(TipoLocalidade.MUN))
		{
			return this.municipio.getLocalidade();
		}
		
		return this.uf.getLocalidade();
	}

	@Override
	public TipoLocalidade getTipo()
	{
		if(this.municipio != null)
		{
			return TipoLocalidade.MUN;
		}
		
		return TipoLocalidade.UF;
	}

	@Override
	public RestLocalidade toRestLocalidade()
	{
		if(this.getTipo().equals(TipoLocalidade.MUN))
    	{
    		return LocalidadeMapper.INSTANCE.convertToRest(this.getMunicipio()); 
    	}
    	
    	return LocalidadeMapper.INSTANCE.convertToRest(this.getUf());
	}
}
