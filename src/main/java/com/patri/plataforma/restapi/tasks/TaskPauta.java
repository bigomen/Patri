package com.patri.plataforma.restapi.tasks;

import com.patri.plataforma.restapi.mapper.PautaMapper;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.UsuarioAssinante;
import com.patri.plataforma.restapi.model.enums.TipoEnvioEmail;
import com.patri.plataforma.restapi.repository.UsuarioAssinanteRepository;
import com.patri.plataforma.restapi.tasks.model.email.PautaEmail;
import com.patri.plataforma.restapi.utility.EnviaEmail;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class TaskPauta
{
    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final EnviaEmail enviaEmail;

    public TaskPauta(UsuarioAssinanteRepository usuarioAssinanteRepository,
                     EnviaEmail enviaEmail)
    {
        this.usuarioAssinanteRepository = usuarioAssinanteRepository;
        this.enviaEmail = enviaEmail;
    }

    public void enviaPauta(Collection<Pauta> pautas)
    {
        for(Pauta pauta : pautas)
        {
            Collection<UsuarioAssinante> destinatarios = pesquisarDestinatariosPauta(pauta);
            destinatarios = destinatarios
                    .stream()
                    .filter(d -> d.getEnvioEmail().equals(TipoEnvioEmail.N))
                    .collect(Collectors.toList());

            enviaEmailPauta(pauta, destinatarios);

            //FIXME : Adcionar flag de envio
        }
    }

    public void enviarPauta(Pauta pauta)
    {
        Collection<UsuarioAssinante> destinatariosPauta = pesquisarDestinatariosPauta(pauta);

        if(!pauta.isUrgente())
        {
            destinatariosPauta = destinatariosPauta
                    .stream()
                    .filter(d -> d.getEnvioEmail().equals(TipoEnvioEmail.D))
                    .collect(Collectors.toList());
        }

        enviaEmailPauta(pauta, destinatariosPauta);
    }

    private Collection<UsuarioAssinante> pesquisarDestinatariosPauta(Pauta pauta)
    {
        Collection<UsuarioAssinante> usuarios = new HashSet<>();

        Collection<UsuarioAssinante> usuarioPautaPorTopico = usuarioAssinanteRepository.findUsuarioPautaPorTopico(pauta.getId());
        Collection<UsuarioAssinante> usuariosPautaPorLocalidade = usuarioAssinanteRepository.findUsuariosPautaPorLocalidade(pauta.getId());

        usuarios.addAll(usuarioPautaPorTopico);
        usuarios.addAll(usuariosPautaPorLocalidade);

        return usuarios;
    }

    private void enviaEmailPauta(Pauta pauta, Collection<UsuarioAssinante> destinatarios)
    {
        if(!destinatarios.isEmpty())
        {
            PautaEmail pautaEmail = PautaMapper.INSTANCE.convertToTemplateEmail(pauta);
            enviaEmail.enviaEmailPauta(pautaEmail, destinatarios);
        }
    }
}
