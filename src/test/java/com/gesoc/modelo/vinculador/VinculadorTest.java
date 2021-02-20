package com.gesoc.modelo.vinculador;

import com.gesoc.enums.TipoDocumento;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.operaciones.TipoDeDocumentoComercial;
import com.gesoc.modelo.vinculador.balances.Balance;
import com.gesoc.modelo.vinculador.condiciones.CondicionVinculacion;
import com.gesoc.modelo.vinculador.condiciones.PeriodoAceptable;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.gesoc.modelo.vinculador.criterios.Criterio.EGRESO;
import static org.junit.Assert.assertEquals;

public class VinculadorTest {

    public Vinculador vinculador = new Vinculador(EGRESO);
    public Set<Egreso> egresos = new HashSet<>();
    public Set<Ingreso> ingresos = new HashSet<>();
    public Set<CondicionVinculacion> condiciones = new HashSet();

    @Before
    public void iniciar(){
        vinculador = new Vinculador(EGRESO);
        egresos = new HashSet<>();
        ingresos = new HashSet<>();
        Proveedor proveedor1 = new Proveedor("Proveedor S.A", TipoDocumento.CUIL ,"111191991", "Av Rivadavia 2222");
        DocumentoComercial documentoComercial1 = new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "1", "path_archivo","path_link");

        LocalDate desde = LocalDate.now().minusDays(2);
        LocalDate hasta = LocalDate.now().plusDays(2);
        condiciones.add(new PeriodoAceptable(desde, hasta));

        // DINERTO TOTAL EN EGRESOS 19
        Egreso egreso1 = new Egreso(proveedor1, documentoComercial1);
        egreso1.cargarItem(new Item("item1",2));
        Egreso egreso2 = new Egreso(proveedor1, documentoComercial1);
        egreso2.cargarItem(new Item("item2",3));
        Egreso egreso3 = new Egreso(proveedor1, documentoComercial1);
        egreso3.cargarItem(new Item("item3",2));
        Egreso egreso4 = new Egreso(proveedor1, documentoComercial1);
        egreso4.cargarItem(new Item("item4",2));
        Egreso egreso5 = new Egreso(proveedor1, documentoComercial1);
        egreso5.cargarItem(new Item("item5",1));
        Egreso egreso6 = new Egreso(proveedor1, documentoComercial1);
        egreso6.cargarItem(new Item("item6",4));
        Egreso egreso7 = new Egreso(proveedor1, documentoComercial1);
        egreso7.cargarItem(new Item("item7",1));
        Egreso egreso8 = new Egreso(proveedor1, documentoComercial1);
        egreso8.cargarItem(new Item("item8",4));

        egresos.add(egreso1);
        egresos.add(egreso2);
        egresos.add(egreso3);
        egresos.add(egreso4);
        egresos.add(egreso5);
        egresos.add(egreso6);
        egresos.add(egreso7);
        egresos.add(egreso8);
    }

    @Test
    public void vincular2IngresosPorUnTotalDe25pesosConEgresosTotalesDe19PesosFaltan6PorCubrirEnAlguno() {
        Ingreso ingreso = new Ingreso();
        Ingreso ingreso2 = new Ingreso();
        ingreso.setMontoTotal(10);
        ingreso2.setMontoTotal(15);
        ingresos.add(ingreso);
        ingresos.add(ingreso2);

        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, egresos, condiciones);

        assertEquals(6, balances.stream().mapToInt(bal -> bal.faltantePorCubrir()).sum());
    }

    @Test
    public void alVincularTodosLosEgresosConIngresosLaListaDeEgresosQuedaVacia() {
        Ingreso ingreso = new Ingreso();
        Ingreso ingreso2 = new Ingreso();
        ingreso.setMontoTotal(10);
        ingreso2.setMontoTotal(15);
        ingresos.add(ingreso);
        ingresos.add(ingreso2);

        //TODO TEST QUEDO BOLUDO
        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, egresos, condiciones);
    }

    @Test
    public void asdasdasdasdasdasd() {
        Ingreso ingreso = new Ingreso();
        ingreso.setMontoTotal(10);

        HashSet<Ingreso> ingresos = new HashSet<>();
        ingresos.add(ingreso);

        Proveedor proveedor1 = new Proveedor("Proveedor S.A", TipoDocumento.CUIL ,"111191991", "Av Rivadavia 2222");
        DocumentoComercial documentoComercial1 = new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "1", "path_archivo","path_link");
        Egreso egreso1 = new Egreso(proveedor1, documentoComercial1);

        egreso1.cargarItem(new Item("item1",10));

        HashSet<Egreso> egresos = new HashSet<>();
        egresos.add(egreso1);

        //TODO TEST QUEDO BOLUDO
        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, egresos, condiciones);
    }

    @Test
    public void vincularUnaListaDeEgresosVaciaConIngresosNoModificaSuFaltantePorCubrir() {

        Ingreso ingreso = new Ingreso();
        ingreso.setMontoTotal(10);
        ingresos.add(ingreso);

        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, new HashSet<>(), condiciones);
        assertEquals(10, balances.stream().mapToInt(bal -> bal.faltantePorCubrir()).sum());

    }

    @Test
    public void vincularUnaListaDeEgresosConUnaDeIngresosCubriendoSuTotalElFaltantePorCubrirEsCero() {

        Ingreso ingreso = new Ingreso();
        ingreso.setMontoTotal(10);
        ingresos.add(ingreso);

        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, egresos, condiciones);
        assertEquals(0, balances.stream().mapToInt(bal -> bal.faltantePorCubrir()).sum());

    }

    @Test
    public void vincularUnaListaDeEgresosCon19PesosTotalesAUnaListaDeIngresosCon18PorCubrirCubreElTotalYSobranEgresos() {

        Ingreso ingreso = new Ingreso();
        ingreso.setMontoTotal(18);
        ingresos.add(ingreso);

        Set<? extends Balance<?, ?>> balances = vinculador.vincular(ingresos, egresos, condiciones);
        assertEquals(0, balances.stream().mapToInt(Balance::faltantePorCubrir).sum());
    }
}
