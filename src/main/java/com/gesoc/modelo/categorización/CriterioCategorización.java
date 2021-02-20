package com.gesoc.modelo.categorización;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class CriterioCategorización {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    @OneToOne
    private Categoría categoríaPadre;
    @OneToMany(cascade = CascadeType.ALL)
    private final Set<Categoría> categorías = new HashSet<>();

    CriterioCategorización(final String nombre, final Categoría categoríaPadre) {
        this.nombre = nombre;
        this.categoríaPadre = categoríaPadre;
    }

    CriterioCategorización(final String nombre) {
        this(nombre, null);
    }

    public boolean perteneceA(final Categoría categoría) {
        return categoríaPadre().filter(categoríaPadre -> categoríaPadre.perteneceA(categoría)).isPresent();
    }

    public Optional<Categoría> categoríaPadre() {
        return Optional.ofNullable(categoríaPadre);
    }

    public String nombreCategoríaPadre() {
        return categoríaPadre().map(Categoría::nombreCompleto).orElse("");
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Categoría> getCategorías() {
        return new HashSet<>(categorías);
    }

    public void agregarCategoría(final String nombreCategoria) {
        Categoría categoríaNueva = new Categoría(nombreCategoria, this);
        this.categorías.add(categoríaNueva);
    }

    public void quitarCategoria(final String nombreCategoria) {
        this.categorías.removeIf(categoría -> categoría.getNombre().equals(nombreCategoria));
    }

    public List<String> ruta() {
        if (categoríaPadre == null) {
            return new ArrayList<>(Collections.singletonList(nombre));
        }
        List<String> ruta = categoríaPadre.ruta();
        ruta.add(nombre);
        return ruta;
    }

    public Set<CriterioCategorización> todosLosSubcriterios() {
        final Set<CriterioCategorización> subcriterios = categorías.stream()
                .flatMap(categoría -> categoría.todosLosSubcriterios().stream())
                .collect(Collectors.toSet());
        subcriterios.add(this);
        return subcriterios;
    }

    public Set<Categoría> todasLasSubcategorías() {
        return categorías.stream()
                .flatMap(categoría -> categoría.todasLasSubcategorías().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CriterioCategorización that = (CriterioCategorización) o;
        return nombre.equals(that.nombre)
                && Objects.equals(categoríaPadre, that.categoríaPadre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, categoríaPadre);
    }

    public CriterioCategorización() {
    }
}
