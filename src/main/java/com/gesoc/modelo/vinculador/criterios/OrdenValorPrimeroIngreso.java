package com.gesoc.modelo.vinculador.criterios;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.BalanceEgreso;

import java.util.List;
import java.util.Set;

public class OrdenValorPrimeroIngreso extends CriterioEjecucion{

    @Override
    public Set<BalanceEgreso> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos) {
        List<Ingreso> ingresosOrdenados = this.ordenarPorValor(ingresos);
        List<Egreso> egresosOrdenados = this.ordenarPorValor(egresos);
        return this.asignarIngresosAEgresos(ingresosOrdenados, egresosOrdenados);
    }
}
