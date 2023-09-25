package com.patri.plataforma.restapi.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import com.patri.plataforma.restapi.mapper.PautaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.StatusPauta;
import com.patri.plataforma.restapi.repository.PautaRepository;
import com.patri.plataforma.restapi.repository.ReportRepository;
import com.patri.plataforma.restapi.restmodel.request.RestPautaRequest;
import com.patri.plataforma.restapi.restmodel.response.RestPautaResponse;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PautaService extends com.patri.plataforma.restapi.service.Service<Pauta, RestPautaResponse>
{
    private final PautaRepository pautaRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository, ReportRepository reportRepository)
    {
        this.pautaRepository = pautaRepository;
		this.reportRepository = reportRepository;
    }

    public Collection<RestPautaResponse> listar(LocalDate dataInicio, LocalDate dataFim, String topico, String unidade, Long id)
    {
        Collection<Pauta> pautas = pautaRepository.listarPautas(dataInicio, dataFim, UtilSecurity.decryptId(topico), UtilSecurity.decryptId(unidade), id);

        return pautas.stream().map(p -> PautaMapper.INSTANCE.convertToSimpleRest(p)).collect(Collectors.toList());
    }

    public RestPautaResponse acharPorId(Long id)
    {
        Pauta pauta = pautaRepository.pesquisarPorId(id)
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
        return pauta.modelParaRest();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestPautaRequest restPauta)
    {
    	Pauta pauta = restPauta.restParaModel();
        pauta = pautaRepository.save(pauta);

        if(pauta.getReports() != null && !pauta.getReports().isEmpty())
        {
        	reportRepository.vincularReportsAPauta(pauta, pauta.getReports());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void alterar(RestPautaRequest restPauta)
    {
        Pauta pautaById = pautaRepository.findById(Long.parseLong(restPauta.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

        Pauta pauta = restPauta.restParaModel();
        pauta.setStatusPauta(pautaById.getStatusPauta());
        pautaRepository.save(pauta).modelParaRest();
        reportRepository.desvincularReportsPauta(pauta);
        
        if(pauta.getReports() != null && !pauta.getReports().isEmpty())
        {
        	reportRepository.vincularReportsAPauta(pauta, pauta.getReports());
        }
    }

    public void deletar(Long id)
    {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
        pauta.setStatusPauta(new StatusPauta(3L));
        pautaRepository.save(pauta);
    }

    @Override
    protected CrudRepository getRepository()
    {
        return pautaRepository;
    }

	@Override
	protected String funcionalidade()
	{
		return "Pauta";
	}
}
