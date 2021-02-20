package com.gesoc.utils;

public class ValidaciónLongitud implements Validación {
    private final int longitudMínima;

    public ValidaciónLongitud(int longitudMínima) {
        if (longitudMínima <= 0) {
            throw new IllegalArgumentException("La longitud mínima debe ser mayor a 0");
        }
        this.longitudMínima = longitudMínima;
    }

    @Override
    public boolean esVálido(String password) {
        return password.length() >= longitudMínima;
    }

    public int longitudMínima() {
        return longitudMínima;
    }

    @Override
    public String descripción() {
        return "La contraseña debe tener una longitud mínima de " + longitudMínima + " caracteres";
    }
}
