package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.LocalidadeControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.service.LocalidadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(value = "/localidades/v1")
@RestController
@Tag(name = SwaggerConstantes.LOCALIDADE_TAG)
public class LocalidadeController implements LocalidadeControllerDoc
{
	private final LocalidadeService localidadeService;

	@Autowired
	public LocalidadeController(LocalidadeService localidadeService)
	{
		super();
		this.localidadeService = localidadeService;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping
	public Collection<RestLocalidade> consultarLocalidades(@RequestParam(name = "parametro") String parametro)
	{
		return localidadeService.consultarLocalidades(parametro);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/assinante/{id}")
	public Collection<RestLocalidade> consultarLocalidadesAssinante(@PathVariable(name = "id") String idAssinante, @RequestParam(value = "param", required = false) String param)
	{
		return localidadeService.consultaLocalidades(idAssinante, param);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/listaSimples")
	public Collection<RestLocalidade> listaSimples(){
		return localidadeService.listaSimples();
	}
}
