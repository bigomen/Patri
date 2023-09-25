package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.patri.plataforma.restapi.mapper.ItemReportMapper;
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.*;
import com.patri.plataforma.restapi.restmodel.response.RestItemReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.model.enums.TipoStatusReport;
import com.patri.plataforma.restapi.repository.ItemRepository;
import com.patri.plataforma.restapi.repository.ReportRepository;
import com.patri.plataforma.restapi.repository.UnidadeNegocioRepository;
import com.patri.plataforma.restapi.restmodel.request.RestReportRequest;
import com.patri.plataforma.restapi.restmodel.response.RestReportResponse;
import com.patri.plataforma.restapi.tasks.TaskReport;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ReportService extends com.patri.plataforma.restapi.service.Service<Report, RestReportResponse>
{
    private final ReportRepository reportRepository;
    private final ItemRepository itemRepository;
    private final TaskReport taskReport;
    private final UnidadeNegocioRepository unidadeNegocioRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository,
                         ItemRepository itemRepository, TaskReport taskReport, UnidadeNegocioRepository unidadeNegocioRepository)
    {
        super(); this.reportRepository = reportRepository;
		this.itemRepository = itemRepository;
		this.taskReport = taskReport;
		this.unidadeNegocioRepository = unidadeNegocioRepository;
    }

    public Collection<RestReportResponse> listar(String param)
    {
        Collection<Report> reports = reportRepository.listarReports(param);
        
        return reports.stream()
        		.map(report -> ReportMapper.INSTANCE.convertToSimpleRest(report))
        		.collect(Collectors.toList());
    }

    public RestReportResponse encontrarPorId(String id)
    {
    	Optional<Report> opReport = reportRepository.pesquisarPorId(UtilSecurity.decryptId(id));
    	Report report = opReport.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.REPORT_INVALIDO));
        return report.modelParaRest();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestReportRequest restReport)
    {
        Report report = restReport.restParaModel();
		report = reportRepository.save(report);
		
		if(report.isAtivo())
		{
			taskReport.enviaReport(report);
		}
    }

    @Transactional(propagation = Propagation.REQUIRED)
	public void update(String id, RestReportRequest restReport)
    {
    	Optional<Report> optionalReport = reportRepository.pesquisarPorId(UtilSecurity.decryptId(id));
    	
    	Report report = optionalReport.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.REPORT_INVALIDO));
    	
    	Report update = restReport.restParaModel();

		List<Item> listaItensAntigos = buscaItensPorId(update);
		atualizaStatusItemExistente(update, listaItensAntigos);
    	
    	update.setId(report.getId());
    	update.setStatus(new StatusReport(2L));

    	if(report.getTipo().equals(TipoProduto.A))
    	{
    		update.setTopicos(report.getTopicos());
    		update.setLocalidades(report.getLocalidades());
    	}
    	
    	if(report.getTipo().equals(TipoProduto.T))
    	{
    		update.setAssinantes(report.getAssinantes());
    	}

        update = reportRepository.save(update);
        
		if(report.isAtivo())
		{
			taskReport.enviaReport(update);
		}
    }

	@Transactional(propagation = Propagation.REQUIRED)
    public void removerItem(String id)
    {
    	Optional<Item> opItem = itemRepository.findById(UtilSecurity.decryptId(id));
    	Item item = opItem.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.REPORT_INVALIDO));
//    	item.setAtivo(false);
    	itemRepository.delete(item);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void alterarStatus(String id, TipoStatusReport status)
    {
    	Optional<Report> opReport = reportRepository.findById(UtilSecurity.decryptId(id));
    	Report report = opReport.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, MensagensID.REPORT_INVALIDO));

		if (status.equals(TipoStatusReport.A)) {
			validarDadosTipoProduto(report);
			validarDadosAtivarReport(report);
		}

    	report.setStatus(new StatusReport(status.getId()));
    	reportRepository.save(report);
    }

	public Collection<RestItemReportResponse> listarHistoricoItens(String id) {
		Collection<Item> items = itemRepository.historicoItens(UtilSecurity.decryptId(id));
		return items.stream().map(item -> ItemReportMapper.INSTANCE.convertToRest(item)).collect(Collectors.toList());
	}

	private List<Item> buscaItensPorId(Report update) {
		return update.getItens().stream()
				.filter(item -> item.getId() != null)
				.map(item -> itemRepository.findById(item.getId())
						.orElseThrow(() -> new PatriRuntimeException(HttpStatus.NOT_FOUND, "Item com ID inválido")))
				.collect(Collectors.toList());
	}

	private void atualizaStatusItemExistente(Report update, List<Item> listaItensAntigos) {
		update.getItens().forEach(item -> {
			Optional<Item> firstAntigo = listaItensAntigos
					.stream()
					.filter(itemAntigo -> (itemAntigo.getId().equals(item.getId()) && !itemAntigo.equals(item)))
					.findFirst();
			firstAntigo.ifPresent(antigo -> {
				item.setId(null);
				antigo.setAtivo(false);
				update.adicionaItem(antigo);
			});
		});
	}

    private void validarDadosTipoProduto(Report report) {
    	if (report.getTipo().equals(TipoProduto.T)) {
    		if (report.getTopicos() == null || report.getTopicos().isEmpty()) {
    			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.TOPICOS);
    		}

			UnidadeNegocio unidadeNegocio = unidadeNegocioRepository.findById(report.getUnidadeNegocio().getId())
					.orElseThrow(() -> new ObjectNotFoundException("Unidade Negócio"));

			if (unidadeNegocio.isEstadosEMunicipios()) {
    			if (report.getLocalidades() == null || report.getLocalidades().isEmpty()) {
    				throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.LOCALIDADES);
    			}
    		}
    	}
    	if (report.getTipo().equals(TipoProduto.A)) {
    		if (report.getAssinantes() == null || report.getAssinantes().isEmpty()) {
    			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.ASSINANTES);
    		}
    	}
    }

	private void validarDadosAtivarReport(Report report) {
		if (report.getTitulo() == null || report.getTitulo().isEmpty()) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR076);
		}
		if (report.getIdentificador() == null || report.getIdentificador().isEmpty()) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR206);
		}
		if (report.getNewsletter() == null) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR207);
		}
		if (report.getTipo() == null) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR037);
		}
		if (report.getItens() == null || report.getItens().isEmpty()) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR208);
		}
		if (report.getUnidadeNegocio() == null) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR054);
		}
		if (report.getOrgao() == null) {
			throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR077);
		}
	}

	@Override
	protected CrudRepository<Report, Long> getRepository()
	{
		return reportRepository;
	}
}
