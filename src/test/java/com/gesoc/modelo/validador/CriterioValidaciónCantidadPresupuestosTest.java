package com.gesoc.modelo.validador;

import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.operaciones.Egreso;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CriterioValidaciónCantidadPresupuestosTest {
    private static final CriterioValidaciónCantidadPresupuestos criterio = new CriterioValidaciónCantidadPresupuestos();

    private static final Presupuesto presupuesto1 =
            new Presupuesto(Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet());

    private static final Presupuesto presupuesto2 =
            new Presupuesto(Collections.emptySet(), Collections.emptySet(), null, Collections.emptySet());

    private static final Set<Presupuesto> dosPresupuestos = new HashSet<>(Arrays.asList(
            presupuesto1,
            presupuesto2
    ));

    private static final Egreso egresoConMásPresupuestosQueLosRequeridos =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    dosPresupuestos,
                    presupuesto1, null, null, null,
                    1,
                    Collections.emptySet()
            );

    private static final Egreso egresoConExactamenteLosPresupuestosRequeridos =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    dosPresupuestos,
                    presupuesto1, null, null, null,
                    2,
                    Collections.emptySet()
            );

    private static final Egreso egresoConMenosPresupuestosQueLosRequeridos =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    dosPresupuestos,
                    presupuesto1, null, null, null,
                    3,
                    Collections.emptySet()
            );

    private static final Egreso egresoSinPresupuestos =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    Collections.emptySet(),
                    presupuesto1, null, null, null,
                    0,
                    Collections.emptySet()
            );

    //@Test
    //public void resultadoValidaciónConEgresoConMásPresupuestosQueLosRequeridosDevuelveResultadoVálido() {
    //    final ResultadoValidación resultadoValidación =
    //            criterio.resultadoValidación(egresoConMásPresupuestosQueLosRequeridos);
//
    //    assertThat(resultadoValidación.válido(), is(true));
    //}
//
    //@Test
    //public void resultadoValidaciónConEgresoConExactamenteLosPresupuestosRequeridosDevuelveResultadoVálido() {
    //    final ResultadoValidación resultadoValidación =
    //            criterio.resultadoValidación(egresoConExactamenteLosPresupuestosRequeridos);
//
    //    assertThat(resultadoValidación.válido(), is(true));
    //}
//
    //@Test
    //public void resultadoValidaciónConEgresoConMenosPresupuestosQueLosRequeridosDevuelveResultadoInválido() {
    //    final ResultadoValidación resultadoValidación =
    //            criterio.resultadoValidación(egresoConMenosPresupuestosQueLosRequeridos);
//
    //    assertThat(resultadoValidación.válido(), is(false));
    //}
//
    //@Test(expected = SinPresupuestosException.class)
    //public void resultadoValidaciónConEgresoSinPresupuestosLanzaExcepción() {
    //    final ResultadoValidación resultadoValidación =
    //            criterio.resultadoValidación(egresoSinPresupuestos);
    //    fail();
    //}
}
