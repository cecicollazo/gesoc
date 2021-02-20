package com.gesoc.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ValidaciónListaNegra implements Validación {
    private final String rutaArchivo;

    public ValidaciónListaNegra(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public boolean esVálido(String password) {
        Stream<String> líneas;
        try {
            líneas = Files.lines(Paths.get(rutaArchivo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return líneas.noneMatch(password::equals);
    }

    @Override
    public String descripción() {
        return "La contraseña no debe encontrarse en la lista negra del archivo " + rutaArchivo;
    }
}
