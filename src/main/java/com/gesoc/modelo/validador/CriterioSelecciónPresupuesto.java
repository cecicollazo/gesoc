package com.gesoc.modelo.validador;

import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.operaciones.Egreso;

import java.time.LocalDateTime;
import java.util.Set;

public abstract class CriterioSelecciónPresupuesto extends CriterioValidación {
    protected abstract Presupuesto presupuestoCorrecto(Set<Presupuesto> presupuestos);

   //@Override
   //public ResultadoValidaciónSelecciónPresupuesto generarResultado(final Egreso egreso) {
   //    final Presupuesto presupuestoCorrecto = presupuestoCorrecto(egreso.getPresupuestos());
   //    final Presupuesto presupuestoElegido = egreso.getPresupuestoElegido();
   //    return new ResultadoValidaciónSelecciónPresupuesto(
   //            LocalDateTime.now(),
   //            presupuestoElegido.equals(presupuestoCorrecto),
   //            presupuestoElegido,
   //            presupuestoCorrecto
   //    );
   //}
}
