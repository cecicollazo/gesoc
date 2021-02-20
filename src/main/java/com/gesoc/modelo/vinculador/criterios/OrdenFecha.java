package com.gesoc.modelo.vinculador.criterios;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.BalanceIngreso;

import java.util.List;
import java.util.Set;

public class OrdenFecha extends CriterioEjecucion{

    @Override
    public Set<BalanceIngreso> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos) {
        List<Ingreso> ingresosOrdenados = this.ordenarPorFechas(ingresos);
        List<Egreso> egresosOrdenados = this.ordenarPorFechas(egresos);
        return this.asignarEgresosAIngresos(ingresosOrdenados, egresosOrdenados);
    }
}
