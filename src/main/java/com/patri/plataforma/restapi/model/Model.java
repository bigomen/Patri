package com.patri.plataforma.restapi.model;

import com.patri.plataforma.restapi.restmodel.BaseRestModel;

import java.io.Serializable;

public abstract class Model<R extends BaseRestModel> extends BaseModel implements Serializable
{
    public abstract R modelParaRest();
}
