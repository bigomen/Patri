package com.patri.plataforma.restapi.repository.custom;

import java.util.Collection;
import com.patri.plataforma.restapi.model.Orgao;

public interface OrgaoRepositoryCustom
{
	public Collection<Orgao> pesquisarOrgaos(String param);
}
