package com.gesoc.excepciones;

public class GeSocException extends RuntimeException {
    private static final long serialVersionUID = 7968753829437910796L;

    protected GeSocException(final String mensaje) {
        super(mensaje);
    }
}
