package com.gesoc.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidaciónListaNegraTest {

    @Test
    public void unPasswordQueNoEstáEnLaListaNegraEsVálido() {
        ValidaciónListaNegra validación = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        String password = "password";

        assertTrue(validación.esVálido(password));
    }

    @Test
    public void unPasswordQueEstáEnLaListaNegraEsInválido() {
        ValidaciónListaNegra validación = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        String password = "zxcvbn";

        assertFalse(validación.esVálido(password));
    }
}
