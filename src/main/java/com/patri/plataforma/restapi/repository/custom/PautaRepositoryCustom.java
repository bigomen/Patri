package com.patri.plataforma.restapi.repository.custom;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import com.patri.plataforma.restapi.model.Pauta;

public interface PautaRepositoryCustom
{
    Collection<Pauta> listarPautas(LocalDate dataInicio, LocalDate dataFim, Long topico, Long unidadeNegocio, Long id);
    
    Collection<Pauta> listarPautas(LocalDate inicio, LocalDate fim);
    
    Optional<Pauta> pesquisarPorId(Long id);
}
