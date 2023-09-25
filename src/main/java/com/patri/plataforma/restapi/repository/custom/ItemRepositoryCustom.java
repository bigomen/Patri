package com.patri.plataforma.restapi.repository.custom;

import com.patri.plataforma.restapi.model.Item;

import java.time.LocalDate;
import java.util.Collection;

public interface ItemRepositoryCustom
{
	public Collection<Item> historicoItens(Long idReport);
}
