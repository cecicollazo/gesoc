package com.gesoc.modelo.categorización;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Categoría {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    @OneToOne
    private CriterioCategorización criterio;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<CriterioCategorización> subcriterios = new HashSet<>();

    Categoría(final String nombre, final CriterioCategorización criterio) {
        this.nombre = nombre;
        this.criterio = criterio;
    }

    public boolean perteneceA(final Categoría categoría) {
        return equals(categoría) || criterio.perteneceA(categoría);
    }

    public String nombreCompleto() {
        return criterio.nombreCategoríaPadre() + ", " + nombre;
    }

    public void agregarSubcriterio(final String nombreSubcriterio) {
        CriterioCategorización criterioNuevo = new CriterioCategorización(nombreSubcriterio, this);
        subcriterios.add(criterioNuevo);
    }

    public void quitarSubcriterio(final String nombreSubcriterio) {
        subcriterios.removeIf(criterio -> criterio.getNombre().equals(nombreSubcriterio));
    }

    public Set<CriterioCategorización> todosLosSubcriterios() {
        if (subcriterios.isEmpty()) {
            return new HashSet<>();
        }
        return subcriterios.stream()
                .flatMap(criterio -> criterio.todosLosSubcriterios().stream())
                .collect(Collectors.toSet());
    }

    public Set<Categoría> todasLasSubcategorías() {
        final Set<Categoría> subcategorías = new HashSet<>();
        subcategorías.add(this);
        if (subcriterios.isEmpty()) {
            return subcategorías;
        }
        subcategorías.addAll(subcriterios.stream()
                .flatMap(criterio -> criterio.todasLasSubcategorías().stream())
                .collect(Collectors.toSet())
        );
        return subcategorías;
    }

    public List<String> ruta() {
        final List<String> ruta = criterio.ruta();
        ruta.add(nombre);
        return ruta;
    }

    public Categoría() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public CriterioCategorización getCriterio() {
        return criterio;
    }

    public void setCriterio(final CriterioCategorización criterio) {
        this.criterio = criterio;
    }

    public Set<CriterioCategorización> getSubcriterios() {
        return subcriterios;
    }

    public void setSubcriterios(final Set<CriterioCategorización> subcriterios) {
        this.subcriterios = subcriterios;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categoría categoría = (Categoría) o;
        return nombre.equals(categoría.nombre) &&
                criterio.equals(categoría.criterio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, criterio);
    }
}
