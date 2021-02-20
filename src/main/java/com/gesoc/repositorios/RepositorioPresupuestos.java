package com.gesoc.repositorios;

import com.gesoc.modelo.Presupuesto;

import java.util.List;

public class RepositorioPresupuestos extends Repositorio<Presupuesto> {

    public RepositorioPresupuestos() {
        super(Presupuesto.class);
    }

    @Override
    public List<Presupuesto> obtenerTodos() {
        return super.obtenerTodos();
    }
}
