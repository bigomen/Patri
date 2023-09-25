package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.AssinanteControllerDoc;
import com.patri.plataforma.restapi.model.enums.TipoStatusAssinante;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.service.AssinanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/assinantes/v1")
@Validated
@Tag(name = SwaggerConstantes.ASSINANTE_TAG)
public class AssinanteController implements AssinanteControllerDoc
{
	private final AssinanteService assinanteService;
	
	@Autowired
	public AssinanteController(AssinanteService assinanteService)
	{
		super();
		this.assinanteService = assinanteService;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/lista")
	public Collection<RestAssinante> listar(String param) {
		return assinanteService.listar(param);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/lista/unidade/{unidade}")
	public Collection<RestAssinante> pesquisarAssinantes(@PathVariable(name = "unidade")String unidade)
	{
		return assinanteService.pesquisarAssinantesPorUnidade(unidade);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{id}")
	public RestAssinante pesquisarPorId(@PathVariable String id) {
		return assinanteService.acharPorId(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping
	public void novo(@Valid @RequestBody RestAssinante restAssinante) {

		assinanteService.novo(restAssinante);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PatchMapping("/{id}")
	public void alterar(@Valid @RequestBody RestAssinante restAssinante, @PathVariable String id){
		assinanteService.update(id, restAssinante);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable String id)
	{
		assinanteService.deletar(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PatchMapping("{id}/{status}")
	public void ativarDesativar(@PathVariable String id, @PathVariable TipoStatusAssinante status)
	{
		assinanteService.update(id, status);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/listaSimples")
	public Collection<RestAssinante> listaSimples(){
		return assinanteService.listaSimples();
	}
}
