package com.gesoc.modelo.vinculador.criterios;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.vinculador.balances.Balance;
import com.gesoc.modelo.vinculador.Vinculable;
import com.gesoc.modelo.vinculador.balances.BalanceEgreso;
import com.gesoc.modelo.vinculador.balances.BalanceIngreso;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CriterioEjecucion {

    List<Long> idsVinculados = new ArrayList<>();

    public abstract Set<? extends Balance<?, ?>> vincular(Set<Ingreso> ingresos, Set<Egreso> egresos);

    public <V extends Vinculable> List<V> ordenarPorValor(Set<V> vinculables) {
        return ordenar(vinculables, Comparator.comparing(Vinculable::calcularValor));
    }

    public <V extends Vinculable> List<V> ordenarPorFechas(Set<V> vinculables) {
        return ordenar(vinculables, Comparator.comparing(Vinculable::obtenerFecha));
    }


    public Set<BalanceIngreso> asignarEgresosAIngresos(List<Ingreso> ingresos, List<Egreso> egresos){

        Set<BalanceIngreso> balances = new HashSet<>();
        ingresos.forEach(ingreso -> balances.add(asignarEgresos(ingreso, egresos)));
        return balances;
    }

    public Set<BalanceEgreso> asignarIngresosAEgresos(List<Ingreso> ingresos, List<Egreso> egresos){
        Set<BalanceEgreso> balances = new HashSet<>();
        egresos.forEach(egreso -> balances.add(asignarIngresos(egreso, ingresos)));
        return balances;
    }


    private BalanceIngreso asignarEgresos(Ingreso ingreso, List<Egreso> egresos){

        BalanceIngreso balance = new BalanceIngreso(ingreso, LocalDate.now());
        egresos.forEach(egreso -> {
            if(balance.faltantePorCubrir() >= egreso.calcularValor() && !idsVinculados.contains(egreso.getId())){
                idsVinculados.add(egreso.getId());
                balance.agregarVinculable(egreso);
            }
        });
        return balance;
    }

    private BalanceEgreso asignarIngresos(Egreso egreso, List<Ingreso> ingresos){
        BalanceEgreso balance = new BalanceEgreso(egreso, LocalDate.now());
        ingresos.forEach(ingreso -> {
            if(balance.faltantePorCubrir() >= ingreso.calcularValor() && !idsVinculados.contains(ingreso.getId())){
                idsVinculados.add(ingreso.getId());
                balance.agregarVinculable(ingreso);
            }
        });
        return balance;
    }

    private <V extends Vinculable> List<V> ordenar(Set<V> vinculables, Comparator<Vinculable> comparador) {
        return vinculables.stream()
                .sorted(comparador.reversed())
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
