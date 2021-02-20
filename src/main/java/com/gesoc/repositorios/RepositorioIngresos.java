package com.gesoc.repositorios;

import com.gesoc.modelo.operaciones.Ingreso;

import java.util.List;

public class RepositorioIngresos extends RepositorioOperaciones<Ingreso> {

    public RepositorioIngresos() {
        super(Ingreso.class);
    }

    @Override
    public List<Ingreso> obtenerTodos() {
        return super.obtenerTodos();
    }
}
