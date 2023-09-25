package com.patri.plataforma.restapi.repository.custom;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.LocalidadePauta;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.Pauta_;
import com.patri.plataforma.restapi.model.Report;
import com.patri.plataforma.restapi.model.StatusPauta_;
import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.model.Topico_;
import com.patri.plataforma.restapi.model.UsuarioAssinante;
import com.patri.plataforma.restapi.repository.AssinanteRepository;
import com.patri.plataforma.restapi.repository.LocalidadePautaRepository;
import com.patri.plataforma.restapi.repository.ReportRepository;
import com.patri.plataforma.restapi.repository.TopicoRepository;
import com.patri.plataforma.restapi.repository.UsuarioAssinanteRepository;

public class PautaRepositoryCustomImpl implements PautaRepositoryCustom
{

    private EntityManager em;
    private final AssinanteRepository assinanteRepository;
    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final TopicoRepository topicoRepository;
    private final LocalidadePautaRepository localidadePautaRepository;
    private final ReportRepository reportRepository;
    

    @Autowired
    public PautaRepositoryCustomImpl(EntityManager em, UsuarioAssinanteRepository usuarioAssinanteRepository, 
    		TopicoRepository topicoRepository, LocalidadePautaRepository localidadePautaRepository, 
    		AssinanteRepository assinanteRepository, ReportRepository reportRepository)
    {
        super();
        this.em = em;
		this.assinanteRepository = assinanteRepository;
		this.usuarioAssinanteRepository = usuarioAssinanteRepository;
		this.topicoRepository = topicoRepository;
		this.localidadePautaRepository = localidadePautaRepository;
		this.reportRepository = reportRepository;
    }

    @Override
    public Collection<Pauta> listarPautas(LocalDate dataInicio, LocalDate dataFim, Long topico, Long unidadeNegocio, Long id)
    {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pauta> criteriaQuery = builder.createQuery(Pauta.class);
        Root<Pauta> root = criteriaQuery.from(Pauta.class);
        Predicate where =  em.getCriteriaBuilder().conjunction();

        if(topico != null)
        {
            Join<Pauta, Topico> joinTopico = root.join(Pauta_.TOPICOS, JoinType.INNER);
            Predicate inTopico = joinTopico.get(Topico_.ID).in(Arrays.asList(topico));
            where = builder.and(where, inTopico);
        }
        if(unidadeNegocio != null)
        {
            Predicate equalUnidade = builder.equal(root.get(Pauta_.UNIDADE_NEGOCIO), unidadeNegocio);
            where = builder.and(where, equalUnidade);
        }
        if(dataInicio != null && dataFim != null)
        {
            Predicate betweenData = builder.between(root.get(Pauta_.DATA_INI), dataInicio, dataFim);
            where = builder.and(where, betweenData);
        }
        if(id != null)
        {
            Predicate equalId = builder.equal(root.get(Pauta_.ID), id);
            where = builder.and(where, equalId);
        }

        criteriaQuery.where(where);

        criteriaQuery.orderBy(builder.asc(root.get(Pauta_.DATA_INI)), builder.asc(root.get(Pauta_.HORA_INI)));
        TypedQuery<Pauta> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

	@Override
	public Collection<Pauta> listarPautas(LocalDate inicio, LocalDate fim)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pauta> criteriaQuery = builder.createQuery(Pauta.class);
        Root<Pauta> root = criteriaQuery.from(Pauta.class);
        
        Predicate where = builder.equal(root.get(Pauta_.STATUS_PAUTA).get(StatusPauta_.ID), 1);
        Predicate betweenData = builder.between(root.get(Pauta_.DATA_INI), inicio, fim);
        where = builder.and(where, betweenData);
        
        criteriaQuery.where(where);

        criteriaQuery.orderBy(builder.asc(root.get(Pauta_.DATA_INI)), builder.asc(root.get(Pauta_.HORA_INI)));
        TypedQuery<Pauta> typedQuery = em.createQuery(criteriaQuery);

        return typedQuery.getResultList();
	}

	@Override
	public Optional<Pauta> pesquisarPorId(Long id)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pauta> criteriaQuery = builder.createQuery(Pauta.class);
        Root<Pauta> root = criteriaQuery.from(Pauta.class);
        root.fetch(Pauta_.UNIDADE_NEGOCIO, JoinType.INNER);
        root.fetch(Pauta_.ORGAO, JoinType.INNER);
        root.fetch(Pauta_.STATUS_PAUTA, JoinType.INNER);
        
        Predicate where = builder.equal(root.get(Pauta_.ID), id);
        criteriaQuery.where(where);
        
        TypedQuery<Pauta> typedQuery = em.createQuery(criteriaQuery);
        Pauta pauta = null;
        
        try
        {
        	pauta = typedQuery.getSingleResult();
        }catch(NoResultException nex)
        {
        	return Optional.empty();
        }
        
        Set<Assinante> assinantesPorPlano = new HashSet<Assinante>(assinanteRepository.pesquisarAssinantesPorPauta(pauta.getId()));
        pauta.setAssinantes(assinantesPorPlano);
        
        Set<Topico> topicosPorPlano = new HashSet<Topico>(topicoRepository.pesquisarTopicosPorPauta(pauta.getId()));
        pauta.setTopicos((Set<Topico>) topicosPorPlano);
        
        Collection<LocalidadePauta> localidades = localidadePautaRepository.findByPautaId(pauta.getId());
        pauta.setLocalidades(localidades);
        
        Set<UsuarioAssinante> usuarios =new HashSet<UsuarioAssinante>( usuarioAssinanteRepository.pesquisarUsuariosPorPauta(pauta.getId()));
        pauta.setUsuarios((Set<UsuarioAssinante>) usuarios);
        
        Collection<Report> reports = reportRepository.findByPautaId(pauta.getId());
        pauta.setReports(reports);
        
        return Optional.of(pauta);
	}
}
