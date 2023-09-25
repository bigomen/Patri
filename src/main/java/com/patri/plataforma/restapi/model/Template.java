package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.patri.plataforma.restapi.mapper.TemplateMapper;
import com.patri.plataforma.restapi.restmodel.RestTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "CONTEXTO")
public class Template extends Model<RestTemplate> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TEMPLATE")
    @SequenceGenerator(name = "SEQ_TEMPLATE", sequenceName = "SEQ_TEMPLATE", allocationSize = 1)
    @Column(name = "CON_ID")
	@NonNull
    @EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "CON_DESCRICAO")
	private String descricao;

	@Override
	public RestTemplate modelParaRest()
	{
		return TemplateMapper.INSTANCE.convertToRest(this);
	}
}
