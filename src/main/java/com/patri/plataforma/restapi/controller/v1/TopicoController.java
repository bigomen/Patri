package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.TopicoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import com.patri.plataforma.restapi.service.TopicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/topicos/v1")
@Validated
@Tag(name = SwaggerConstantes.TOPICO_TAG)
public class TopicoController implements TopicoControllerDoc {

    private final TopicoService topicoService;

    @Autowired
    public TopicoController(TopicoService topicoService) {
        super();
        this.topicoService = topicoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestTopico> listarTopicos() {
        return topicoService.listarTopicos();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/pesquisar")
    public Collection<RestTopico> pesquisar(@RequestParam(value = "param", required = false) String param)
    {
        return topicoService.pesquisar(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestTopico pesquisarPorId(@PathVariable String id) throws Exception {
        return topicoService.obterPorId(id);
    }
    
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/assinante/{id}")
    public Collection<RestTopico> pesquisarPorAssinanteEParametros(@PathVariable(value = "id")String idAssinante, @RequestParam(value = "param", required = false) String param)
    {
    	return topicoService.consultarTopicosPorAssinante(idAssinante, param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestTopico restTopico) {
        topicoService.novo(restTopico);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{id}")
    public void alterar(@Valid @RequestBody RestTopico restTopico, @PathVariable(value = "id") String id) throws Exception {
        topicoService.alterar(restTopico, id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("{id}/status/{status}")
    public void ativarDesativar(@PathVariable(value = "id") String id,
                                      @PathVariable(value = "status") boolean status)
    {
        topicoService.alterar(id, status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listaSimples")
    public Collection<RestTopico> listaSimples(){
        return topicoService.listaSimples();
    }
}
