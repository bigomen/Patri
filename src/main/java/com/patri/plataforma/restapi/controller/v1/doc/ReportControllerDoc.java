package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.model.enums.TipoStatusReport;
import com.patri.plataforma.restapi.restmodel.RestTemplate;
import com.patri.plataforma.restapi.restmodel.request.RestReportRequest;
import com.patri.plataforma.restapi.restmodel.response.RestItemReportResponse;
import com.patri.plataforma.restapi.restmodel.response.RestReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import java.util.Collection;

public interface ReportControllerDoc
{
    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Lista todos os reports",
        description = "Retorna as informações de título, newsletter, data de atualização, unidade negócio e status de todos os reports cadastrados na base de dados."
    )
    public Collection<RestReportResponse> listar(String param);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Retorna o report de acordo com o id",
        description = "Retorna as informações de título, newsletter, data de atualização, unidade negócio e status do report cadastrado na base de dados, conforme id informado pelo usuário."
    )
    public RestReportResponse pesquisarPorId(@Parameter(name = "Id", description = "Id necessário para consulta do report em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Lista todos os templates",
        description = "Retorna as informações de id e descrição de templates cadastrados na base de dados."
    )
    public Collection<RestTemplate> listarTemplates();

    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Insere um novo report na base de dados",
        description = "Insere na base de dados um novo report contendo as informações de título, newsletter, data de atualização, unidade negócio e status do report, enviadas no corpo da requisição."
    )
    public void novo(@Parameter(required = true, description = "Corpo da requisição para criação de novo report.") @Valid RestReportRequest restReport);

    @Operation
    (
        method = "PUT",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Altera um report na base de dados",
        description = "Altera na base de dados as informações de título, newsletter, data de atualização, unidade negócio e status do report, conforme Id informado como parâmetro na URI."
    )
    public void alterar(@Parameter(name = "Id", description = "Id necessário para alteração do report em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id,
                                      @Parameter(required = true, description = "Corpo da requisição para alteração do report.") @Valid RestReportRequest restReport) throws Exception;
    
    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Altera o status de um report na base de dados",
        description = "Altera na base de dados o status de um report"
        
    )
    public void alterarStatus(@Parameter(name = "Id", description = "Id necessário para alteração do report em específico.", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id,
                              @Parameter(required = true, description = "Novo status a ser atribuido", schema = @Schema(allowableValues = "{A, I, E}")) TipoStatusReport status);

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Remove um template da base de dados",
        description = "Remove na base o template conforme id informado como parâmetro na URI."
    )
    public void removerTemplate(@Parameter(name = "Id", description = "Id necessário para remoção do template em específico.", example ="d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46" ) String id);

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.REPORT_TAG,
        summary = "Retorna uma lista contendo histórico de Itens alterados do Report",
        description = "Retorna as informações de texto, ordem, errata, template e Usuário Backoffice do histórico de Itens."
    )
    public Collection<RestItemReportResponse> listarHistoricoItens(@Parameter(name = "id", description = "Id necessário para busca do histórico de Itens de um Report", example = "d2d228265f6b2b19929a0cce2fd60c54fd02ad6025565e46") String id);
}
