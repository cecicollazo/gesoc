package com.gesoc.modelo.organización.entidad.empresa.categorización;

import com.gesoc.modelo.organización.entidad.empresa.Empresa;
import com.google.common.collect.ImmutableSet;

import java.util.Comparator;
import java.util.Set;

public class CategorizadorAFIP {
    private final ImmutableSet<CriterioCategorizaciónAFIP> criterios;

    public CategorizadorAFIP(Set<CriterioCategorizaciónAFIP> criterios) {
        if (criterios.isEmpty())
            throw new IllegalArgumentException("Un categorizador AFIP debe tener al menos un criterio");
        this.criterios = ImmutableSet.copyOf(criterios);
    }

    public CategoríaAFIP categorizar(Empresa empresa) {
        return criterios.stream()
                .map(criterio -> criterio.categoría(empresa))
                .max(Comparator.comparing(CategoríaAFIP::ordinal))
                .orElseThrow(RuntimeException::new);
    }
}
