package com.gesoc.modelo.validador;

import com.gesoc.excepciones.SinPresupuestosException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.item.Item;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CriterioSelecciónMenorCostoTest {
    private static final Collection<Item> ítemsMontoTotal100 = new HashSet<>(Collections.singletonList(
            new Item(null, 100)
    ));
    private static final Collection<Item> ítemsMontoTotal200 = new HashSet<>(Collections.singletonList(
            new Item(null, 200)
    ));
    private static final Presupuesto presupuestoCosto100 =
            new Presupuesto(Collections.emptySet(), ítemsMontoTotal100, null, Collections.emptySet());

    private static final Presupuesto presupuestoCosto200 =
            new Presupuesto(Collections.emptySet(), ítemsMontoTotal200, null, Collections.emptySet());

    private static final CriterioSelecciónMenorCosto criterioMenorCosto = new CriterioSelecciónMenorCosto();

    @Test
    public void presupuestoCorrectoConMásDeUnPresupuestoSeleccionaElDeMenorCosto() {
        final Set<Presupuesto> presupuestos = new HashSet<>(Arrays.asList(
                presupuestoCosto100,
                presupuestoCosto200
        ));

        assertThat(criterioMenorCosto.presupuestoCorrecto(presupuestos), is(presupuestoCosto100));
    }

    @Test
    public void presupuestoCorrectoConSoloUnPresupuestoSeleccionaEse() {
        final Set<Presupuesto> presupuestos = new HashSet<>(Collections.singletonList(presupuestoCosto200));

        assertThat(criterioMenorCosto.presupuestoCorrecto(presupuestos), is(presupuestoCosto200));
    }

    @Test(expected = SinPresupuestosException.class)
    public void presupuestoCorrectoSinPresupuestosLanzaExcepción() {
        final Set<Presupuesto> presupuestos = new HashSet<>();
        criterioMenorCosto.presupuestoCorrecto(presupuestos);
        fail();
    }
}
