package com.gesoc.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidaciónLongitudTest {

    @Test
    public void unaValidaciónDeLongitudPuedeTenerLongitudPositiva() {
        ValidaciónLongitud validación = new ValidaciónLongitud(10);

        assertEquals(10, validación.longitudMínima());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unaValidaciónDeLongitudNoPuedeTenerLongitudCero() {
        new ValidaciónLongitud(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unaValidaciónDeLongitudNoPuedeTenerLongitudNegativa() {
        new ValidaciónLongitud(-1);
    }

    @Test
    public void unPasswordConLongitudIgualALaMínimaEsVálido() {
        String password = "12345678";
        ValidaciónLongitud validación = new ValidaciónLongitud(8);

        assertTrue(validación.esVálido(password));
    }

    @Test
    public void unPasswordConLongitudMayorALaMínimaEsVálido() {
        String password = "123456789";
        ValidaciónLongitud validación = new ValidaciónLongitud(8);

        assertTrue(validación.esVálido(password));
    }

    @Test
    public void unPasswordConLongitudMenorALaMínimaEsInválido() {
        String password = "1234567";
        ValidaciónLongitud validación = new ValidaciónLongitud(8);

        assertFalse(validación.esVálido(password));
    }
}
