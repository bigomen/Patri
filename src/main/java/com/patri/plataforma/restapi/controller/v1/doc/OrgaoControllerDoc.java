package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestOrgao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.Collection;

public interface OrgaoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Lista todos os órgãos",
        description = "Pode ser passado parte do nome ou o nome completo de um órgão para a busca. " +
            "Retorna as informações de nome, ordem e a unidade de negócio de todos os órgãos cadastrados na base de dados."
    )
    public Collection<RestOrgao> listarOrgaosAtivos(String param);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Retorna o órgão de acordo com o id",
        description = "Retorna as informações de nome, ordem e a unidade de negócio do órgão cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestOrgao buscarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do órgão em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Insere um novo órgão na base de dados",
        description = "Insere na base de dados um novo órgão contendo as informações de nome, ordem e unidade de negócio, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo órgão.") @Valid RestOrgao restOrgao);

    @Operation
    (
        method = "PUT",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Altera um órgão na base de dados",
        description = "Altera na base de dados as informações de nome, ordem e unidade de negócio, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do órgão.") @Valid RestOrgao restOrgao,
                             @Parameter(name = "Id", description = "Id necessário para alteração do órgão em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46")String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Altera o status de um órgão na base de dados",
        description = "Altera na base de dados as informações do status, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativarOrgao(@Parameter(name = "Id", description = "Id necessário para alteração do status em um órgão em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id,
                                     @Parameter(name = "Status", description = "Valor necessário para a alteração do status de um órgão em específico", example = "false") Boolean status);

    @Operation(
        method = "GET",
        tags = SwaggerConstantes.ORGAO_TAG,
        summary = "Retorna os órgãos.",
        description = "Retorna os órgãos de acordo com a unidade de negócio informada."
    )
    public Collection<RestOrgao> buscarOrgaoPorUnidade(String unidadeNegocio);
}
