package com.gesoc.modelo.validador;

import com.gesoc.excepciones.SinPresupuestosException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;

public class CriterioSelecciónPresupuestoTest {
    // TODO: uso esta subclase concreta para testear, pero estaría bueno que sea un mock
    private static final CriterioSelecciónPresupuesto criterio = new CriterioSelecciónMenorCosto();

    private static final Collection<Item> ítemsMontoTotal100 = new HashSet<>(Collections.singletonList(
            new Item(null, 100)
    ));
    private static final Collection<Item> ítemsMontoTotal200 = new HashSet<>(Collections.singletonList(
            new Item(null, 200)
    ));
    private static final Presupuesto presupuestoCorrecto =
            new Presupuesto(Collections.emptySet(), ítemsMontoTotal100, null, Collections.emptySet());

    private static final Presupuesto presupuestoIncorrecto =
            new Presupuesto(Collections.emptySet(), ítemsMontoTotal200, null, Collections.emptySet());

    private static final Set<Presupuesto> presupuestos = new HashSet<>(Arrays.asList(
            presupuestoCorrecto,
            presupuestoIncorrecto
    ));

    private static final Egreso egresoConPresupuestoCorrecto =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    presupuestos, presupuestoCorrecto,
                    null, null, null, 0, Collections.emptySet());

    private static final Egreso egresoConPresupuestoIncorrecto =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    presupuestos, presupuestoIncorrecto,
                    null, null, null, 0, Collections.emptySet());

    private static final Egreso egresoSinPresupuestos =
            new Egreso(null, null, null, Collections.emptySet(), true,
                    Collections.emptySet(), null,
                    null, null, null, 0, Collections.emptySet());

    //@Test
    //public void resultadoValidaciónConEgresoConPresupuestoCorrectoDevuelveResultadoVálido() {
    //    final ResultadoValidación resultadoValidaciónObtenido =
    //            criterio.resultadoValidación(egresoConPresupuestoCorrecto);
//
    //    assertThat(resultadoValidaciónObtenido.válido(), is(true));
    //}
//
    //@Test
    //public void resultadoValidaciónConEgresoConPresupuestoIncorrectoDevuelveResultadoVálido() {
    //    final ResultadoValidación resultadoValidaciónObtenido =
    //            criterio.resultadoValidación(egresoConPresupuestoIncorrecto);
//
    //    assertThat(resultadoValidaciónObtenido.válido(), is(false));
    //}

    @Test(expected = SinPresupuestosException.class)
    public void resultadoValidaciónConEgresoSinPresupuestosLanzaExcepción() {
        final ResultadoValidación resultadoValidaciónObtenido =
                criterio.resultadoValidación(egresoSinPresupuestos);
        fail();
    }
}
