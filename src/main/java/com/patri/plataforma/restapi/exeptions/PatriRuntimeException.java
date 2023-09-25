package com.patri.plataforma.restapi.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatriRuntimeException extends RuntimeException
{
    private HttpStatus status;
    private String messageId;

    public PatriRuntimeException(HttpStatus status, String message)
    {
        super(message);
        this.status = status;
        this.messageId = message;
    }
}
