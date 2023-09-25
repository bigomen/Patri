package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestGrupoBackOffice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface GrupoBackOfficeControllerDoc {

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Lista todos os grupos backoffice",
        description = "Retorna as informações de descrição e permissões de todos os grupos backoffice cadastrados na base de dados."
    )
    public Collection<RestGrupoBackOffice> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Retorna o grupo backoffice de acordo com o id",
        description = "Retorna as informações de descrição e permissões de todos os grupos backoffice cadastrados na base de dados, conforme id informado pelo usuário."
    )
    public RestGrupoBackOffice pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do grupo backoffice em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Lista todos os Grupos BackOffice",
        description = "Retorna as informações de descrição e permissões de todos os grupos backoffice cadastrados na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestGrupoBackOffice> pesquisar
    (
        @Parameter
            (
                name = "Param",
                description = "Parâmetro necessário para consulta do grupo backoffice em específico. Possíveis valores id e nome",
                example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46",
                required = true
            )
        @NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param
    );

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Insere um novo grupo backoffice na base de dados",
        description = "Insere na base de dados um novo grupo backoffice contendo as informações de descrição e permissões, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo grupo backoffice.") @Valid RestGrupoBackOffice restGrupoBackOffice) throws Exception;

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Altera um grupo backoffice na base de dados",
        description = "Altera na base de dados as informações de descrição e permissões do grupobackoffice, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do grupo backoffice.") @Valid RestGrupoBackOffice restGrupoBackOffice,
                                       @Parameter(name = "Id", description = "Id necessário para alteração do grupo backoffice em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.GRUPOBACKOFFICE_TAG,
        summary = "Remove um grupo backoffice da base de dados",
        description = "Remove na base de dados o grupo backoffice referente ao Id recebido como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do grupo backoffice em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);
}
