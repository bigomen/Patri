package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.ContextoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestContexto;
import com.patri.plataforma.restapi.service.ContextoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/contexto/v1")
@Validated
@Tag(name = SwaggerConstantes.CONTEXTO_TAG)
public class ContextoController implements ContextoControllerDoc
{
    private final ContextoService contextoService;

    @Autowired
    public ContextoController(ContextoService contextoService)
    {
        super();
        this.contextoService = contextoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestContexto> listar()
    {
        return contextoService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestContexto pesquisarPorId(@Valid @PathVariable String id)
    {
        return contextoService.acharPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestContexto restContexto)
    {
        contextoService.novo(restContexto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestContexto restContexto,
                                @Valid @PathVariable String id ) throws Exception
    {
        contextoService.update(id,restContexto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@Valid @PathVariable String id)
    {
        contextoService.deletar(id);
    }
}
