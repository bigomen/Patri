package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.GrupoBackOfficeControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestGrupoBackOffice;
import com.patri.plataforma.restapi.service.GrupoBackOfficeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@RestController
@RequestMapping("/grupobackoffice/v1")
@Validated
@Tag(name = SwaggerConstantes.GRUPOBACKOFFICE_TAG)
public class GrupoBackOfficeController implements GrupoBackOfficeControllerDoc {

    private final GrupoBackOfficeService grupoBackOfficeService;

    public GrupoBackOfficeController(GrupoBackOfficeService grupoBackOfficeService) {
        super();
        this.grupoBackOfficeService = grupoBackOfficeService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestGrupoBackOffice> listar() {
        return grupoBackOfficeService.listar();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestGrupoBackOffice pesquisarPorId(@PathVariable String id) {
        return grupoBackOfficeService.obterPorId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/pesquisar")
    public Collection<RestGrupoBackOffice> pesquisar(@NotBlank(message = "{PTR-022}") @RequestParam(value = "param") String param)
    {
        return grupoBackOfficeService.pesquisar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestGrupoBackOffice restGrupoBackOffice)
    {
        grupoBackOfficeService.novo(restGrupoBackOffice);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestGrupoBackOffice restGrupoBackOffice, @PathVariable String id)
    {
        grupoBackOfficeService.alterar(restGrupoBackOffice, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        grupoBackOfficeService.deletar(id);
    }
}
