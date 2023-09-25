package com.patri.plataforma.restapi.repository.custom.util;

public class UtilQuery
{
	public static String like(String parametro)
    {
        final String CARACTERLIKE = "%";

        return CARACTERLIKE.concat(parametro).concat(CARACTERLIKE);
    }
}
