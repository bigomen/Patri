package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestReportContexto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.Collection;

public interface ReportContextoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORTCONTEXTO_TAG,
        summary = "Lista todos os ProdutosContexto",
        description = "Retorna as informações de texto, ordem, produto e contexto de todos os ProdutosContexto cadastrados na base de dados."
    )
    public Collection<RestReportContexto> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORTCONTEXTO_TAG,
        summary = "Retorna o ProdutoContexto de acordo com o id",
        description = "Retorna as informações de texto, ordem, produto e contexto do ProdutoContexto cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestReportContexto pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do ProdutoContexto em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.REPORTCONTEXTO_TAG,
        summary = "Remove um ProdutoContexto da base de dados",
        description = "Remove na base o ProdutoContexto conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do ProdutoContexto em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) @Valid String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.REPORTCONTEXTO_TAG,
        summary = "Insere um novo ProdutoContexto na base de dados",
        description = "Insere na base de dados um novo ProdutoContexto contendo as informações de texto, ordem, produto e contexto do ProdutoContexto, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo ProdutoContexto.") @Valid RestReportContexto restReportContexto);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.REPORTCONTEXTO_TAG,
        summary = "Altera um ProdutoContexto na base de dados",
        description = "Altera na base de dados as informações de texto, ordem, produto e contexto do ProdutoContexto, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do ProdutoContexto.") @Valid RestReportContexto restReportContexto,
                                      @Parameter(name = "Id", description = "Id necessário para alteração do plano em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);
}
