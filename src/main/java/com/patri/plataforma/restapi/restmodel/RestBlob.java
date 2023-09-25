package com.patri.plataforma.restapi.restmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Schema(description = "Objeto provedor da classe Blob", name = "RestBlob")
@Data
@AllArgsConstructor
public class RestBlob {

    @Schema(name = "nome", description = "Nome da imagem")
    @JsonProperty(value = "nome")
    private String nome;

    @Schema(name = "url", description = "URL da imagem")
    @JsonProperty(value = "url")
    private String url;

    @Schema(name = "data_criacao", description = "Data de criação da imagem")
    @JsonProperty(value = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Schema(name = "ultima_modificacao", description = "Data da última modificação da imagem")
    @JsonProperty(value = "ultima_modificacao")
    private OffsetDateTime ultimaModificacao;

    @Schema(name = "tamanho", description = "Tamanho da imagem")
    @JsonProperty(value = "tamanho")
    private Long tamanho;
}
