package com.gesoc.modelo.categorización;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class CategoríaTest {

    private static final Categorizador CATEGORIZADOR = new Categorizador();

    @Test
    public void laRutaDeUnaCategoríaDePrimerNivelEsSuNombreYElDeSuCriterio() {
        CATEGORIZADOR.agregarCriterio("Ubicación");
        CATEGORIZADOR.agregarCategoría("Buenos Aires", "Ubicación");

        Categoría categoría = CATEGORIZADOR.obtenerCategoría("Buenos Aires").get();

        assertEquals(Arrays.asList("Ubicación", "Buenos Aires"), categoría.ruta());
    }

    @Test
    public void laRutaDeUnaCategoríaDeMásDePrimerNivelEsSuNombreElDeSuCriterioYLaRutaDeLaCategoríaPadre() {
        CATEGORIZADOR.agregarCriterio("Ubicación");
        CATEGORIZADOR.agregarCategoría("Buenos Aires", "Ubicación");
        CATEGORIZADOR.agregarCriterio("Municipio", "Buenos Aires");
        CATEGORIZADOR.agregarCategoría("La Matanza", "Municipio");

        Categoría categoría = CATEGORIZADOR.obtenerCategoría("La Matanza").get();

        assertEquals(Arrays.asList("Ubicación", "Buenos Aires", "Municipio", "La Matanza"), categoría.ruta());
    }

}
