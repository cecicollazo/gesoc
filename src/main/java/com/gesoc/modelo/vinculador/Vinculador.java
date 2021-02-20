package com.gesoc.modelo.vinculador;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.Balance;
import com.gesoc.modelo.vinculador.condiciones.CondicionVinculacion;
import com.gesoc.modelo.vinculador.criterios.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Vinculador {

//    private Set<CondicionVinculacion> condiciones = new HashSet<>();
    private CriterioEjecucion criterioEjecucion;

    public Vinculador(Criterio criterio) {
        switch (criterio) {
            case EGRESO:
                criterioEjecucion = new OrdenValorPrimeroEgreso();
                break;
            case INGRESO:
                criterioEjecucion = new OrdenValorPrimeroIngreso();
                break;
            case FECHA:
                criterioEjecucion = new OrdenFecha();
                break;
            case MIX:
                criterioEjecucion = new Mix(Arrays.asList(new OrdenValorPrimeroEgreso()));
                break;
            default:
                throw new RuntimeException("Debe tener un criterio de ejecucion no nulo");
        }
    }

    public Set<? extends Balance<?, ?>> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos, Set<CondicionVinculacion> condiciones){
        ingresos = cumpleCondicionesIngresos(ingresos, condiciones);
        egresos = cumpleCondicionesEgresos(egresos, condiciones);
        return this.criterioEjecucion.vincular(ingresos, egresos);
    }

    private Set<Egreso> cumpleCondicionesEgresos(Set<Egreso> vinculables, Set<CondicionVinculacion> condiciones) {
        return vinculables.stream()
                .filter(egreso -> condiciones.stream()
                        .allMatch(condicionVinculacion -> condicionVinculacion.cumpleCondicion(egreso))).collect(Collectors.toSet());
    }

    private Set<Ingreso> cumpleCondicionesIngresos(Set<Ingreso> vinculables, Set<CondicionVinculacion> condiciones) {
        return vinculables.stream()
                .filter(vinculable -> condiciones.stream()
                        .allMatch(condicionVinculacion -> condicionVinculacion.cumpleCondicion(vinculable))).collect(Collectors.toSet());
    }

}
