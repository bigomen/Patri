package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.ReportControllerDoc;
import com.patri.plataforma.restapi.model.enums.TipoStatusReport;
import com.patri.plataforma.restapi.restmodel.RestTemplate;
import com.patri.plataforma.restapi.restmodel.request.RestReportRequest;
import com.patri.plataforma.restapi.restmodel.response.RestItemReportResponse;
import com.patri.plataforma.restapi.restmodel.response.RestReportResponse;
import com.patri.plataforma.restapi.service.ReportService;
import com.patri.plataforma.restapi.service.TemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Tag(name = SwaggerConstantes.REPORT_TAG)
@RestController
@RequestMapping("/reports/v1")
@Validated
public class ReportController implements ReportControllerDoc
{
    private final ReportService reportService;
    private final TemplateService templateService;

    @Autowired
    public ReportController(ReportService reportService, TemplateService templateService)
    {
        super();
        this.reportService = reportService;
        this.templateService = templateService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestReportResponse> listar(@RequestParam(required = false, name = "param") String param)
    {
        return reportService.listar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestReportResponse pesquisarPorId(@PathVariable String id)
    {
        return reportService.encontrarPorId(id);
    }
    
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/templates/lista")
    public Collection<RestTemplate> listarTemplates()
    {
    	return templateService.pesquisarTemplates();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestReportRequest restReport)
    {
        reportService.novo(restReport);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{id}")
    public void alterar(@PathVariable String id, @Valid @RequestBody RestReportRequest restReport)
    {
        reportService.update(id, restReport);
    }
    
    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}/{status}")
    public void alterarStatus(@PathVariable(value = "id")String id, @PathVariable(value = "status") TipoStatusReport status)
    {
    	reportService.alterarStatus(id, status);
    }
    
    @DeleteMapping("/template/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerTemplate(@PathVariable(value = "id") String id)
    {
    	reportService.removerItem(id);
    }

    @GetMapping("/{id}/historico")
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RestItemReportResponse> listarHistoricoItens(@PathVariable(value = "id") String id)
    {
    	return reportService.listarHistoricoItens(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/template/listaSimples")
    public Collection<RestTemplate> listaSimples(){
        return templateService.listaSimples();
    }
}
