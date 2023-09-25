package com.patri.plataforma.restapi.service;

import com.azure.core.http.rest.Response;
import com.azure.core.util.BinaryData;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.azure.storage.blob.specialized.BlobInputStream;
import com.patri.plataforma.restapi.restmodel.RestBlob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlobService {

    @Value(value = "${azure.myblob.connection.string}")
    private String CONNECTION_STRING;
    @Value(value = "${azure.myblob.container}")
    private String CONTAINER_NAME;
    @Value(value = "${api.context}")
    private String CONTEXT;

    private final String downloadURL = "/blob/v1/download?path=";

    public int storeFile(String fileName, InputStream content) {
        BlobClient client = containerClient().getBlobClient(fileName);
        if (client.exists()) {
            return HttpStatus.CONFLICT.value();
        }
        Response<BlockBlobItem> response = client.uploadWithResponse(new BlobParallelUploadOptions(content), null, Context.NONE);
        return response.getStatusCode();
    }

    public InputStream downloadFile(String fileName) {
        BlobClient client = containerClient().getBlobClient(fileName);
        BinaryData binaryData = client.downloadContent();
        return new ByteArrayInputStream(binaryData.toBytes());
    }

    public List<RestBlob> listFiles() {
        List<BlobItem> blobItemList = containerClient().listBlobs().stream().collect(Collectors.toList());
        return blobItemList.stream()
                .map(this::toRestBlob)
                .collect(Collectors.toList());
    }

    public List<RestBlob> findFile(String fileName) {
        List<RestBlob> restBlobList = listFiles().stream()
                .filter(restBlob -> restBlob.getNome().matches(".*" + fileName + ".*"))
                .collect(Collectors.toList());
        return restBlobList;
    }

    public int update(String oldFileName, String newFileName) {
        BlobClient oldBlob = containerClient().getBlobClient(oldFileName);
        int response = storeFile(newFileName, oldBlob.openInputStream());
        oldBlob.delete();
        return response;
    }

    public void deleteFile(String fileName) {
        BlobClient client = containerClient().getBlobClient(fileName);
        client.delete();
    }

    private RestBlob toRestBlob(BlobItem blobItem) {
        return new RestBlob(
                blobItem.getName(),
                CONTEXT + downloadURL + blobItem.getName(),
                blobItem.getProperties().getCreationTime(),
                blobItem.getProperties().getLastModified(),
                blobItem.getProperties().getContentLength()
        );
    }

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING).buildClient();
        return serviceClient.getBlobContainerClient(CONTAINER_NAME);
    }
}
