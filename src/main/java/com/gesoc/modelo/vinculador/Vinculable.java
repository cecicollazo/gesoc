package com.gesoc.modelo.vinculador;

import java.time.LocalDate;

public interface Vinculable {

    Integer calcularValor();

    LocalDate obtenerFecha();

    Long getId();

    String getTipoOperacion();

    int getMontoTotal();

}
