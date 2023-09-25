package com.patri.plataforma.restapi.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.patri.plataforma.restapi.constants.MensagensID;
import com.patri.plataforma.restapi.exeptions.ObjectNotFoundException;
import com.patri.plataforma.restapi.exeptions.PatriRuntimeException;
import com.patri.plataforma.restapi.mapper.TopicoMapper;
import com.patri.plataforma.restapi.model.Assinante;
import com.patri.plataforma.restapi.model.Topico;
import com.patri.plataforma.restapi.model.TopicoUsuarioAssinante;
import com.patri.plataforma.restapi.repository.TopicoRepository;
import com.patri.plataforma.restapi.repository.TopicoUsuarioAssinanteRepository;
import com.patri.plataforma.restapi.restmodel.RestTopico;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TopicoService extends com.patri.plataforma.restapi.service.Service<Topico, RestTopico>
{
    private final TopicoRepository topicoRepository;
    private final TopicoUsuarioAssinanteRepository topicoUsuarioAssinanteRepository;
    private Collection<Topico> todosTopicos;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, TopicoUsuarioAssinanteRepository topicoUsuarioAssinanteRepository, TopicoUsuarioAssinanteRepository topicoUsuarioAssinanteRepository1) {
        super();
        this.topicoRepository = topicoRepository;
        this.topicoUsuarioAssinanteRepository = topicoUsuarioAssinanteRepository1;
    }

    public Collection<RestTopico> listarTopicos() 
    {
    	Iterable<Topico> findAll = topicoRepository.findAll();
		todosTopicos = StreamSupport.stream(findAll.spliterator(), false)
				.collect(Collectors.toList());
    	
    	Collection<RestTopico> rest = todosTopicos.stream()
    		.filter(Topico::isRoot)
    		.peek(t -> {
    			Collection<Topico> subTopicos = pesquisarTopico(t);
    			t.setSubTopicos(subTopicos);
    		})
    		.map(Topico::modelParaRest)
    		.collect(Collectors.toList());
        rest.forEach(r -> {
            if(r.getSubTopicos() != null){
                r.setSubTopicos(setPaiSubtopico(r.getSubTopicos(), r.getId()));
            }
        });
        return rest;
    }

    private Collection<RestTopico> setPaiSubtopico(Collection<RestTopico> sub, String idPai){
        for(RestTopico rt: sub){
            if(rt.getSubTopicos() != null){
                setPaiSubtopico(rt.getSubTopicos(), rt.getId());
            }
            rt.setTopicoPai(idPai);
        }
        return sub;
    }

    @Deprecated
    public Collection<RestTopico> listarSubTopicos(String id)
    {
        Collection<Topico> topicos = topicoRepository.listarSubTopicos(UtilSecurity.decryptId(id));

        return topicos.stream().map(Topico :: modelParaRest).collect(Collectors.toList());
    }

    public Collection<RestTopico> pesquisar(String param)
    {
        Collection<Topico> topicos = topicoRepository.pesquisar(param);
        
        return topicos.stream()
        		.map(Topico :: modelParaRest)
        		.collect(Collectors.toList());
    }

    public RestTopico obterPorId(String id) {
        Long newId = UtilSecurity.decryptId(id);
        Optional<Topico> topicoById = topicoRepository.findById(newId);
        Topico topico = topicoById.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
        return topico.modelParaRest();
    }
    
    public Collection<RestTopico> consultarTopicosPorAssinante(String idAssinante, String param)
    {
    	Collection<Topico> topicosPorAssinante = topicoRepository.findByAssinanteAndParam(UtilSecurity.decryptId(idAssinante), param);

    	List<RestTopico> topicos = topicosPorAssinante.stream().map(t -> TopicoMapper.INSTANCE.convertToRest(t)).collect(Collectors.toList());
    	return topicos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void novo(RestTopico restTopico) {
        Topico topico = new Topico();
        topico.setDescricao(restTopico.getDescricao());

        if(StringUtils.isNotBlank(restTopico.getTopicoPai()))
        {
            Topico topicoPai = topicoRepository.findById(UtilSecurity.decryptId(restTopico.getTopicoPai()))
                    .orElseThrow(() -> new ObjectNotFoundException(funcionalidade() + " pai"));

            topico.setPai(topicoPai);
        }

        topicoRepository.save(topico);
    }

    public void alterar(RestTopico restTopico, String id) throws Exception {
        Long newId = UtilSecurity.decryptId(id);
        Optional<Topico> topicoById = topicoRepository.findById(newId);

        Topico topico = topicoById.orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));
        topico.setDescricao(restTopico.getDescricao());

        topicoRepository.save(topico);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void alterar(String id, boolean status)
    {
        Topico topico = topicoRepository.findById(UtilSecurity.decryptId(id))
                .orElseThrow(() -> new ObjectNotFoundException(funcionalidade()));

        verificaTopicoComSubtopicosAtivos(topico);

        topico.setAtivo(status);

        if (!status) {
            Collection<TopicoUsuarioAssinante> topicosUsuarioAssinante = topicoUsuarioAssinanteRepository.findByIdIdTopico(topico.getId());
            topicoUsuarioAssinanteRepository.deleteAll(topicosUsuarioAssinante);
            topicoRepository.excluirTopicoAssinante(topico.getId());
        }

        topicoRepository.save(topico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void atualizarTopicosAssinantes(Assinante assinante) {
        Collection<TopicoUsuarioAssinante> topicosUsuariosAssinantes = topicoUsuarioAssinanteRepository.findByAssinante(assinante.getId());

        Collection<TopicoUsuarioAssinante> topicosDiferentes = topicosDiferentes(assinante.getTopicos(), topicosUsuariosAssinantes);
        topicoUsuarioAssinanteRepository.excluirTopicos(topicosDiferentes.stream()
                .map(TopicoUsuarioAssinante::getTopico)
                .collect(Collectors.toList()));
        
        for (int i = 0; i < assinante.getTopicos().size(); i++) {
        	Topico t = (Topico) assinante.getTopicos().toArray()[i];
        	Optional<Topico> tOpt = topicoRepository.findById(t.getId());
        	if (tOpt.isPresent()) t = tOpt.get();
        	
        	for (Topico tSub : t.getSubTopicos()) {
            	assinante.getTopicos().add(tSub);
        	}
        }

        
        
    }
    
    private Collection<TopicoUsuarioAssinante> topicosDiferentes(Collection<Topico> topicos,
                                                                 Collection<TopicoUsuarioAssinante> topicosUsuariosAssinantes) {
        if (topicos == null) {
            return Collections.EMPTY_LIST;
        }
        return topicosUsuariosAssinantes.stream()
                .filter(topicoUsuarioAssinante -> !topicos.contains(topicoUsuarioAssinante.getTopico()))
                .collect(Collectors.toList());
    }

    private Collection<Topico> pesquisarTopico(Topico pai)
    {
        if(pai.getQtdSubTopicos() == 0)
        {
            return null;
        }

        Collection<Topico> subTopicos = todosTopicos.stream()
                .filter(t-> t.isSubTopico() && t.getPai().equals(pai))
                .collect(Collectors.toList());
        subTopicos.forEach(st ->{
            Collection<Topico> sub = pesquisarTopico(st);
            st.setSubTopicos(sub);
        });

        return subTopicos;
    }

    private void verificaTopicoComSubtopicosAtivos(Topico topico) {
        Collection<Topico> subTopicos = topicoRepository.findByPaiOrderByDescricao(topico);
        Optional<Topico> subTopicoAtivo = subTopicos.stream().filter(Topico::getAtivo).findAny();
        if (subTopicoAtivo.isPresent()) {
            throw new PatriRuntimeException(HttpStatus.BAD_REQUEST, MensagensID.PTR506);
        }
    }

    public Collection<RestTopico> listaSimples(){
        Collection<Topico> topicos = topicoRepository.listaSimples();
        return topicos.stream().map(Topico::modelParaRest).collect(Collectors.toList());
    }

    @Override
    protected CrudRepository<Topico, Long> getRepository() {
        return topicoRepository;
    }

	@Override
	protected String funcionalidade()
	{
		return "Tópico";
	}
}
