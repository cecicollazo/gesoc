package com.gesoc.modelo.vinculador.criterios;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.Balance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mix extends CriterioEjecucion {

    private List<CriterioEjecucion> criterioEjecucion;

    public Mix(List<CriterioEjecucion> criterioEjecucion) {
        this.criterioEjecucion = criterioEjecucion;
    }

    @Override
    public Set<Balance<?, ?>> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos) {
        Set<Balance<?, ?>> balances = new HashSet<>();
        this.criterioEjecucion.forEach(criterio -> balances.addAll(criterio.vincular(ingresos, egresos)));
        return balances;
    }
}
