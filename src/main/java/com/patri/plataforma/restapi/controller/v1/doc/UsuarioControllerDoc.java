package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.util.Collection;

public interface UsuarioControllerDoc {

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Lista todos os usuários assinantes",
        description = "Retorna as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante de todos os usuários assinantes cadastrados na base de dados."
    )
    public Collection<RestUsuarioAssinante> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Retorna o usuário assinante de acordo com o login",
        description = "Retorna as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante do usuário assinante cadastrado na base de dados, conforme login informado pelo usuário."
    )
    public Collection<RestUsuarioAssinante> pesquisarPorLogin(@Parameter(name = "Login", description = "Login necessário para consulta do usuário assinante em específico.") String login);


    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Retorna o usuário assinante de acordo com o id",
        description = "Retorna as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante do usuário assinante cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestUsuarioAssinante pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do usuário assinante em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Lista todos os usuários assinantes",
        description = "Retorna as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante de todos os usuários assinantes cadastrados na base de dados, conforme parâmetro informado pelo usuário."
    )
    public Collection<RestUsuarioAssinante> pesquisar(@Parameter(name = "Param", description = "Parametro necessário para consulta de usuários assinantes. Recebe como possíveis valores o Id, nome, email, setor ou cargo.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String param);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Insere um novo usuário assinante na base de dados",
        description = "Insere na base de dados um novo usuário assinante contendo as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante do usuário assinante, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo usuário assinante.") @Valid RestUsuarioAssinante restUsuarioAssinante);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Altera um usuário assinante na base de dados",
        description = "Altera na base de dados as informações de nome, sobrenome, email, login, senha, cargo, setor, status e id do assinante e grupo assinante do usuário assinante, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do usuário assinante.") @Valid RestUsuarioAssinante restUsuarioAssinante,
                        @Parameter(name = "Id", description = "Id necessário para alteração do usuário assinante em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Remove um usuário assinante da base de dados",
        description = "Remove na base de dados o usuário assinante referente ao Id recebido como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do usuário assinante em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.USUARIOASSINANTE_TAG,
        summary = "Ativa ou desativa um usuário assinante na base de dados",
        description = "Ativa ou desativa na base de dados o usuário assinante, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar(@Parameter(required = true, description = "Id necessário para ativação ou inativação do usuário assinante em específico") String id,
                                                @Parameter(required = true, description = "True para ativar e false para inativar") boolean status);
}
