package com.patri.plataforma.restapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoStatusReport
{
    A("Ativo", 1L, "cc4c49814839377e929a0cce2fd60c54fd02ad6025565e46"),
    I("Inativo", 2L, "d6d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46"),
    E("Excluído", 3L, "3a5e79c0e6f8390b929a0cce2fd60c54fd02ad6025565e46"),
    EA("Em Atualização", 4L, "df2effe59fc6628f929a0cce2fd60c54fd02ad6025565e46");

    private String descricao;
    private Long id;
    private String encriptedId;
}
