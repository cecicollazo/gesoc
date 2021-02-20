package com.gesoc.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidaciónRegexTest {

    @Test
    public void unPasswordQueMatcheaConLaRegexEsVálido() {
        String REGEX_ALFANUMERICA = "^.*(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+).*$";
        ValidaciónRegex validación = new ValidaciónRegex(REGEX_ALFANUMERICA);
        String password = "aaaa1111";

        assertTrue(validación.esVálido(password));
    }

    @Test
    public void unPasswordQueNoMatcheaConLaRegexEsInválido() {
        String REGEX_ALFANUMERICA = "^.*(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+).*$";
        ValidaciónRegex validación = new ValidaciónRegex(REGEX_ALFANUMERICA);
        String password = "12345678";

        assertFalse(validación.esVálido(password));
    }
}
