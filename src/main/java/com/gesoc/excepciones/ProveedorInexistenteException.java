package com.gesoc.excepciones;

public class ProveedorInexistenteException extends GeSocException {
    private static final long serialVersionUID = 256593461064016765L;

    public ProveedorInexistenteException(final Long idProveedor) {
        super("No existe el proveedor con el id " + idProveedor);
    }
}
