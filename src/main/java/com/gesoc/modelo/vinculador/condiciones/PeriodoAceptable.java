package com.gesoc.modelo.vinculador.condiciones;

import com.gesoc.modelo.vinculador.Vinculable;

import java.time.LocalDate;

public class PeriodoAceptable implements CondicionVinculacion{

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public PeriodoAceptable(LocalDate fechaDesde, LocalDate fechaHasta) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    @Override
    public Boolean cumpleCondicion(Vinculable vinculable) {
        return vinculable.obtenerFecha().isAfter(this.fechaDesde) && vinculable.obtenerFecha().isBefore(this.fechaHasta);
    }
}
