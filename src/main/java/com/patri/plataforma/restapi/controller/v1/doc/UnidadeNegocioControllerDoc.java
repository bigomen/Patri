package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface UnidadeNegocioControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Lista todas as unidades de negócio",
        description = "Retorna as informações de descrição de todas as unidades de negócio cadastradas na base de dados."
    )
    public Collection<RestUnidadeNegocio> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Lista todas as unidades de negócio",
        description = "Retorna as informações de descrição de todas as unidades de negócio cadastradas na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestUnidadeNegocio> pesquisar
    (
        @Parameter
        (
            name = "Param",
            description = "Parâmetro necessário para consulta da Unidade de Negócio em específico. Possíveis valores id e descrição",
            example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46",
            required = true
        )
        @NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param
    );

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Retorna a unidade de negócio de acordo com o id",
        description = "Retorna as informações de descrição da unidade de negócio cadastrada na base de dados, conforme id informado pelo usuário."
    )
    public RestUnidadeNegocio pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta da unidade de negócio em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Insere uma nova unidade de negócio na base de dados",
        description = "Insere na base de dados uma nova unidade de negócio contendo as informações de descrição da unidade de negócio, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de nova unidade de negócio.") @Valid RestUnidadeNegocio unidadeNegocio);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Altera uma unidade de negócio na base de dados",
        description = "Altera na base de dados as informações de descrição da unidade de negócio, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração da unidade de negócio.") @Valid RestUnidadeNegocio unidadeNegocio,
                                      @Parameter(name = "Id", description = "Id necessário para alteração da unidade de negócio em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.UNIDADENEGOCIO_TAG,
        summary = "Altera o status de uma unidade de negócio na base de dados",
        description = "Altera na base de dados o status de descrição da unidade de negócio, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar( @Parameter(name = "Id", description = "Id necessário para alteração da unidade de negócio em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id,
                                               @Parameter(name = "Status", description = "Valor necessário para a alteração do status de uma unidade de negócio em específico", example = "false") Boolean status);
}
