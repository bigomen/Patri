package com.patri.plataforma.restapi.exeptions;

public class BadRequest extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequest(String msg) {
        super(msg);
    }

    public BadRequest(String msg, Throwable cause) {
        super(msg, cause);
    }
}
