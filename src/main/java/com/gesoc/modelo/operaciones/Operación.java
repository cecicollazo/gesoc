package com.gesoc.modelo.operaciones;

import com.gesoc.modelo.categorización.Categorizable;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.vinculador.Vinculable;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class Operación implements Categorizable, Vinculable,Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long identificador;
    private Long organizaciónId;
    @ManyToMany
    private Set<Categoría> categorías = new HashSet<>();
    private LocalDate fecha;

    protected Operación(final LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public Set<Categoría> categorías() {
        return categorías;
    }

    @Override
    public LocalDate obtenerFecha() {
        return fecha;
    }

    public Long getId() {
        return identificador;
    }

    public void setId(Long id) {
        this.identificador = id;
    }

    public Long getOrganizaciónId() {
        return organizaciónId;
    }

    public void setOrganizaciónId(final Long organizaciónId) {
        this.organizaciónId = organizaciónId;
    }

    public Set<Categoría> getCategorías() {
        return categorías;
    }

    public void setCategorías(final Set<Categoría> categorías) {
        this.categorías = categorías;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDate fecha) {
        this.fecha = fecha;
    }

    protected Operación() {
    }
}
