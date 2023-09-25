package com.patri.plataforma.restapi.controller.v1.doc;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.restmodel.RestBlob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BlobControllerDoc
{
    @Operation
    (
        method = "POST",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Upload de imagem",
        description = "Realiza o upload de uma imagem no container Azure."
    )
    public ResponseEntity uploadFile
    (
        @Parameter
            (
                name = "path",
                description = "Parâmetro necessário para criação de pastas em que a imagem será armazenada.",
                example = "esportes/",
                required = true
            )
        @RequestParam(value = "path") String path,
        @Parameter
            (
                name = "file",
                description = "Parâmetro necessário para upload da imagem.",
                example = "image.jpg"
            )
        @RequestBody(required = true, content = @Content(mediaType = "application/octet-stream")) MultipartFile file
    ) throws IOException;

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Download de imagem",
        description = "Realiza o download de uma imagem do container Azure."
    )
    public void getImageWithMediaType(
        @Parameter
            (
                name = "path",
                description = "Parâmetro necessário para download de uma imagem em sua respectiva pasta.",
                example = "esportes/imagem.jpg",
                required = true
            )
        @RequestParam(value = "path") String path, HttpServletResponse response
    ) throws IOException;

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Listar todas imagens",
        description = "Retorna uma lista de todas as imagens com as informações de nome, url, data de criação, ultima modificação e tamanho."
    )
    public List<RestBlob> listar();

    @Operation
    (
        method = "GET",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Filtrar imagens",
        description = "Retorna uma lista de imagens com as informações de nome, url, data de criação, ultima modificação e tamanho que contenham o nome ou parte do nome informado."
    )
    public List<RestBlob> findFile
    (
        @Parameter
        (
            name = "nome",
            description = "Parâmetro necessário para consulta de imagens.",
            example = "imagem"
        )
        @RequestParam(value = "nome") String filename
    );

    @Operation
    (
        method = "PATCH",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Update de imagem",
        description = "Realiza a atualização do nome de uma imagem no container Azure."
    )
    public ResponseEntity updateFile
    (
        @Parameter
        (
            name = "oldPath",
            description = "Parâmetro necessário para busca da imagem que será renomeada.",
            example = "esportes/oldImage.jpg",
            required = true
        )
        @RequestParam(value = "oldPath") String oldPath,
        @Parameter
        (
            name = "newPath",
            description = "Parâmetro necessário para atualização do novo nome da imagem.",
            example = "esportes/newImage.jpg",
            required = true
        )
        @RequestParam(value = "newPath") String newPath
    );

    @Operation
    (
        method = "DELETE",
        tags = SwaggerConstantes.BLOB_TAG,
        summary = "Remoção de imagem",
        description = "Remove do container Azure uma imagem conforme caminho e nome informado como parâmetro."
    )
    public void delete
    (
        @Parameter
            (
                name = "path",
                description = "Parâmetro necessário para remoção de uma imagem em sua respectiva pasta.",
                example = "esportes/imagem.jpg",
                required = true
            )
        @RequestParam(value = "path") String path
    );
}
