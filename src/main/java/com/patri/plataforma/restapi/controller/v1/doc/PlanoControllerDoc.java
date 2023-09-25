package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.model.enums.TipoStatusPlano;
import com.patri.plataforma.restapi.restmodel.RestPlano;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface PlanoControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Lista todos os Planos",
        description = "Retorna as informações de descrição, status e a unidade de negócio de todos os planos cadastrados na base de dados."
    )
    public Collection<RestPlano> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Lista todos os Planos ativados",
        description = "Retorna as informações de descrição, status e a unidade de negócio de todos os planos cadastrados na base de dados."
    )
    public Collection<RestPlano> listarAtivos();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Lista todos os Planos",
        description = "Retorna as informações de descrição, status e a unidade de negócio de todos os planos cadastrados na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestPlano> pesquisar
    (
        @Parameter
        (
            name = "Param",
            description = "Parâmetro necessário para consulta do plano em específico. Possíveis valores id e descrição",
            example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46",
            required = true
        )
        @NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param
    );

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Retorna o plano de acordo com o id",
        description = "Retorna as informações de descrição, status e a unidade de negócio do plano cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestPlano pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do plano em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Insere um novo plano na base de dados",
        description = "Insere na base de dados um novo plano contendo as informações de descrição, status e a unidade de negócio do plano, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo plano.") @Valid RestPlano restPlano);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Altera um plano na base de dados",
        description = "Altera na base de dados as informações de descrição, status e a unidade de negócio do plano, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do plano.") @Valid RestPlano plano,
                             @Parameter(name = "Id", description = "Id necessário para alteração do plano em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.PLANO_TAG,
        summary = "Altera status do plano na base de dados",
        description = "Altera na base de dados as informações de status do plano, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar(@Parameter(required = true, description = "Id necessário para a alteração do plano em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @PathVariable String id,
                                     @Parameter(required = true, description = "Enum necessário para a alteração do status. Possíveis valores : A: Ativo e I: Inativo", example = "A") @PathVariable TipoStatusPlano status);
}
