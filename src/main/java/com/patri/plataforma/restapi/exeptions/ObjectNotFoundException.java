package com.patri.plataforma.restapi.exeptions;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String param;

	public ObjectNotFoundException(String param)
	{
		super();
		this.param = param;
	}

	public String getParam()
	{
		return param;
	}
}    
