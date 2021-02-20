package com.gesoc.modelo.operaciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Ingreso extends Operación {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String descripcion;
    private Integer montoTotal;

    public String getTipoOperacion(){
        return "Ingreso";
    }

    public Ingreso() {
        super(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer calcularValor() {
        return this.getMontoTotal();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return "Ingreso{" +
                "descripcion='" + descripcion + '\'' +
                ", montoTotal=" + montoTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingreso ingreso = (Ingreso) o;
        return Objects.equals(descripcion, ingreso.descripcion) &&
                Objects.equals(montoTotal, ingreso.montoTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, montoTotal);
    }
}
