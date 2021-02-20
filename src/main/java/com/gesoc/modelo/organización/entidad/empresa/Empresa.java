package com.gesoc.modelo.organización.entidad.empresa;

import com.gesoc.modelo.organización.entidad.empresa.categorización.CategorizadorAFIP;
import com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import static com.gesoc.modelo.organización.entidad.empresa.Sector.*;
import static com.gesoc.modelo.organización.entidad.empresa.categorización.CategoríaAFIP.*;

public class Empresa {

    private Sector sector;
    private Integer cantidadDePersonal;
    private Integer ventasTotalesAnuales;
    private CategorizadorAFIP categorizador;
    private CategoríaAFIP categoría;

    public Empresa(Sector sector, Integer cantidadDePersonal, Integer ventasTotalesAnuales, CategorizadorAFIP categorizador) {
        this.sector = sector;
        this.cantidadDePersonal = cantidadDePersonal;
        this.ventasTotalesAnuales = ventasTotalesAnuales;
        this.categorizador = categorizador;
    }

    public void categorizar() {
        this.categoría = categorizador.categorizar(this);
    }

    private static final Table<CategoríaAFIP, Sector, Integer> tablaDeCategoríaPorVentasAnuales =
            ImmutableTable.<CategoríaAFIP, Sector, Integer>builder()
                    .put(MICRO, CONSTRUCCIÓN, 15230000)
                    .put(MICRO, SERVICIOS, 8500000)
                    .put(MICRO, COMERCIO, 29740000)
                    .put(MICRO, INDUSTRIA_Y_MINERÍA, 26540000)
                    .put(MICRO, AGROPECUARIO, 12890000)
                    .put(PEQUEÑA, CONSTRUCCIÓN, 90310000)
                    .put(PEQUEÑA, SERVICIOS, 50950000)
                    .put(PEQUEÑA, COMERCIO, 178860000)
                    .put(PEQUEÑA, INDUSTRIA_Y_MINERÍA, 190410000)
                    .put(PEQUEÑA, AGROPECUARIO, 48480000)
                    .put(MEDIANAT1, CONSTRUCCIÓN, 503880000)
                    .put(MEDIANAT1, SERVICIOS, 425170000)
                    .put(MEDIANAT1, COMERCIO, 1502750000)
                    .put(MEDIANAT1, INDUSTRIA_Y_MINERÍA, 1190330000)
                    .put(MEDIANAT1, AGROPECUARIO, 345430000)
                    .put(MEDIANAT2, CONSTRUCCIÓN, 755740000)
                    .put(MEDIANAT2, SERVICIOS, 607210000)
                    .put(MEDIANAT2, COMERCIO, 2146810000)
                    .put(MEDIANAT2, INDUSTRIA_Y_MINERÍA, 1739590000)
                    .put(MEDIANAT2, AGROPECUARIO, 547890000)
                    .build();

    private static final Table<CategoríaAFIP, Sector, Integer> tablaDeCategoríaPorPersonal =
            ImmutableTable.<CategoríaAFIP, Sector, Integer>builder()
                    .put(MICRO, CONSTRUCCIÓN, 12)
                    .put(MICRO, SERVICIOS, 7)
                    .put(MICRO, COMERCIO, 7)
                    .put(MICRO, INDUSTRIA_Y_MINERÍA, 15)
                    .put(MICRO, AGROPECUARIO, 5)
                    .put(PEQUEÑA, CONSTRUCCIÓN, 45)
                    .put(PEQUEÑA, SERVICIOS, 30)
                    .put(PEQUEÑA, COMERCIO, 35)
                    .put(PEQUEÑA, INDUSTRIA_Y_MINERÍA, 60)
                    .put(PEQUEÑA, AGROPECUARIO, 10)
                    .put(MEDIANAT1, CONSTRUCCIÓN, 200)
                    .put(MEDIANAT1, SERVICIOS, 165)
                    .put(MEDIANAT1, COMERCIO, 125)
                    .put(MEDIANAT1, INDUSTRIA_Y_MINERÍA, 235)
                    .put(MEDIANAT1, AGROPECUARIO, 50)
                    .put(MEDIANAT2, CONSTRUCCIÓN, 590)
                    .put(MEDIANAT2, SERVICIOS, 535)
                    .put(MEDIANAT2, COMERCIO, 345)
                    .put(MEDIANAT2, INDUSTRIA_Y_MINERÍA, 655)
                    .put(MEDIANAT2, AGROPECUARIO, 215)
                    .build();

    public Sector sector() {
        return sector;
    }

    public Integer ventasTotalesAnuales() {
        return ventasTotalesAnuales;
    }

    public Integer cantidadDePersonal() {
        return cantidadDePersonal;
    }
}
