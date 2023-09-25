package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoStatusAssinante
{
    A("Ativo", 1L, "cc4c49814839377e929a0cce2fd60c54fd02ad6025565e46"),
    I("Inativo", 2L, "d6d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46");

    private String descricao;
    private Long id;
    private String encriptedId;
}
