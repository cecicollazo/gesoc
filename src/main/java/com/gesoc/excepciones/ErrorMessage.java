package com.gesoc.excepciones;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
