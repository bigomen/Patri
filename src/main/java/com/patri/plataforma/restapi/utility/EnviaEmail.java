package com.patri.plataforma.restapi.utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.model.Usuario;
import com.patri.plataforma.restapi.model.UsuarioAssinante;
import com.patri.plataforma.restapi.tasks.model.email.AgendaEmail;
import com.patri.plataforma.restapi.tasks.model.email.NewsLetterEmail;
import com.patri.plataforma.restapi.tasks.model.email.PautaEmail;
import com.patri.plataforma.restapi.tasks.model.email.ReportEmail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EnviaEmail {

    @Value(value = "${sendgrid.key}")
    private String sendgridKey;
    
    @Value(value = "${sendgrid.template.id.novo.usuario}")
    private String TEMPLATE_EMAIL_NOVO_USUARIO;
    
    @Value(value = "${sendgrid.template.id.reset.usuario}")
    private String TEMPLATE_EMAIL_RESET_SENHA;
    
    @Value(value = "${sendgrid.template.from}")
    private String FROM;

    @Value(value = "${email.habilitaEnvio}")
    private boolean habilitaEnvio;

    @Value(value = "${sendgrid.template.id.report}")
    private String TEMPLATE_ID_REPORT;

    @Value(value = "${sendgrid.template.id.pauta}")
    private String TEMPLATE_ID_PAUTA;
    
    @Value(value = "${sendgrid.template.id.newsletter}")
    private String TEMPLATE_ID_NEWSLETTER;

    @Async
    public void enviarEmailUsuario(Usuario usuario, URL url, boolean novoUsuario)
    {
    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("link", url.toString());

    	Email email = new Email(usuario.getLogin(), usuario.getNome());
    	enviarEmail(parametros, novoUsuario ? TEMPLATE_EMAIL_NOVO_USUARIO : TEMPLATE_EMAIL_RESET_SENHA, email);
    }

    public void enviaEmailReport(ReportEmail report, Collection<UsuarioAssinante> usuarios)
    {
        Collection<Email> emails = getEmails(usuarios);
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("report", report);
        enviarEmail(parametros, TEMPLATE_ID_REPORT, emails);
    }
    
    public void enviaEmailNewsletter(NewsLetterEmail newsletter, Collection<UsuarioAssinante> usuarios)
    {
    	 Collection<Email> emails = getEmails(usuarios);
         Map<String, Object> parametros = new HashMap<>();
    
         parametros.put("reports", newsletter);
         enviarEmail(parametros, TEMPLATE_ID_NEWSLETTER, emails);
    }

    public void enviaEmailPauta(PautaEmail pautaEmail, Collection<UsuarioAssinante> usuarios)
    {
        Collection<Email> emails = getEmails(usuarios);
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("pauta", pautaEmail);
        enviarEmail(parametros, TEMPLATE_ID_PAUTA, emails);
    }
    
    public void enviaEmailPauta(AgendaEmail agenda, Collection<UsuarioAssinante> usuarios)
    {
        Collection<Email> emails = getEmails(usuarios);
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("pautas", agenda);
        enviarEmail(parametros, TEMPLATE_ID_PAUTA, emails);
    }


    private Collection<Email> getEmails(Collection<UsuarioAssinante> usuarios)
    {
        Collection<Email> emails = new ArrayList<>();

        for (UsuarioAssinante u : usuarios)
        {
            Email email = new Email(u.getLogin(), u.getNome());
            emails.add(email);
        }
        return emails;
    }
    
    private void enviarEmail(Map<String, Object> parametros, String templateID, Collection<Email> destinatarios)
    {
    	enviarEmail(parametros, templateID, destinatarios.toArray(new Email[destinatarios.size()]));
    }

    private void enviarEmail(Map<String, Object> parametros, String templateID, Email ... destinatarios)
    {
    	parametros.put("data", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    	
    	final Mail mail = new Mail();
        Email from = new Email(FROM, "Não Responda");
        mail.setFrom(from);

        for(Email dest : destinatarios)
        {
            Personalization personalization = new Personalization();
            //personalization.addTo(dest);
            personalization.addTo(new Email("gperinotto@luxfacta.com"));
            personalization.addDynamicTemplateData("usuario", dest.getName());

            for (Map.Entry<String, Object> entry : parametros.entrySet())
            {
                String key = entry.getKey();
                Object value = entry.getValue();
                personalization.addDynamicTemplateData(key, value);
            }

            mail.setTemplateId(templateID);
            mail.addPersonalization(personalization);
        }

        if(habilitaEnvio && mail.personalization != null && !mail.personalization.isEmpty())
        {
            this.enviar(mail);
        }
    }

    private Response enviar(Mail mail)
    {
          try 
          {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            SendGrid sg = new SendGrid(sendgridKey);
            sg.addRequestHeader("X-Mock", "true");
            Response response = sg.api(request);
            log.debug("RESULTADO PARA ENVIO DO EMAIL: CODE {}, BODY {}", response.getStatusCode(), response.getBody());
            return response;
          } catch (IOException me) 
          {
              log.error("FALHA NO ENVIO DE EMAIL: {}", me);
              throw new PatriRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, MensagensID.PTR003);
          }
    }
}
