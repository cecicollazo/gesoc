package com.gesoc.utils;

import java.util.regex.Pattern;

public class ValidaciónRegex implements Validación {
    private final Pattern regex;

    public ValidaciónRegex(String regex) {
        this.regex = Pattern.compile(regex);
    }

    @Override
    public boolean esVálido(String password) {
        return regex.matcher(password).matches();
    }

    @Override
    public String descripción() {
        return "La contraseña debe matchear con la regex " + regex.toString();
    }
}
