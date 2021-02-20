package com.gesoc.modelo.categorización;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CriterioCategorizaciónTest {

    @Test
    public void laRutaDeUnCriterioDePrimerNivelEsSuNombre() {
        CriterioCategorización criterio = new CriterioCategorización("Ubicación");

        assertEquals(Collections.singletonList("Ubicación"), criterio.ruta());
    }

    @Test
    public void laRutaDeUnCriterioDeMásDePrimerNivelEsSuNombreYLaRutaDeLaCategoríaPadre() {
        CriterioCategorización criterioUbicación = new CriterioCategorización("Ubicación");
        Categoría categoríaBuenosAires = new Categoría("Buenos Aires", criterioUbicación);
        CriterioCategorización criterioMunicipio = new CriterioCategorización("Municipio", categoríaBuenosAires);

        assertEquals(Arrays.asList("Ubicación", "Buenos Aires", "Municipio"), criterioMunicipio.ruta());
    }
}
