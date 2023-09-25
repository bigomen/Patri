package com.patri.plataforma.restapi.restmodel.request;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.AssinanteMapper;
import com.patri.plataforma.restapi.mapper.PautaMapper;
import com.patri.plataforma.restapi.mapper.TopicoMapper;
import com.patri.plataforma.restapi.mapper.UsuarioAssinanteMapper;
import com.patri.plataforma.restapi.model.LocalidadePauta;
import com.patri.plataforma.restapi.model.Municipio;
import com.patri.plataforma.restapi.model.Pauta;
import com.patri.plataforma.restapi.model.UF;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import com.patri.plataforma.restapi.model.enums.TipoProduto;
import com.patri.plataforma.restapi.model.enums.TipoStatusPauta;
import com.patri.plataforma.restapi.restmodel.RestAssinante;
import com.patri.plataforma.restapi.restmodel.RestLocalidade;
import com.patri.plataforma.restapi.restmodel.RestModel;
import com.patri.plataforma.restapi.restmodel.RestOrgao;
import com.patri.plataforma.restapi.restmodel.RestStatusPauta;
import com.patri.plataforma.restapi.restmodel.RestTopico;
import com.patri.plataforma.restapi.restmodel.RestUnidadeNegocio;
import com.patri.plataforma.restapi.restmodel.RestUsuarioAssinante;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Objeto request provador da classe Pauta.", name = "RestPautaRequest")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class RestPautaRequest extends RestModel<Pauta> implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Schema(name = "titulo", description = "Titulo da pauta.")
    @NotBlank(message = "{PTR-076}")
    @JsonProperty(value = "titulo")
    private String titulo;

    @Schema(name = "dataInicio", description = "Data inicial da pauta.")
    @NotNull(message = "{PTR-078}")
    @JsonProperty(value = "dataInicio")
    private LocalDate dataIni;

    @Schema(name = "dataFim", description = "Data final da pauta.")
    @JsonProperty(value = "dataFim")
    private LocalDate dataFim;

    @Schema(name = "horaInicio", description = "Hora inicial da pauta.", pattern = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "horaInicio")
    private String horaIni;

    @Schema(name = "horaFim", description = "Hora final da pauta.", pattern = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "horaFim")
    private String horaFim;

    @NotNull(message = "{PTR-054}")
    @JsonProperty(value = "unidadeNegocio")
    private RestUnidadeNegocio unidadeNegocio;

    @Schema(name = "orgao", description = "Orgao da pauta.")
    @NotNull(message = "{PTR-077}")
    @JsonProperty(value = "orgao")
    private RestOrgao orgao;

    @Schema(name = "localidades", description = "Localidades da pauta.")
    @JsonProperty(value = "localidades")
    private Collection<RestLocalidade> localidades;

    @Schema(name = "descricao", description = "Descricao da pauta.")
    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "tipo")
    private TipoProduto tipo;

    @JsonProperty(value = "urgente")
    private boolean urgente = false;

    @JsonIgnore
    private RestStatusPauta statusPauta;

    @JsonProperty(value = "status")
    private TipoStatusPauta tipoStatus;
    
    @Schema(name = "reports", title = "Reports da Pauta", description = "Reports vinculados à pauta. Informar os objetos contendo apenas os id's dos reports")
    @JsonProperty(value = "reports")
    private Collection<RestReportRequest> reports;
    
    @Schema(name = "assinantes", title = "Assinantes", description = "Assinantes vinculados à Pauta. Informar os objetos contendo apenas os id's dos assinantes")
    @JsonProperty(value = "assinantes")
    private Collection<RestAssinante> assinantes;
    
    @Schema(name = "usuarios", title = "Usuários", description = "Usuários assinantes vinculados à Pauta. Informar os objetos contendo apenas os id's dos usuários")
    @JsonProperty(value = "usuarios")
    private Collection<RestUsuarioAssinante> usuarios;
    
    @Schema(name = "topicos", title = "Tópicos", description = "Tópicos vinculados à Pauta. Informar os objetos contendo apenas os id's dos tópicos")
    @JsonProperty(value = "topicos")
    private Collection<RestTopico> topicos;

    public TipoStatusPauta getTipoStatus()
    {
        Stream<TipoStatusPauta> values = Stream.of(TipoStatusPauta.values());

        return values.filter(st -> st.getEncriptedId()
                .equals(this.statusPauta.getId())).findFirst().orElse(null);
    }
    public void setTipoStatus(TipoStatusPauta tipoStatus)
    {
        RestStatusPauta status = new RestStatusPauta();

        status.setId(tipoStatus.getEncriptedId());
        status.setDescricao(tipoStatus.getDescricao());

        this.statusPauta = status;
        this.tipoStatus = tipoStatus;
    }

    @Override
    public Pauta restParaModel()
    {
        Pauta pauta = PautaMapper.INSTANCE.convertToModel(this);

        if(this.localidades != null)
        {
            pauta.setLocalidades(this.localidades.stream().map(this::convertToLocalidade).collect(Collectors.toSet()));
            pauta.getLocalidades().forEach(localidadePauta -> localidadePauta.setPauta(pauta));
        }
        
        if(this.assinantes != null)
        {
        	pauta.setAssinantes(this.assinantes.stream().map(AssinanteMapper.INSTANCE::convertToModel).collect(Collectors.toSet()));
        }
        
        if(this.usuarios != null)
        {
        	pauta.setUsuarios(this.usuarios.stream().map(UsuarioAssinanteMapper.INSTANCE::convertToModel).collect(Collectors.toSet()));
        }
        
        if(this.topicos != null)
        {
        	pauta.setTopicos(this.topicos.stream().map(TopicoMapper.INSTANCE::convertToModel).collect(Collectors.toSet()));
        }


        return pauta;
    }

    private LocalidadePauta convertToLocalidade(RestLocalidade localidade)
    {
        Long id = UtilSecurity.decryptId(localidade.getId());

        if(localidade.getTipo().equals(TipoLocalidade.MUN))
        {
            Municipio municipio = new Municipio();
            municipio.setId(id);
            return new LocalidadePauta(null, null, municipio, null);
        }

        UF uf = new UF();
        uf.setId(id);
        return new LocalidadePauta(null, null, null, uf);
    }
}
