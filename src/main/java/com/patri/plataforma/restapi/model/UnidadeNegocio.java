package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.patri.plataforma.restapi.mapper.UnidadeNegocioMapper;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIDADE_NEGOCIO")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UnidadeNegocio extends Model<RestUnidadeNegocio> implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Long ESTADOS_E_MUNICIPIOS = 3L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNIDADE_NEGOCIO")
    @SequenceGenerator(name = "SEQ_UNIDADE_NEGOCIO", sequenceName = "SEQ_UNIDADE_NEGOCIO", allocationSize = 1)
    @Column(name = "UNE_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "UNE_DESCRICAO")
    private String descricao;

    @Column(name = "UNE_NOME")
    private String nome;

    @Column(name = "UNE_COR_APRESENTACAO")
    private String corApresentacao = "#ffffff";

    @Column(name = "UNE_LOGO")
    private byte[] logo;

    @Column(name = "UNE_ATIVO")
    private Boolean unidadeAtiva;

    @Column(name = "UNE_DT_ALTERACAO")
    private LocalDateTime ultimaDataAlteracao;

    public UnidadeNegocio(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @PreUpdate
    private void preUpdate()
    {
        this.ultimaDataAlteracao = LocalDateTime.now();
    }

    @Override
    public RestUnidadeNegocio modelParaRest()
    {
        return UnidadeNegocioMapper.INSTANCE.convertToRest(this);
    }

	public boolean isEstadosEMunicipios()
	{
		return this.id.equals(ESTADOS_E_MUNICIPIOS);
	}
}
