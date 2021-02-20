package com.gesoc.dto.balance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BalanceDTO {

    private VinculableDTO vinculable;
    private List<VinculableDTO> vinculables = new ArrayList<>();
    private LocalDate fechaVinculacion;
    private int faltantePorVincular;

    public int getFaltantePorVincular() {
        return faltantePorVincular;
    }

    public void setFaltantePorVincular(int faltantePorVincular) {
        this.faltantePorVincular = faltantePorVincular;
    }

    public VinculableDTO getVinculable() {
        return vinculable;
    }

    public void setVinculable(VinculableDTO vinculable) {
        this.vinculable = vinculable;
    }

    public List<VinculableDTO> getVinculables() {
        return vinculables;
    }

    public void setVinculables(List<VinculableDTO> vinculables) {
        this.vinculables = vinculables;
    }

    public LocalDate getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDate fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }
}
