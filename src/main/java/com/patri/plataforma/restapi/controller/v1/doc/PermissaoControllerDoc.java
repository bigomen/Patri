package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestPermissao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.Collection;

public interface PermissaoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PERMISSAO_TAG,
        summary = "Lista todos os permissões",
        description = "Retorna as informações de descrição, tipo, rota, grupo backoffice e grupo assinante de todos as permissões cadastradas na base de dados."
    )
    public Collection<RestPermissao> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PERMISSAO_TAG,
        summary = "Retorna a permissão de acordo com o id",
        description = "Retorna as informações de descrição, tipo, rota, grupo backoffice e grupo assinante da permissão cadastrada na base de dados, conforme id informado pelo usuário."
    )
    public RestPermissao pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta da permissão em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.PERMISSAO_TAG,
        summary = "Remove uma permissão da base de dados",
        description = "Remove na base a permissão conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção da permissão em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) String id);

//    @Operation
//    (
//        method = "POST",
//        tags = SwaggerConstantes.PERMISSAO_TAG,
//        summary = "Insere uma nova permissão na base de dados",
//        description = "Insere na base de dados uma nova permissão contendo as informações de descrição, tipo, rota, grupo backoffice e grupo assinante, enviadas no corpo da requisição."
//    )
//    public RestPermissao novo(@Parameter(required = true, description = "Corpo da requisição para criação de uma nova permissão.") @Valid RestPermissao restPermissao) throws Exception;

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.PERMISSAO_TAG,
        summary = "Altera uma permissão na base de dados",
        description = "Altera na base de dados as informações de descrição, tipo, rota, grupo backoffice e grupo assinante da permissão, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração da permissão.") @Valid RestPermissao restPermissao,
                                 @Parameter(name = "Id", description = "Id necessário para alteração da permissão em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);
}
