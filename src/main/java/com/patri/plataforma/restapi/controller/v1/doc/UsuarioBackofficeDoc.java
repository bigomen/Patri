package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface UsuarioBackofficeDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Lista todos os usuários backoffice",
        description = "Retorna as informações de login, nome, sobrenome, senha, status, grupobackoffice e a unidade de negócio de todos os usuários backoffice cadastrados na base de dados."
    )
    public Collection<RestUsuarioBackOffice> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Lista todos os usuários backoffice",
        description = "Retorna as informações de login, nome, sobrenome, senha, status, grupobackoffice e a unidade de negócio de todos os usuários backoffice cadastrados na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestUsuarioBackOffice> pesquisar
    (
        @Parameter
        (
            name = "Param",
            description = "Parâmetro necessário para consulta do Usuário Backoffice em específico. Possíveis valores id e descrição",
            example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46",
            required = true
        )
        @NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param
    );

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Retorna o usuário backoffice de acordo com o id",
        description = "Retorna as informações de login, nome, sobrenome, senha, status, grupobackoffice e a unidade de negócio do usuário backoffice cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestUsuarioBackOffice pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do usuário Backoffice em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Remove um usuário backoffice da base de dados",
        description = "Remove na base o usuário backoffice conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do usuário backoffice em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) @Valid String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Insere um novo usuário backoffice na base de dados",
        description = "Insere na base de dados um novo usuário backoffice contendo as informações de login, nome, sobrenome, senha, status, grupobackoffice e a unidade de negócio do usuário backoffice, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo usuário backoffice.") @Valid RestUsuarioBackOffice usuarioBackoffice);

    @Operation
    (
        method = "PUT",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Altera um usuário backoffice na base de dados",
        description = "Altera na base de dados as informações de login, nome, sobrenome, senha, status, grupobackoffice e a unidade de negócio do usuário backoffice, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do usuário backoffice.") @Valid RestUsuarioBackOffice usuarioBackoffice,
                                         @Parameter(name = "Id", description = "Id necessário para alteração do plano em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @Valid String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.USUARIOBACKOFFICE_TAG,
        summary = "Altera o status de um usuário backoffice na base de dados",
        description = "Altera na base de dados as informações do status, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar(@Parameter(name = "Id", description = "Id necessário para alteração do status em um usuário backoffice em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @PathVariable String id,
                                                 @Parameter(required = true, description = "Tipo do Status necessário.") @PathVariable Boolean status);
}
