package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestContexto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.Collection;

public interface ContextoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.CONTEXTO_TAG,
        summary = "Lista todos os contextos",
        description = "Retorna as informações de descrição de todos os contextos cadastrados na base de dados."
    )
    public Collection<RestContexto> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.CONTEXTO_TAG,
        summary = "Retorna o contexto de acordo com o id",
        description = "Retorna as informações de descrição do contexto cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestContexto pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do contexto em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.CONTEXTO_TAG,
        summary = "Remove um contexto da base de dados",
        description = "Remove na base o contexto conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do contexto em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) @Valid String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.CONTEXTO_TAG,
        summary = "Insere um novo contexto na base de dados",
        description = "Insere na base de dados um novo contexto contendo as informações de descrição do contexto, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo contexto.") @Valid RestContexto contexto);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.CONTEXTO_TAG,
        summary = "Altera um contexto na base de dados",
        description = "Altera na base de dados as informações de descrição do contexto, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do contexto.")  @Valid RestContexto contexto,
                                @Parameter(name = "Id", description = "Id necessário para alteração do contexto em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id) throws Exception;
}
