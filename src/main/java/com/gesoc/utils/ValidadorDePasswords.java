package com.gesoc.utils;

import com.gesoc.excepciones.PasswordException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidadorDePasswords {

    private final Set<Validación> validaciones;

    public ValidadorDePasswords(Collection<Validación> validaciones) {
        this.validaciones = new HashSet<>(validaciones);
    }

    public boolean esVálido(String password) {
        return validacionesFallidas(password).isEmpty();
    }

    public void validar(String password) {
        if (!esVálido(password)) {
            throw new PasswordException(validacionesFallidas(password));
        }
    }

    public Set<Validación> validacionesFallidas(String password) {
        return validaciones.stream()
                .filter(validación -> !validación.esVálido(password))
                .collect(Collectors.toSet());
    }
}
