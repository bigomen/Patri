package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.UsuarioBackOfficeMapper;
import com.patri.plataforma.restapi.model.UsuarioBackOffice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe UsuarioBackOffice", name = "ResUsuarioBackOffice")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestUsuarioBackOffice extends RestModel<UsuarioBackOffice> implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "login", description = "Login do usuário referente ao UsuarioBackOffice", required = true)
    @Size(max = 128, message = "{PTR-063}")
    @NotBlank(message = "{PTR-071}")
    @JsonProperty(value = "login")
    private String login;

    @Schema(name = "nome", description = "Nome do usuário referente ao UsuarioBackOffice", required = true)
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @Size(max = 60, message = "{PTR-067}")
    @NotBlank(message = "{PTR-051}")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "sobrenome", description = "Sobrenome do usuário referente ao UsuarioBackOffice", required = true)
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @Size(max = 60, message = "{PTR-067}")
    @NotBlank(message = "{PTR-104}")
    @JsonProperty(value = "sobrenome")
    private String sobrenome;

    @Schema(name = "grupo", description = "Grupo do Usuario referente ao UsuarioBackOffice", required = true)
    @NotNull(message = "{PTR-105}")
    @JsonProperty(value = "grupo")
    private RestGrupoBackOffice grupo;

    @Schema(name = "unidade", description = "Unidade de Negócio referente ao UsuarioBackOffice", required = true)
    @NotNull(message = "{PTR-054}")
    @JsonProperty(value = "unidade")
    private RestUnidadeNegocio unidadeNegocio;

    @Schema(name = "ativo", description = "Atividade do Usuário BackOffice. Por Padrão o valor é true.", required = true)
    @JsonProperty(value = "ativo")
    private Boolean ativo;

    @Override
    public UsuarioBackOffice restParaModel() {
        return UsuarioBackOfficeMapper.INSTANCE.convertToModel(this);
    }
}
