package com.gesoc.modelo.vinculador.balances;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;

import java.time.LocalDate;

public class BalanceEgreso extends Balance<Egreso, Ingreso> {
    public BalanceEgreso(Egreso egreso, LocalDate fechaVinculacion) {
        super(egreso, fechaVinculacion);
    }

    @Override
    public String toString() {
        return "BalanceEgreso{}";
    }
}
