package com.gesoc.modelo.vinculador.criterios;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.BalanceIngreso;

import java.util.List;
import java.util.Set;

public class OrdenValorPrimeroEgreso extends CriterioEjecucion {

    @Override
    public Set<BalanceIngreso> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos) {
        List<Ingreso> ingresosOrdenados = this.ordenarPorValor(ingresos);
        List<Egreso> egresosOrdenados = this.ordenarPorValor(egresos);
        return this.asignarEgresosAIngresos(ingresosOrdenados, egresosOrdenados);
    }
}
