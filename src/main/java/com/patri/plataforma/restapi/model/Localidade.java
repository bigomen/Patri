package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;

public interface Localidade
{
	public Long getId();
	public String getLocalidade();
	public TipoLocalidade getTipo();
	public RestLocalidade toRestLocalidade();
}
