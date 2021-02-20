package com.gesoc.modelo.categorización;

import java.util.Set;

public interface Categorizable {
    Set<Categoría> categorías();

    default void agregarCategoría(Categoría categoría) {
        categorías().add(categoría);
    }

    default void quitarCategoría(Categoría categoría) {
        categorías().remove(categoría);
    }

    default boolean perteneceA(Categoría categoría) {
        return categorías().stream().anyMatch(c -> c.perteneceA(categoría));
    }
}
