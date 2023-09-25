package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.UsuarioAssinanteMapper;
import com.patri.plataforma.restapi.model.*;
import com.patri.plataforma.restapi.model.enums.TipoEnvioEmail;
import com.patri.plataforma.restapi.model.enums.TipoLocalidade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author rcerqueira
 */

@Schema(description = "Objeto provedor da classe Usuário Assinante.", name = "RestUsuario")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestUsuarioAssinante extends RestModel<UsuarioAssinante> implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "nome", description = "Nome do usuário assinante.", required = true)
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @NotBlank(message = "{PTR-051}")
    @Size(max = 60, message = "{PTR-067}")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "sobrenome", description = "Sobrenome do usuário assinante.", required = true)
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @NotBlank(message = "{PTR-104}")
    @Size(max = 60, message = "{PTR-067}")
    @JsonProperty(value = "sobrenome")
    private String sobrenome;

    @Schema(name = "login", description = "Login do usuário assinante.", required = true)
    @Email(message = "{PTR-205}")
    @NotBlank(message = "{PTR-025}")
    @Size(max = 128, message = "{PTR-063}")
    @JsonProperty(value = "login")
    private String login;

    @Schema(name = "cargo", description = "Cargo do usuário assinante.")
    @Size(max = 64, message = "{PTR-069}")
    @JsonProperty(value = "cargo")
    private String cargo;

    @Schema(name = "setor", description = "Setor do usuário assinante.")
    @Size(max = 64, message = "{PTR-069}")
    @JsonProperty(value = "setor")
    private String setor;

    @Schema(name = "assinante", description = "Assinante referente ao Usuario Assinante.", required = true)
    @NotNull(message = "{PTR-042}")
    @JsonProperty(value = "assinante")
    private RestAssinante assinante;


    @Schema(name = "localidadesAssinantes")
    @JsonProperty(value = "localidadesAssinantes")
    private Collection<RestLocalidade> localidadesAssinantes;

    @Schema(name = "ativo", description = "Atividade do Usuário Assinante. Por Padrão o valor é true.", required = true)
    @NotNull(message = "{PTR-204}")
    @JsonProperty(value = "ativo")
    private boolean ativo;

    @Schema(name = "topicos", description = "Topicos do Usuario Assinante.")
    @JsonProperty(value = "topicos")
    private Collection<RestTopico> topicos;

    @Schema(name = "data", description = "Data da última alteração do Usuário Assinante.")
    @JsonProperty(value = "data")
    private LocalDateTime ultimaAlteracao;

    @Schema(name = "enviaEmail", description = "Forma que o Usuário Assinante receberá as notícias.", allowableValues = "D: Direto, N: Newsletter", required = true)
    @JsonProperty(value = "enviaEmail")
    @NotNull(message = "{PTR-210}")
    private TipoEnvioEmail envioEmail;

    @Override
    public UsuarioAssinante restParaModel()
    {
        UsuarioAssinante usuarioAssinante = UsuarioAssinanteMapper.INSTANCE.convertToModel(this);

        Assinante assinante = new Assinante();
        
        if(this.assinante != null)
        {
        	assinante.setId(UtilSecurity.decryptId(this.getAssinante().getId()));
        	usuarioAssinante.setAssinante(assinante);
        }

        
        if(this.localidadesAssinantes != null)
        {
        	usuarioAssinante.setLocalidadesAssinantes(this.localidadesAssinantes
        			.stream()
        			.map(this::convertToLocalidade)
        			.collect(Collectors.toList()));
            usuarioAssinante.getLocalidadesAssinantes().forEach(localidadeAssinante -> localidadeAssinante.setAssinante(assinante));
        }

        if(this.topicos != null)
        {
        	for (RestTopico restTopico : this.topicos)
        	{
        		Topico topico = new Topico();
        		topico.setId(UtilSecurity.decryptId(restTopico.getId()));
        		usuarioAssinante.adicionaTopico(topico, assinante, usuarioAssinante);
        	}
        }

        return usuarioAssinante;
    }

    private LocalidadeAssinante convertToLocalidade(RestLocalidade localidade)
    {
        Long id = UtilSecurity.decryptId(localidade.getId());

        if(localidade.getTipo().equals(TipoLocalidade.MUN))
        {
            Municipio municipio = new Municipio();
            municipio.setId(id);
            return new LocalidadeAssinante(null, null, municipio, null, null);
        }

        UF uf = new UF();
        uf.setId(id);
        return new LocalidadeAssinante(null, null, null, uf, null);
    }
}
