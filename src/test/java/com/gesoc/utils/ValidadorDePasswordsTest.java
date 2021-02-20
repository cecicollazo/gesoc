package com.gesoc.utils;

import com.gesoc.excepciones.PasswordException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.EMPTY_SET;
import static org.junit.Assert.*;

public class ValidadorDePasswordsTest {

    @Test
    public void unPasswordCualquieraPasaUnValidadorSinValidaciones() {
        ValidadorDePasswords validador = new ValidadorDePasswords(new HashSet<>());
        String password = "123";

        assertTrue(validador.esVálido(password));
    }

    @Test
    public void unPasswordQuePasaTodasLasValidacionesDelValidadorEsVálido() {
        Validación validaciónA = new ValidaciónLongitud(4);
        Validación validaciónB = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        ValidadorDePasswords validador = new ValidadorDePasswords(Arrays.asList(validaciónA, validaciónB));
        String password = "passSegura";

        assertTrue(validador.esVálido(password));
    }

    @Test
    public void unPasswordQueNoPasaAlgunaDeLasValidacionesDelValidadorEsInválido() {
        Validación validaciónA = new ValidaciónLongitud(4);
        Validación validaciónB = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        ValidadorDePasswords validador = new ValidadorDePasswords(Arrays.asList(validaciónA, validaciónB));
        String password = "qwerty";

        assertFalse(validador.esVálido(password));
    }

    @Test
    public void validarConPasswordVálidoPasa() {
        Validación validación = new ValidaciónLongitud(4);
        ValidadorDePasswords validador = new ValidadorDePasswords(Collections.singletonList(validación));
        String password = "1234";

        validador.validar(password);
    }

    @Test(expected = PasswordException.class)
    public void validarConPasswordInválidoLanzaExcepción() {
        Validación validación = new ValidaciónLongitud(4);
        ValidadorDePasswords validador = new ValidadorDePasswords(Collections.singletonList(validación));
        String password = "123";

        validador.validar(password);
    }

    @Test
    public void validacionesFallidasDevuelveSetVacíoSiNoFallaNingunaValidación() {
        Validación validaciónA = new ValidaciónLongitud(4);
        Validación validaciónB = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        ValidadorDePasswords validador = new ValidadorDePasswords(Arrays.asList(validaciónA, validaciónB));
        String password = "123456";

        assertEquals(EMPTY_SET, validador.validacionesFallidas(password));
    }

    @Test
    public void validacionesFallidasConPasswordQueFallaValidacionesDevuelveLasValidacionesFallidas() {
        Validación validaciónA = new ValidaciónLongitud(4);
        Validación validaciónB = new ValidaciónListaNegra("src/test/resources/lista-negra-test.txt");
        ValidadorDePasswords validador = new ValidadorDePasswords(Arrays.asList(validaciónA, validaciónB));
        String password = "qwerty";
        Set<Validación> validacionesFallidasEsperadas = new HashSet<>(Collections.singletonList(validaciónB));

        assertEquals(validacionesFallidasEsperadas, validador.validacionesFallidas(password));
    }
}
