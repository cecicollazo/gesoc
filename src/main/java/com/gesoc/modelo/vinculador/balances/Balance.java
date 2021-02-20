package com.gesoc.modelo.vinculador.balances;

import com.gesoc.modelo.vinculador.Vinculable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Balance<V1 extends Vinculable, V2 extends Vinculable> {

    private V1 vinculable;
    private Set<V2> vinculables = new HashSet<>();
    private LocalDate fechaVinculacion;

    protected Balance(V1 vinculable, LocalDate fechaVinculacion) {
        if (vinculable == null) {
            throw new RuntimeException("si o si se debe asignar un vinculable");
        }
        this.vinculable = vinculable;
        this.fechaVinculacion = fechaVinculacion;
    }

    public void agregarVinculable(V2 vinculable) {
        vinculables.add(vinculable);
    }

    public Integer faltantePorCubrir() {
        return this.vinculable.calcularValor() - this.montoCubiertoActual();
    }

    private Integer montoCubiertoActual() {
        return this.vinculables.stream().mapToInt(Vinculable::calcularValor).sum();
    }

    @Override
    public String toString() {
        return "Balance{" +
                "vinculable=" + vinculable +
                ", vinculables=" + vinculables +
                ", fechaVinculacion=" + fechaVinculacion +
                '}';
    }

    public V1 getVinculable() {
        return vinculable;
    }

    public void setVinculable(V1 vinculable) {
        this.vinculable = vinculable;
    }

    public Set<V2> getVinculables() {
        return vinculables;
    }

    public void setVinculables(Set<V2> vinculables) {
        this.vinculables = vinculables;
    }

    public LocalDate getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDate fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }
}
