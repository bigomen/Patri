package com.patri.plataforma.restapi.schedule;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.StatusReport;
import com.patri.plataforma.restapi.repository.PautaRepository;
import com.patri.plataforma.restapi.repository.ReportRepository;
import com.patri.plataforma.restapi.tasks.TaskPauta;
import com.patri.plataforma.restapi.tasks.TaskReport;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PatriScheduling
{
    @Autowired
    ReportRepository reportRepository;
    
    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    TaskReport taskReport;
    
    @Autowired
    TaskPauta taskPauta;

//  @Scheduled(cron = "0 30 11,17 ? * MON-FRI")
//    @Scheduled(cron = "0 */10 * ? * *")
    public void newsletterScheduller()
    {
        log.debug("INICIANDO ROTINA DE ENVIO DE EMAILS DE NEWSLETTERS");

        Collection<Report> reports = reportRepository.pesquisarReports(new StatusReport(1L));
        log.debug("QUANTIDADE DE REPORTS PARA ENVIO: {}", reports.size());

        taskReport.enviaNewsLetter(reports);

        log.debug("ROTINA DE ENVIO DE EMAILS DE NEWSLETTERS ENCERRADA");
    }
    
//    @Scheduled(cron = "0 0 11 ? * MON-FRI")
//    @Scheduled(cron = "0 */10 * ? * *")
    public void agendaEsplanadaESP()
    {
    	log.debug("INICIANDO ROTINA DE ENVIO DE EMAILS DE PAUTAS");

        Collection<Pauta> pautas = pautaRepository.listarPautas(LocalDate.now(), LocalDate.now());
        log.debug("QUANTIDADE DE PAUTAS PARA ENVIO: {}", pautas.size());

        taskPauta.enviaPauta(pautas);

        log.debug("ROTINA DE ENVIO DE EMAILS DE PAUTAS ENCERRADA");
    }

//    @Scheduled(cron = "0 30 12 ? * MON")
//    @Scheduled(cron = "0 */10 * ? * *")
    public void agendaEspecifica()
    {

        log.debug("INICIANDO ROTINA DE ENVIO DE EMAILS DE PAUTAS");

        Collection<Pauta> pautas = pautaRepository.listarPautas(LocalDate.now(), LocalDate.now().plusDays(4));
        log.debug("QUANTIDADE DE PAUTAS PARA ENVIO: {}", pautas.size());

        taskPauta.enviaPauta(pautas);

        log.debug("ROTINA DE ENVIO DE EMAILS DE PAUTAS ENCERRADA");

    }
}
