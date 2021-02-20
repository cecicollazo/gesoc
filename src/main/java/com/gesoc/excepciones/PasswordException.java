package com.gesoc.excepciones;

import com.gesoc.utils.Validación;

import java.util.Collection;
import java.util.stream.Collectors;

public class PasswordException extends RuntimeException {
    public PasswordException(Collection<Validación> validacionesFallidas) {
        super(validacionesFallidas.stream()
                .map(Validación::descripción)
                .collect(Collectors.joining(". "))
        );
    }
}
