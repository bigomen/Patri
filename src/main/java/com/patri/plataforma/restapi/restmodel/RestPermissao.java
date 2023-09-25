package com.patri.plataforma.restapi.restmodel;

import com.patri.plataforma.restapi.model.enums.TipoPermissao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema(description = "Objeto provedor da classe Permissão", name = "RestPermissao")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class RestPermissao extends BaseRestModel implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Schema(name = "descricao", description = "Texto descritivo da permissão", required = true)
    @Size(max = 64, message = "{PTR-020")
    @NotBlank(message = "{PTR-023}")
    private String descricao;

    @Schema(name = "tipo", description = "Tipo da permissão", required = true)
    @NotNull(message = "{PTR-037}")
    private TipoPermissao tipo;

    @Schema(name = "rota", description = "Rota referente a permissão")
    @Size(max = 50, message = "{PTR-020}")
    @NotBlank(message = "{PTR-045}")
    private String rota;
}
