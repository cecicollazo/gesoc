package com.gesoc.excepciones;

public class CategoríaInexistenteException extends GeSocException {
    private static final long serialVersionUID = -2637280505754595202L;

    public CategoríaInexistenteException(final String nombre) {
        super("No existe categoría con nombre " + nombre);
    }
}
