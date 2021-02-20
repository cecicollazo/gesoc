package com.gesoc.modelo.organización.entidad.empresa.categorización;

import com.gesoc.modelo.organización.entidad.empresa.Empresa;
import com.gesoc.modelo.organización.entidad.empresa.Sector;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.junit.Test;

import static com.gesoc.modelo.organización.entidad.empresa.Sector.CONSTRUCCIÓN;
import static com.gesoc.modelo.organización.entidad.empresa.Sector.SERVICIOS;
import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.MEDIANAT1;
import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.PEQUEÑA;
import static org.junit.Assert.assertEquals;

public class CriterioCategorizaciónAFIPTest {
    private static final Table<CategoríaAFIP, Sector, Integer> tablaIngresos =
            ImmutableTable.<CategoríaAFIP, Sector, Integer>builder()
                    .put(PEQUEÑA, CONSTRUCCIÓN, 2000)
                    .put(PEQUEÑA, SERVICIOS, 1000)
                    .put(MEDIANAT1, CONSTRUCCIÓN, 4000)
                    .put(MEDIANAT1, SERVICIOS, 2000)
                    .build();

    private static final Table<CategoríaAFIP, Sector, Integer> tablaPersonal =
            ImmutableTable.<CategoríaAFIP, Sector, Integer>builder()
                    .put(PEQUEÑA, CONSTRUCCIÓN, 200)
                    .put(PEQUEÑA, SERVICIOS, 100)
                    .put(MEDIANAT1, CONSTRUCCIÓN, 400)
                    .put(MEDIANAT1, SERVICIOS, 200)
                    .build();

    @Test
    public void ingresosTotalesAnualesDevuelveCategoríaSegúnTabla() {
        IngresosTotalesAnuales criterioIngresosAnuales = new IngresosTotalesAnuales(tablaIngresos);
        Empresa empresa = new Empresa(SERVICIOS, 150, 1500, null);
        assertEquals(MEDIANAT1, criterioIngresosAnuales.categoría(empresa));
    }

    @Test
    public void cantidadDePersonalDevuelveCategoríaSegúnTabla() {
        CantidadDePersonal criterioCantidadDePersonal = new CantidadDePersonal(tablaPersonal);
        Empresa empresa = new Empresa(CONSTRUCCIÓN, 400, 4000, null);
        assertEquals(MEDIANAT1, criterioCantidadDePersonal.categoría(empresa));
    }
}
