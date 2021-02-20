package com.gesoc.modelo.validador;

import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;
import com.gesoc.modelo.resultado.ResultadoValidaciónCantidadPresupuestos;

import java.time.LocalDateTime;

public class CriterioValidaciónCantidadPresupuestos extends CriterioValidación {
    @Override
    protected ResultadoValidación generarResultado(Egreso egreso) {
        return null;
    }
    //@Override
    //public ResultadoValidaciónCantidadPresupuestos generarResultado(final Egreso egreso) {
    //    final int cantidadPresupuestosReal = egreso.getPresupuestos().size();
    //    final int cantidadPresupuestosRequeridos = egreso.getCantidadPresupuestosRequeridos();
//
    //    return new ResultadoValidaciónCantidadPresupuestos(
    //            LocalDateTime.now(),
    //            cantidadPresupuestosReal >= cantidadPresupuestosRequeridos,
    //            cantidadPresupuestosRequeridos,
    //            cantidadPresupuestosReal
    //    );
    //}
}
