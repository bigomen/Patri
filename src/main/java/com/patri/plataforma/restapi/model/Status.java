package com.patri.plataforma.restapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.patri.plataforma.restapi.model.enums.TipoStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "STATUS")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Status implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STA_ID")
	@Enumerated(EnumType.STRING)
	private TipoStatus id;
	
	@Column(name = "STA_DESCRICAO")
	private String descricao;

	public Status(TipoStatus id) {
		this.id = id;
	}
}
