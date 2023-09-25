package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.model.BaseModel;
import com.patri.plataforma.restapi.model.Model;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class RestModel<E extends Model> extends BaseRestModel
{
    public abstract E restParaModel();
}
