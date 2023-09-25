package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.request.RestPautaRequest;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

public interface PautaControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PAUTA_TAG,
        summary = "Lista todos as pautas",
        description = "Retorna as informações de descrição, unidade de negócio e produto de todas as pautas cadastradas na base de dados."
    )
    public Collection<RestPautaResponse> listar
    (
        @Parameter
        (
            name = "dataInicio",
            description = "Parâmetro necessário para busca de uma Pauta por período de datas",
            example = "2022-05-01",
            required = true
        ) @RequestParam(value = "dataInicio") LocalDate dataInicio,
        @Parameter
        (
            name = "dataFim",
            description = "Parâmetro necessário para busca de uma Pauta por período de datas",
            example = "2022-06-30",
            required = true
        ) @RequestParam(value = "dataFim") LocalDate dataFim,
        @Parameter
        (
            name = "topico",
            description = "Parâmetro para busca de uma Pauta por um Topico",
            example = "d6d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46"
        ) @RequestParam(value = "topico") String topico,
        @Parameter
        (
            name = "unidade",
            description = "Parâmetro para busca de uma Pauta por Unidade de Negócio",
            example = "df2effe59fc6628f929a0cce2fd60c54fd02ad6025565e46"
        ) @RequestParam(value = "unidade") String unidade,
        @Parameter
        (
            name = "id",
            description = "Parâmetro para busca de uma Pauta por identificador",
            example = "53"
        ) @RequestParam(value = "id") Long id
    );

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.PAUTA_TAG,
        summary = "Retorna a pauta de acordo com o id",
        description = "Retorna as informações de descrição, unidade de negócio e produto da pauta cadastrada na base de dados, conforme id informado pelo usuário."
    )
    public RestPautaResponse pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta da pauta em específico.", example = "47") @Valid Long id);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.PAUTA_TAG,
            summary = "Remove uma pauta da base de dados",
            description = "Remove na base a pauta conforme id informado como parâmetro na URI."
    )
    public void deletar(@Parameter(name = "Id", description = "Id necessário para remoção da pauta em específico.", example = "47" ) @Valid Long id);

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.PAUTA_TAG,
        summary = "Insere um nova pauta na base de dados",
        description = "Insere na base de dados uma nova pauta contendo as informações de descrição, unidade de negócio e produto da pauta, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de nova pauta.") @Valid RestPautaRequest pauta);

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.PAUTA_TAG,
        summary = "Altera uma pauta na base de dados",
        description = "Altera na base de dados as informações de descrição, unidade de negócio e produto da pauta, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(required = true, description = "Corpo da requisição para alteração da pauta.") @Valid RestPautaRequest pauta,
                                     @Parameter(name = "Id", description = "Parâmetro necessário para alteração da pauta em específico. Recebe como possível valor o Id.", example = "47") Long id);
}
