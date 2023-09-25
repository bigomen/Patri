package com.patri.plataforma.restapi.utility;

@Deprecated
public class SuccessMessage implements IBaseMessage {

    private String message;

    public SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return  "OK";
    }

    public boolean getError() {
        return false;
    }


}
