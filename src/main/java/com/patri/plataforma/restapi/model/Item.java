package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.patri.plataforma.restapi.mapper.ItemReportMapper;
import com.patri.plataforma.restapi.restmodel.response.RestItemReportResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@Entity
@Table(name = "REPORT_CONTEXTO")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@DynamicInsert
@DynamicUpdate
public class Item  extends Model<RestItemReportResponse> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
    @SequenceGenerator(name = "SEQ_ITEM", sequenceName = "SEQ_REPORT_CONTEXTO", allocationSize = 1)
    @Column(name = "RCO_ID")
	private Long id;
	
	@Column(name = "RCO_TEXTO")
	private String texto;
	
	@Column(name = "RCO_ORDEM")
	private Integer ordem;
	
	@Column(name = "RCO_DATA", updatable = false)
	private LocalDateTime data;
	
	@Column(name = "RCO_DT_ULTIMA_ALTERACAO")
	@Setter(value = AccessLevel.NONE)
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "RCO_ERRATA")
	private Boolean errata;
	
	@Column(name = "RCO_ATIVO")
	private Boolean ativo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REP_ID")
	private Report report;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CON_ID")
	private Template template;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USB_ID")
	private UsuarioBackOffice usuario;

	@PrePersist
	private void preInsert()
	{
		this.data = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		this.ativo = true;
	}

	@PreUpdate
	private void preUpdate()
	{
		this.dataAtualizacao = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return Objects.equals(texto, item.texto) && Objects.equals(ordem, item.ordem) && Objects.equals(errata, item.errata) && Objects.equals(report, item.report) && Objects.equals(template, item.template) && Objects.equals(usuario, item.usuario);
	}

	@Override
	public int hashCode() {
		return Objects.hash(texto, ordem, errata, report, template, usuario);
	}

	@Override
	public RestItemReportResponse modelParaRest()
	{
		return ItemReportMapper.INSTANCE.convertToRest(this);
	}
}
