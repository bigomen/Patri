package com.patri.plataforma.restapi.controller.v1;

import com.patri.plataforma.restapi.constants.SwaggerConstantes;
import com.patri.plataforma.restapi.controller.v1.doc.BlobControllerDoc;
import com.patri.plataforma.restapi.restmodel.RestBlob;
import com.patri.plataforma.restapi.service.BlobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/blob/v1")
@Tag(name = SwaggerConstantes.BLOB_TAG)
public class BlobController implements BlobControllerDoc {

    private final BlobService blobService;

    @Autowired
    public BlobController(BlobService blobService) {
        this.blobService = blobService;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam(value = "path") String path, @RequestBody MultipartFile file) throws IOException {
        int httpStatus = blobService.storeFile(path + file.getOriginalFilename(), file.getInputStream());
        return ResponseEntity.status(httpStatus).build();
    }

    @GetMapping(value = "/download")
    public void getImageWithMediaType(@RequestParam(value = "path") String path, HttpServletResponse response) throws IOException {
        String mimeType = "application/octet-stream";
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + path + "\""));
        InputStream inputStream = blobService.downloadFile(path);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/lista")
    public List<RestBlob> listar() {
        return blobService.listFiles();
    }

    @GetMapping("/find")
    public List<RestBlob> findFile(@RequestParam(value = "nome") String fileName) {
        return blobService.findFile(fileName);
    }

    @PatchMapping()
    public ResponseEntity updateFile(@RequestParam(value = "oldPath") String oldPath, @RequestParam(value = "newPath") String newPath) {
        int httpStatus = blobService.update(oldPath, newPath);
        return ResponseEntity.status(httpStatus).build();
    }

    @DeleteMapping()
    public void delete(@RequestParam(value = "path") String path) {
        blobService.deleteFile(path);
    }
}

