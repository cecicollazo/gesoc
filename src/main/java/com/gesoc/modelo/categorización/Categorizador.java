package com.gesoc.modelo.categorización;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Entity
public class Categorizador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private final Set<CriterioCategorización> criterios = new HashSet<>();

    public Set<CriterioCategorización> getCriterios() {
        return new HashSet<>(criterios);
    }

    public Optional<CriterioCategorización> obtenerCriterio(final String nombre) {
        return criterios.stream()
                .flatMap(criterio -> criterio.todosLosSubcriterios().stream())
                .filter(criterio -> criterio.getNombre().equals(nombre))
                .findAny();
    }

    public Optional<Categoría> obtenerCategoría(final String nombre) {
        return obtener(categoría -> categoría.getNombre().equals(nombre));
    }

    public Optional<Categoría> obtenerCategoría(final Long id) {
        return obtener(categoría -> categoría.getId().equals(id));
    }

    private Optional<Categoría> obtener(final Predicate<? super Categoría> predicado) {
        return criterios.stream()
                .flatMap(criterio -> criterio.todasLasSubcategorías().stream())
                .filter(predicado)
                .findAny();
    }

    public void agregarCriterio(final String nombreCriterio) {
        this.criterios.add(new CriterioCategorización(nombreCriterio));
    }

    public void agregarCriterio(final String nombreCriterio, final String nombreCategoríaPadre) {
        // TODO: excepción específica
        obtenerCategoría(nombreCategoríaPadre).orElseThrow(RuntimeException::new).agregarSubcriterio(nombreCriterio);
    }

    public void agregarCategoría(final String nombreCategoría, final String nombreCriterio) {
        // TODO: excepción específica
        obtenerCriterio(nombreCriterio).orElseThrow(RuntimeException::new).agregarCategoría(nombreCategoría);
    }

    public void quitarCriterio(final String nombreCriterio) {
        this.criterios.removeIf(criterio -> criterio.getNombre().equals(nombreCriterio));
    }
}
