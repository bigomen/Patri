package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.ReportContextoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestReportContexto;
import com.patri.plataforma.restapi.service.ReportContextoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Tag(name = SwaggerConstantes.REPORTCONTEXTO_TAG)
@RestController
@RequestMapping("/reportcontexto/v1")
@Validated
public class ReportContextoController implements ReportContextoControllerDoc
{
    private final ReportContextoService reportContextoService;

    @Autowired
    public ReportContextoController(ReportContextoService reportContextoService)
    {
        super();
        this.reportContextoService = reportContextoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestReportContexto> listar()
    {
        return reportContextoService.listar();
    }

    @GetMapping("/{id}")
    public RestReportContexto pesquisarPorId(@PathVariable String id)
    {
        return reportContextoService.acharPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestReportContexto restReportContexto)
    {
        reportContextoService.novo(restReportContexto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id)
    {
        reportContextoService.deletar(id);
    }


    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestReportContexto restReportContexto, @Valid @PathVariable String id)
    {
        reportContextoService.update(id, restReportContexto);
    }
}
