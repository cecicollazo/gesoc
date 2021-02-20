package com.gesoc.excepciones;

public class SinPresupuestoElegidoException extends GeSocException {
    private static final long serialVersionUID = -5767148198670417812L;

    public SinPresupuestoElegidoException() {
        super("El egreso no tiene ningún presupuesto elegido");
    }
}
