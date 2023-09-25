package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.UsuarioBackofficeDoc;
import com.patri.plataforma.restapi.restmodel.RestUsuarioBackOffice;
import com.patri.plataforma.restapi.service.UsuarioBackOfficeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@RestController
@RequestMapping("/usuariobackoffice/v1")
@Tag(name = SwaggerConstantes.USUARIOBACKOFFICE_TAG)
@Validated
public class UsuarioBackOfficeController implements UsuarioBackofficeDoc
{
    private final UsuarioBackOfficeService usuarioBackOfficeService;

    @Autowired
    public UsuarioBackOfficeController(UsuarioBackOfficeService usuarioBackOfficeService)
    {
        super();
        this.usuarioBackOfficeService = usuarioBackOfficeService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestUsuarioBackOffice> listar()
    {
        return usuarioBackOfficeService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/pesquisar")
    public Collection<RestUsuarioBackOffice> pesquisar(@NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param)
    {
        return usuarioBackOfficeService.pesquisar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestUsuarioBackOffice pesquisarPorId(@Valid @PathVariable String id)
    {
        return usuarioBackOfficeService.pesquisarPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestUsuarioBackOffice usuarioBackOffice)
    {
        usuarioBackOfficeService.novo(usuarioBackOffice);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@Valid @PathVariable String id)
    {
        usuarioBackOfficeService.deletar(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{id}")
    public void alterar(@Valid @RequestBody RestUsuarioBackOffice usuarioBackOffice,@Valid @PathVariable String id)
    {
        usuarioBackOfficeService.alterar(id, usuarioBackOffice);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}/{status}")
    public void ativarDesativar(@PathVariable String id, @PathVariable Boolean status)
    {
        usuarioBackOfficeService.alterar(id, status);
    }
}
