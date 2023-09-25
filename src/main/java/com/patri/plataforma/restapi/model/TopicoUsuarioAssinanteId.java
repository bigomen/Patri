package com.patri.plataforma.restapi.model;

import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TopicoUsuarioAssinanteId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Include
    private Long idAssinante;
	
	@Include
    private Long idTopico;
	
	@Include
    private Long idUsuario;

    public TopicoUsuarioAssinanteId(Long assinante, Long topico, Long usuario)
    {
        this.idAssinante = assinante;
        this.idTopico = topico;
        this.idUsuario = usuario;
    }
}
