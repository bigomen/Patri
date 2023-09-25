package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.PermissaoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestPermissao;
import com.patri.plataforma.restapi.service.PermissaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/permissoes/v1")
@Validated
@Tag(name = SwaggerConstantes.PERMISSAO_TAG)
public class PermissaoController implements PermissaoControllerDoc
{
    private final PermissaoService permissaoService;

    @Autowired
    public PermissaoController(PermissaoService permissaoService){
        this.permissaoService = permissaoService;
    }

    @GetMapping("/lista")
    public Collection<RestPermissao> listar(){
        return permissaoService.findAll();
    }

    @GetMapping(value = "/{id}")
    public RestPermissao pesquisarPorId(@PathVariable String id){
        return permissaoService.find(id);
    }

//    @PostMapping
//    public RestPermissao novo(@Valid @RequestBody RestPermissao restPermissao) throws Exception
//    {
//        return permissaoService.insert(restPermissao);
//    }

    @DeleteMapping(value = "/{id}")
    public void deletar(@PathVariable String id){
        permissaoService.delete(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping(value = "/{id}")
    public void alterar(@Valid @RequestBody RestPermissao updatedPermissao, @PathVariable String id) {
        permissaoService.update(id, updatedPermissao);
    }
}
