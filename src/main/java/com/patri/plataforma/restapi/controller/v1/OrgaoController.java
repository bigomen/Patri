package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.OrgaoControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestOrgao;
import com.patri.plataforma.restapi.service.OrgaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/orgao/v1")
@Tag(name = SwaggerConstantes.ORGAO_TAG)
public class OrgaoController implements OrgaoControllerDoc
{
    private final OrgaoService orgaoService;

    @Autowired
    public OrgaoController(OrgaoService orgaoService)
    {
        this.orgaoService = orgaoService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestOrgao> listarOrgaosAtivos(@RequestParam(value = "param", required = false) String param)
    {
        return orgaoService.pesquisarOrgaos(param);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public RestOrgao buscarPorId(@PathVariable(value = "id") String id)
    {
        return orgaoService.buscarPorId(id);
    }
    
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista/unidade/{unidade}")
    public Collection<RestOrgao> buscarOrgaoPorUnidade(@PathVariable(value = "unidade")String unidadeNegocio)
    {
    	
    	return orgaoService.pesquisarOrgaosPorUnidadeNegocio(unidadeNegocio);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/novo")
    public void novo(@Valid @RequestBody RestOrgao restOrgao)
    {
        orgaoService.novo(restOrgao);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{id}")
    public void alterar(@Valid @RequestBody RestOrgao restOrgao, @PathVariable(value = "id") String id)
    {
        orgaoService.alterar(restOrgao, id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/{status}")
    public void ativarDesativarOrgao(@Valid @PathVariable String id, @PathVariable Boolean status)
    {
        orgaoService.ativarDesativarOrgao(id, status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listaSimples")
    public Collection<RestOrgao> listaSimples(){
        return orgaoService.listaSimples();
    }
}
