package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.PautaControllerDoc;
import com.patri.plataforma.restapi.restmodel.request.RestPautaRequest;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;
import com.patri.plataforma.restapi.service.PautaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/pauta/v1")
@Validated
@Tag(name = SwaggerConstantes.PAUTA_TAG)
public class PautaController implements PautaControllerDoc
{
    private final PautaService pautaService;

    @Autowired
    public PautaController(PautaService pautaService)
    {
        this.pautaService = pautaService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/lista")
    public Collection<RestPautaResponse> listar(@RequestParam(value = "dataInicio", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
                                                @RequestParam(value = "dataFim", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
                                                @RequestParam(value = "topico", required = false) String topico,
                                                @RequestParam(value = "unidadeNegocio", required = false) String unidadeNegocio,
                                                @RequestParam(value = "id", required = false) Long id)
    {
        return pautaService.listar(dataInicio, dataFim, topico, unidadeNegocio, id);
    }

    @GetMapping("/{id}")
    public RestPautaResponse pesquisarPorId(@Valid @PathVariable Long id)
    {
        return pautaService.acharPorId(id);
    }

	@ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void novo(@Valid @RequestBody RestPautaRequest restPauta)
    {
        pautaService.novo(restPauta);
    }

	@ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{id}")
    public void alterar(@Valid @RequestBody RestPautaRequest restPautaRequest, @PathVariable Long id)
    {
		restPautaRequest.setId(String.valueOf(id));
        pautaService.alterar(restPautaRequest);
    }

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id)
    {
        pautaService.deletar(id);
    }
}
