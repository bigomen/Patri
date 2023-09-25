package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.model.enums.TipoStatusAssinante;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;

public interface AssinanteControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Lista todos os assinantes",
        description = "Retorna as informações de razão social, email e o tipo de todos os assinantes cadastrados na base de dados."
    )
    public Collection<RestAssinante> listar(@RequestParam(value = "param", required = false) String param);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Retorna o assinante de acordo com o id",
        description = "Retorna as informações de razão social, email e o tipo do assinante cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestAssinante pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do assinante em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Lista de Assinantes",
        description = "Retorna uma lista de assinantes com as informações de razão social, email e tipo que em seu plano contenham a unidade de negócio informada"
    )
    public Collection<RestAssinante> pesquisarAssinantes(@PathVariable(name = "unidade")String unidade);
    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Remove um assinante da base de dados",
        description = "Remove na base o assinante conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção do assinante em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) String id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Insere um novo assinante na base de dados",
        description = "Insere na base de dados um novo assinante contendo as informações de razão social, email e o tipo do assinante, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo assinante.") @Valid RestAssinante restAssinante) throws Exception;

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Altera um assinante na base de dados",
        description = "Altera na base de dados as informações de razão social, email e o tipo do assinante, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do assinante.") @Valid RestAssinante restAssinante,
                                 @Parameter(name = "Id", description = "Id necessário para alteração do assinante em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.ASSINANTE_TAG,
        summary = "Altera o status de um assinante na base de dados",
        description = "Altera na base de dados as informações do status, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar(@Parameter(name = "Id", description = "Id necessário para alteração do status em um assinante em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") @PathVariable String id,
                                         @Parameter(required = true, description = "Tipo do Status necessário.", example = "A") @PathVariable TipoStatusAssinante status);
}
