package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.PlanoControllerDoc;
import com.patri.plataforma.restapi.model.enums.TipoStatusPlano;
import com.patri.plataforma.restapi.restmodel.RestPlano;
import com.patri.plataforma.restapi.service.PlanoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@RestController
@RequestMapping("/planos/v1/")
@Validated
@Tag(name = SwaggerConstantes.PLANO_TAG)
public class PlanoController implements PlanoControllerDoc
{
    private final PlanoService planoService;

    @Autowired
    public PlanoController(PlanoService planoService)
    {
        super();
        this.planoService = planoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("lista")
    public Collection<RestPlano> listar()
    {
        return planoService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("listaAtivos")
    public Collection<RestPlano> listarAtivos()
    {
        return planoService.listarAtivos();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("pesquisar")
    public Collection<RestPlano> pesquisar(@NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param)
    {
        return planoService.pesquisar(param);
    }

    @GetMapping("{id}")
    public RestPlano pesquisarPorId(@PathVariable String id)
    {
        return planoService.encontrarPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestPlano restPlano)
    {
        planoService.novo(restPlano);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}")
    public void alterar(@Valid @RequestBody RestPlano restPlano, @PathVariable String id
    )
    {
        planoService.update(id, restPlano);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}/{status}")
    public void ativarDesativar(@PathVariable String id, @PathVariable TipoStatusPlano status)
    {
        planoService.update(id, status);
    }
}
