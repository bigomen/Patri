package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.UnidadeNegocioControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import com.patri.plataforma.restapi.service.UnidadeNegocioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/unidadenegocio/v1")
@Validated
@Tag(name = SwaggerConstantes.UNIDADENEGOCIO_TAG)
public class UnidadeNegocioController implements UnidadeNegocioControllerDoc
{
    private final UnidadeNegocioService unidadeNegocioService;

    @Autowired
    public UnidadeNegocioController(UnidadeNegocioService unidadeNegocioService)
    {
        this.unidadeNegocioService = unidadeNegocioService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestUnidadeNegocio> listar()
    {
        return unidadeNegocioService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/pesquisar")
    public Collection<RestUnidadeNegocio> pesquisar(@RequestParam(value = "param") String param)
    {
        return unidadeNegocioService.pesquisar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestUnidadeNegocio pesquisarPorId(@Valid @PathVariable String id)
    {
        return unidadeNegocioService.acharPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestUnidadeNegocio restUnidadeNegocio)
    {
        unidadeNegocioService.novo(restUnidadeNegocio);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestUnidadeNegocio restUnidadeNegocio,@Valid @PathVariable String id)
    {
        unidadeNegocioService.alterar(id, restUnidadeNegocio);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}/{status}")
    public void ativarDesativar(@Valid @PathVariable String id, @PathVariable Boolean status)
    {
        unidadeNegocioService.alterar(id, status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listaSimples")
    public Collection<RestUnidadeNegocio> listaSimples(){
        return unidadeNegocioService.listaSimples();
    }

}
