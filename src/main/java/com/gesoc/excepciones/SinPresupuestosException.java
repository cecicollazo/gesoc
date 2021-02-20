package com.gesoc.excepciones;

public class SinPresupuestosException extends GeSocException {
    private static final long serialVersionUID = -9150272844350058289L;

    public SinPresupuestosException() {
        super("No se encontraron presupuestos");
    }
}
