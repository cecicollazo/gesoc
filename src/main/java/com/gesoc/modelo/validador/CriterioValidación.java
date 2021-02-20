package com.gesoc.modelo.validador;

import com.gesoc.excepciones.SinPresupuestoElegidoException;
import com.gesoc.excepciones.SinPresupuestosException;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;

import java.util.Objects;

public abstract class CriterioValidación {
    private Long id;

    public final ResultadoValidación resultadoValidación(final Egreso egreso) {
        if (Objects.isNull(egreso.getPresupuestos()) || egreso.getPresupuestos().isEmpty()) {
            throw new SinPresupuestosException();
        }
        if (Objects.isNull(egreso.getPresupuestoElegido())) {
            throw new SinPresupuestoElegidoException();
        }
        return generarResultado(egreso);
    }

    protected abstract ResultadoValidación generarResultado(Egreso egreso);
}
