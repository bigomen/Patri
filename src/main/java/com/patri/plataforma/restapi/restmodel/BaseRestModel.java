/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patri.plataforma.restapi.restmodel;

import lombok.EqualsAndHashCode;

/**
 *
 * @author rcerqueira
 */

public abstract class BaseRestModel
{
	@EqualsAndHashCode.Include
	private String id;

	public final void setId(String id)
	{
		this.id = id;
	}

	public final String getId()
	{
		return this.id;
	}
}
