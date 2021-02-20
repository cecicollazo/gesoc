package com.gesoc.repositorios;

import com.gesoc.modelo.Proveedor;

import java.util.List;

public class RepositorioProveedores extends Repositorio<Proveedor> {

    public RepositorioProveedores() {
        super(Proveedor.class);
    }

    @Override
    public List<Proveedor> obtenerTodos() {
        return super.obtenerTodos();
    }
}
