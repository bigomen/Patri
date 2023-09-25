package com.patri.plataforma.restapi.restmodel;

import java.io.Serializable;
import java.util.List;

public class RestGrupoUsuario extends BaseRestModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gusNome;
	private List<RestPerfil> perfis;

	public String getGusNome() {
		return gusNome;
	}

	public void setGusNome(String gusNome) {
		this.gusNome = gusNome;
	}

	public List<RestPerfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<RestPerfil> perfis) {
		this.perfis = perfis;
	}

}
