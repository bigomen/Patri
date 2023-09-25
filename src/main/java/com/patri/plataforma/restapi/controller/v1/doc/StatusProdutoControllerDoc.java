package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestStatusReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface StatusProdutoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Lista todos os status de produto",
        description = "Retorna as informações de descrição de todos os status de produto cadastrados na base de dados."
    )
    public Collection<RestStatusReport> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Lista todos os status de produto",
        description = "Retorna as informações de descrição de todos os status de produto cadastrados na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestStatusReport> pesquisar(@NotBlank(message = "{PTR-022}") String param);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Retorna o status de produto de acordo com o id",
        description = "Retorna as informações de descrição do status de produto cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestStatusReport pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do status de produto em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Remove um status de produto da base de dados",
        description = "Remove na base o status de produto conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do status de produto em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Insere um novo status de produto na base de dados",
        description = "Insere na base de dados um novo status de produto contendo as informações de descrição do status de produto, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo status de produto.") @Valid RestStatusReport restStatusReport)  throws Exception;

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.STATUSPRODUTO_TAG,
        summary = "Altera um status de produto na base de dados",
        description = "Altera na base de dados as informações de descrição do status de produto, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do status de produto.") @Valid RestStatusReport restStatusReport,
                                    @Parameter(name = "Id", description = "Id necessário para alteração do status de produto em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);
}
