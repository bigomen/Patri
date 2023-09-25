package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.StatusProdutoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestStatusReport;
import com.patri.plataforma.restapi.service.StatusProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/statusproduto/v1")
@Validated
@Tag(name = SwaggerConstantes.STATUSPRODUTO_TAG)
public class StatusProdutoController implements StatusProdutoControllerDoc
{
    private final StatusProdutoService statusProdutoService;

    @Autowired
    public StatusProdutoController(
            StatusProdutoService statusProdutoService)
    {
        this.statusProdutoService = statusProdutoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestStatusReport> listar()
    {
        return statusProdutoService.listar();
    }

    @GetMapping("/{id}")
    public RestStatusReport pesquisarPorId(@PathVariable String id)
    {
        return statusProdutoService.acharPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestStatusReport restStatusReport)
    {
        statusProdutoService.novo(restStatusReport);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id)
    {
        statusProdutoService.deletar(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestStatusReport restStatusReport, @PathVariable String id)
    {
        statusProdutoService.update(id, restStatusReport);
    }

    @Override
    public Collection<RestStatusReport> pesquisar(String param) {
        return null;
    }
}
