package com.gesoc.modelo.vinculador.balances;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;

import java.time.LocalDate;

public class BalanceIngreso extends Balance<Ingreso, Egreso> {
    public BalanceIngreso(Ingreso ingreso, LocalDate fechaVinculacion) {
        super(ingreso, fechaVinculacion);
    }

    @Override
    public String toString() {
        return "BalanceIngreso{}";
    }
}
