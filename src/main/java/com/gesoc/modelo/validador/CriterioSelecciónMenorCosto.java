package com.gesoc.modelo.validador;

import com.gesoc.excepciones.SinPresupuestosException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;

import java.util.Comparator;
import java.util.Set;

public class CriterioSelecciónMenorCosto extends CriterioSelecciónPresupuesto {
    @Override
    protected Presupuesto presupuestoCorrecto(final Set<Presupuesto> presupuestos) {
        return presupuestos.stream()
                .min(Comparator.comparing(Presupuesto::getMontoTotal))
                .orElseThrow(SinPresupuestosException::new);
    }

    @Override
    protected ResultadoValidación generarResultado(Egreso egreso) {
        return null;
    }
}
