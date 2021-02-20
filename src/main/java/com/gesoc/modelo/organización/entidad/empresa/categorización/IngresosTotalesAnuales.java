package com.gesoc.modelo.organización.entidad.empresa.categorización;

import com.gesoc.modelo.organización.entidad.empresa.Empresa;
import com.gesoc.modelo.organización.entidad.empresa.Sector;
import com.google.common.collect.Table;

import java.util.Comparator;
import java.util.Map;

import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.GRANDE;

public class IngresosTotalesAnuales implements CriterioCategorizaciónAFIP {
    private final Table<CategoríaAFIP, Sector, Integer> tabla;

    public IngresosTotalesAnuales(Table<CategoríaAFIP, Sector, Integer> tablaCategorización) {
        this.tabla = tablaCategorización;
    }

    @Override
    public CategoríaAFIP categoría(Empresa empresa) {
        return tabla.column(empresa.sector()).entrySet().stream()
                .filter(entry -> empresa.ventasTotalesAnuales() <= entry.getValue())
                .min(Comparator.comparingInt(e -> e.getKey().ordinal()))
                .map(Map.Entry::getKey)
                .orElse(GRANDE);
    }
}
