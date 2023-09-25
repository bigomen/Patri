package com.patri.plataforma.restapi.restmodel;

import java.io.Serializable;

public class RestPerfil extends BaseRestModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String aenDescricao;

	public String getAenDescricao() {
		return aenDescricao;
	}

	public void setAenDescricao(String aenDescricao) {
		this.aenDescricao = aenDescricao;
	}

}
