package com.gesoc.modelo.organización.entidad.empresa.categorización;

import com.gesoc.modelo.organización.entidad.empresa.Empresa;
import com.gesoc.modelo.organización.entidad.empresa.Sector;
import com.google.common.collect.Table;

import java.util.Comparator;
import java.util.Map;

import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.GRANDE;

//TODO: refactorizar lógica repetida con criterio de ingresos totales (solo cambia el valor a comparar)
public class CantidadDePersonal implements CriterioCategorizaciónAFIP {
    private final Table<CategoríaAFIP, Sector, Integer> tabla;

    public CantidadDePersonal(Table<CategoríaAFIP, Sector, Integer> tablaCategorización) {
        this.tabla = tablaCategorización;
    }

    @Override
    public CategoríaAFIP categoría(Empresa empresa) {
        return tabla.column(empresa.sector()).entrySet().stream()
                .filter(entry -> empresa.cantidadDePersonal() <= entry.getValue())
                .min(Comparator.comparingInt(e -> e.getKey().ordinal()))
                .map(Map.Entry::getKey)
                .orElse(GRANDE);
    }
}
