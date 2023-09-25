package com.patri.plataforma.restapi.tasks;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.UnidadeNegocio;
import com.patri.plataforma.restapi.model.UsuarioAssinante;
import com.patri.plataforma.restapi.model.enums.TipoEnvioEmail;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.repository.ItemRepository;
import com.patri.plataforma.restapi.repository.ReportRepository;
import com.patri.plataforma.restapi.repository.UsuarioAssinanteRepository;
import com.patri.plataforma.restapi.tasks.model.email.NewsLetterEmail;
import com.patri.plataforma.restapi.tasks.model.email.ReportEmail;
import com.patri.plataforma.restapi.utility.EnviaEmail;

@Component
public class TaskReport
{
    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final ReportRepository reportRepository;
    private final ItemRepository itemRepository;
    private final EnviaEmail enviaEmail;
    
    @Autowired
    public TaskReport(UsuarioAssinanteRepository usuarioAssinanteRepository,
			ReportRepository reportRepository, ItemRepository itemRepository,
			EnviaEmail enviaEmail)
	{
		super();
		this.usuarioAssinanteRepository = usuarioAssinanteRepository;
		this.reportRepository = reportRepository;
		this.itemRepository = itemRepository;
		this.enviaEmail = enviaEmail;
	}

    public void enviaNewsLetter(Collection<Report> reports)
    {
    	Map<TipoProduto, List<Report>> mapReportsPorTipo = reports.stream().collect(Collectors.groupingBy(Report::getTipo));
    	
    	for(TipoProduto tipo : mapReportsPorTipo.keySet())
    	{
    		List<Report> reportsPorTipo = mapReportsPorTipo.get(tipo);
    		Map<UnidadeNegocio,List<Report>> mapReportsPorUnidade = reportsPorTipo.stream().collect(Collectors.groupingBy(Report::getUnidadeNegocio));
    		
    		loopUnidades: for(UnidadeNegocio un : mapReportsPorUnidade.keySet())
        	{
        		List<Report> reportsPorUnidade = mapReportsPorUnidade.get(un);
        		Collection<UsuarioAssinante> destinatarios = pesquisaDestinatariosReport(reportsPorUnidade);
        		
        		if(destinatarios.isEmpty())
        		{
        			continue loopUnidades;
        		}
        		
        		destinatarios = destinatarios.stream().filter(u-> u.getEnvioEmail().equals(TipoEnvioEmail.N)).collect(Collectors.toSet());
        		complementaInformacoesReport(reportsPorUnidade);
        		NewsLetterEmail newsLetterEmail = NewsLetterEmail.montaObjetoNewsletter(reportsPorUnidade);
                enviaEmail.enviaEmailNewsletter(newsLetterEmail, destinatarios);
        		
                reportsPorUnidade.forEach(r -> {
        			r.setEnviado(true);
        			r.setDataEnvioEmail(LocalDateTime.now());
        		});
        		reportRepository.saveAll(reportsPorUnidade);
        	}
    	}
    }
	
	@Async
    public void enviaReport(Report report)
    {
		if (report.isAtivo())
		{
			Collection<UsuarioAssinante> destinatarios = pesquisaDestinatariosReport(report);
			
			if (!report.isUrgente())
			{
				destinatarios = destinatarios.stream()
						.filter(d -> d.getEnvioEmail().equals(TipoEnvioEmail.D))
						.collect(Collectors.toList());
			}
			
			if (!destinatarios.isEmpty())
			{
				ReportEmail reportEmail = ReportMapper.INSTANCE.convertToTemplateEmail(report);
				enviaEmail.enviaEmailReport(reportEmail, destinatarios);
			}
		}
    }
	
	private void complementaInformacoesReport(Collection<Report> reports)
	{
		reports.forEach(r ->{
			List<Item> itens = itemRepository.findByReport(r);
			r.setItens(itens);
		});
	}
	
	private Collection<UsuarioAssinante> pesquisaDestinatariosReport(Collection<Report> reports)
	{
		Set<UsuarioAssinante> usuarios = new HashSet<>();
		
		for(Report report : reports)
		{
			Collection<UsuarioAssinante> destinatariosReport = pesquisaDestinatariosReport(report);
			usuarios.addAll(destinatariosReport);
		}
		
		return usuarios;
	}
    
    private Collection<UsuarioAssinante> pesquisaDestinatariosReport(Report report)
    {
        if(report.getTipo().equals(TipoProduto.A))
        {
            return usuarioAssinanteRepository.findUsuariosDoReport(report);
        }
        
        if(report.getUnidadeNegocio().isEstadosEMunicipios())
        {
        	return usuarioAssinanteRepository.findUsuariosDoReportPorLocalidade(report.getId());
        }

        return usuarioAssinanteRepository.findUsuariosDoReportPorTopico(report.getId());
    }
}
