package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;

public interface TopicoControllerDoc {

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Lista todos os tópicos",
        description = "Retorna as informações de descrição de todos os tópicos cadastrados na base de dados."
    )
    public Collection<RestTopico> listarTopicos();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Lista os tópicos de acordo com a descrição",
        description = "Retorna as informações de descrição de todos os tópicos cadastrados na base de dados."
    )
    public Collection<RestTopico> pesquisar
    (
        @Parameter
        (
            name = "param",
            description = "Parâmetro necessário para consulta do tópico em específico. Possível valor Descrição",
            example = "Esportes",
            required = false
        )
        @RequestParam(value = "param") String param
    );

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Retorna o tópico de acordo com o id",
        description = "Retorna as informações de descrição e subtópicos do tópico cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestTopico pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do tópico em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Insere um novo tópico na base de dados",
        description = "Insere na base de dados um novo tópico contendo as informações de descrição e subtópicos do tópico, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo tópico.") @Valid RestTopico restTopico);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Altera um tópico na base de dados",
        description = "Altera na base de dados as informações de descrição e subtópico do tópico, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração do tópico.") @Valid RestTopico restTopico,
                              @Parameter(name = "Id", description = "Id necessário para alteração do tópico em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id) throws Exception;

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Lista todos os tópicos",
        description = "Retorna as informações de descrição e subtópicos do tópico cadastrado na base de dados, conforme id do assinante informado pelo usuário. Pode também informar algum texto para "
            + "pesquisar pela descrição"
    )
    public Collection<RestTopico> pesquisarPorAssinanteEParametros(String idAssinante, String param);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.TOPICO_TAG,
        summary = "Ativa ou desativa um tópico na base de dados",
        description = "Ativa ou desativa na base de dados o tópico, conforme Id informado como parâmetro na URI."
    )
    public void ativarDesativar(@Parameter(required = true, description = "Id necessário para ativação ou inativação do tópico em específico") String id,
                                @Parameter(required = true, description = "True para ativar e false para inativar") boolean status);
}
