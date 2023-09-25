package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.constants.Constantes;
import com.patri.plataforma.restapi.mapper.GrupoBackOfficeMapper;
import com.patri.plataforma.restapi.model.GrupoBackOffice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Schema(description = "Objeto provedor da classe GrupoBackOffice.", name = "RestGrupoBackOffice")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestGrupoBackOffice extends RestModel<GrupoBackOffice> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "descricao", description = "Texto descritivo do Grupo BackOffice", required = true)
    @NotBlank(message = "{PTR-047}")
    @Size(max = 40, message = "{PTR-020}")
    @JsonProperty(value = "descricao")
    private String descricao;

    @Schema(name = "nome", description = "Nome do Grupo BackOffice", required = true)
    @Pattern(regexp = Constantes.PATTERN_TEXTO_SEM_CARACTERES_ESPECIAIS, message = "{PTR-052}")
    @NotBlank(message = "{PTR-051}")
    @Size(max = 40, message = "{PTR-020}")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "permissoes", description = "Permissões referente ao Grupo BackOffice.", required = true)
    @JsonProperty(value = "permissoes")
    private Collection<RestPermissao> permissoes;

    @Override
    public GrupoBackOffice restParaModel() {
        return GrupoBackOfficeMapper.INSTANCE.convertToModel(this);
    }
}
