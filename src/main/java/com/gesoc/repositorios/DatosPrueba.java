package com.gesoc.repositorios;

import com.gesoc.enums.TipoDocumento;
import com.gesoc.enums.TipoProducto;
import com.gesoc.modelo.medioDePago.MedioDePago;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.medioDePago.TipoDeMedioDePago;
import com.gesoc.modelo.categorización.Categorizador;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.item.Producto;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.modelo.operaciones.TipoDeDocumentoComercial;
import com.gesoc.modelo.organización.Organizacion;
import com.gesoc.modelo.usuarios.Usuario;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class DatosPrueba {

    static List<Egreso> egresos = new ArrayList<>();
    private static Organizacion organizacion;

    public static void initPersistance() {
        final RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
        final RepositorioEgresos repositorioEgresos = new RepositorioEgresos();
        final RepositorioIngresos repositorioIngresos = new RepositorioIngresos();
        final RepositorioOrganizaciones repositorioOrganizaciones = new RepositorioOrganizaciones();
        final RepositorioProveedores repositorioProveedores = new RepositorioProveedores();
        final RepositorioPresupuestos repositorioPresupuesto = new RepositorioPresupuestos();

        final List<Proveedor> proveedores = proveedores();
        //final List<Presupuesto> presupuestos = presupuestos();
        proveedores.forEach(repositorioProveedores::guardar);
        //presupuestos.forEach(repositorioPresupuesto::guardar);

        final Organizacion organización1 = org();
        repositorioOrganizaciones.guardar(organización1);

        final Categoría caba = organización1.getCategorizador().obtenerCategoría("CABA").get();
        final Categoría laMatanza = organización1.getCategorizador().obtenerCategoría("La Matanza").get();
        final Categoría moreno = organización1.getCategorizador().obtenerCategoría("Moreno").get();

        final List<Ingreso> ingresos1 = ingresos();
        ingresos1.forEach(organización1::agregarIngreso);
        ingresos1.get(0).agregarCategoría(caba);
        ingresos1.get(1).agregarCategoría(laMatanza);
        ingresos1.get(2).agregarCategoría(moreno);
        repositorioOrganizaciones.guardar(organización1);

        final List<Egreso> egresos1 = egresos();
        egresos1.forEach(organización1::agregarEgreso);
        egresos1.get(0).setProveedor(proveedores.get(0));
        egresos1.get(1).setProveedor(proveedores.get(1));
        //egresos1.get(0).setPresupuestos((Set<Presupuesto>) presupuestos);
        //egresos1.get(1).setPresupuestos((Set<Presupuesto>) presupuestos);
        repositorioOrganizaciones.guardar(organización1);

        final Usuario usuarioOrganización1 = new Usuario("org1", "123", organización1);
        repositorioUsuarios.guardar(usuarioOrganización1);

        // ORGANIZACIÓN 2

        final Organizacion organización2 = org2();
        repositorioOrganizaciones.guardar(organización2);

        final List<Ingreso> ingresos2 = ingresos2();
        ingresos2.forEach(organización2::agregarIngreso);
        repositorioOrganizaciones.guardar(organización2);

        final List<Egreso> egresos2 = egresos2();
        egresos2.forEach(organización2::agregarEgreso);
        egresos2.get(0).setProveedor(proveedores.get(0));
        egresos2.get(1).setProveedor(proveedores.get(1));
        repositorioOrganizaciones.guardar(organización2);

        final Usuario usuarioOrganización2 = new Usuario("org2", "123", organización2);
        repositorioUsuarios.guardar(usuarioOrganización2);
    }

    public static String inicializarDatos(final Request request, final Response response) {
        initPersistance();
        return "Inicializado OK";
    }

    public static String borrarTodosLosDatos(final Request request, final Response response) {
        borrar(Usuario.class);
        borrar(Organizacion.class);
        borrar(Proveedor.class);
        return "Borrado OK";
    }

    private static <T> void borrar(final Class<T> clase) {
        final Repositorio<T> repositorio = new Repositorio<>(clase);
        final List<T> entidades = repositorio.obtenerTodos();
        entidades.forEach(repositorio::borrar);
    }

    private static List<Ingreso> ingresos() {
        final List<Ingreso> ingresos = new ArrayList<>();
        final LocalDate fechaIngreso = LocalDate.of(2020, Month.AUGUST, 1);
        final LocalDate fechaIngreso2 = LocalDate.of(2020, Month.MAY, 1);
        final LocalDate fechaIngreso3 = LocalDate.of(2020, Month.MAY, 1);
        final LocalDate fechaIngreso4 = LocalDate.of(2020, Month.APRIL, 1);
        final LocalDate fechaIngreso5 = LocalDate.of(2020, Month.APRIL, 1);
        final LocalDate fechaIngreso6 = LocalDate.of(2020, Month.APRIL, 1);
        final LocalDate fechaIngreso7 = LocalDate.of(2020, Month.MAY, 1);
        final LocalDate fechaIngreso8 = LocalDate.of(2020, Month.MAY, 1);
        final LocalDate fechaIngreso9 = LocalDate.of(2020, Month.MAY, 1);

        final Ingreso ingreso1 = new Ingreso();
        ingreso1.setDescripcion("Descripcion");
        ingreso1.setMontoTotal(200);
        ingreso1.setFecha(fechaIngreso);

        final Ingreso ingreso2 = new Ingreso();
        ingreso2.setDescripcion("Descripcion-2");
        ingreso2.setMontoTotal(400);
        ingreso2.setFecha(fechaIngreso2);

        final Ingreso ingreso3 = new Ingreso();
        ingreso3.setDescripcion("Descripcion-3");
        ingreso3.setMontoTotal(500);
        ingreso3.setFecha(fechaIngreso3);

        final Ingreso ingreso4 = new Ingreso();
        ingreso4.setDescripcion("Descripcion-4");
        ingreso4.setMontoTotal(200);
        ingreso4.setFecha(fechaIngreso4);

        final Ingreso ingreso5 = new Ingreso();
        ingreso5.setDescripcion("Descripcion-5");
        ingreso5.setMontoTotal(600);
        ingreso5.setFecha(fechaIngreso5);

        final Ingreso ingreso6 = new Ingreso();
        ingreso6.setDescripcion("Descripcion-6");
        ingreso6.setMontoTotal(100);
        ingreso6.setFecha(fechaIngreso6);

        final Ingreso ingreso7 = new Ingreso();
        ingreso7.setDescripcion("Descripcion-7");
        ingreso7.setMontoTotal(200);
        ingreso7.setFecha(fechaIngreso7);

        final Ingreso ingreso8 = new Ingreso();
        ingreso8.setDescripcion("Descripcion-8");
        ingreso8.setMontoTotal(250);
        ingreso8.setFecha(fechaIngreso8);

        final Ingreso ingreso9 = new Ingreso();
        ingreso9.setDescripcion("Descripcion-9");
        ingreso9.setMontoTotal(350);
        ingreso9.setFecha(fechaIngreso9);

        ingresos.add(ingreso1);
        ingresos.add(ingreso2);
        ingresos.add(ingreso3);
        ingresos.add(ingreso4);
        ingresos.add(ingreso5);
        ingresos.add(ingreso6);
        ingresos.add(ingreso7);
        ingresos.add(ingreso8);
        ingresos.add(ingreso9);
        return ingresos;
    }

    private static List<Ingreso> ingresos2() {
        final List<Ingreso> ingresos = new ArrayList<>();
        final LocalDate fechaIngreso = LocalDate.of(2020, Month.AUGUST, 1);
        final LocalDate fechaIngreso2 = LocalDate.of(2020, Month.MAY, 1);

        final Ingreso ingreso1 = new Ingreso();
        ingreso1.setDescripcion("Ingreso organización 2 - A");
        ingreso1.setMontoTotal(200);
        ingreso1.setFecha(fechaIngreso);

        final Ingreso ingreso2 = new Ingreso();
        ingreso2.setDescripcion("Ingreso organización 2 - B");
        ingreso2.setMontoTotal(400);
        ingreso2.setFecha(fechaIngreso2);

        ingresos.add(ingreso1);
        ingresos.add(ingreso2);
        return ingresos;
    }

    private static List<Proveedor> proveedores() {
        final List<Proveedor> proveedores = new ArrayList<>();
        final Proveedor proveedor1 =
                new Proveedor("Proveedor 1", TipoDocumento.DNI, "111191991", "Av Rivadavia 2222");
        final Proveedor proveedor2 =
                new Proveedor("Proveedor 2", TipoDocumento.CUIL, "2222222", "Av Mendoza 299");
        final Proveedor proveedor3 =
                new Proveedor("Proveedor 3", TipoDocumento.CUIT, "30111111115", "Av. La plata 13000");
        final Proveedor proveedor4 =
                new Proveedor("Proveedor 4", TipoDocumento.CUIL, "30222222226", "Av. Medellin 14000");
        final Proveedor proveedor5 =
                new Proveedor("Tecno 2000", TipoDocumento.CUIT, "20-40213998-8", "av Provincial 1234");

        proveedores.add(proveedor1);
        proveedores.add(proveedor2);
        proveedores.add(proveedor3);
        proveedores.add(proveedor4);
        proveedores.add(proveedor5);
        return proveedores;
    }

    public static List<Egreso> egresos() {
        final List<Egreso> egresos = new ArrayList<>();
        final Producto ordenador = new Producto("Ordenador", "Pineapple", "Chinarda", TipoProducto.ARTICULO);
        final Producto teclado = new Producto("Teclado", "Pineapple", "Chinarda", TipoProducto.ARTICULO);
        final Producto mouse = new Producto("Mouse", "Pineapple", "Chinarda", TipoProducto.ARTICULO);

        final Item item1 = new Item("Compra de ordenadores", ordenador, 500, 4);
        final Item item2 = new Item("Compra de teclados", teclado, 200, 4);

        final Proveedor proveedor1 = proveedores().get(0);
        final DocumentoComercial documentoComercial1 =
                new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "1", "path_archivo", "path_link");
        final MedioDePago medioDePago = new MedioDePago("Visa", "342342423");
        
        final Egreso egreso1 = new Egreso(proveedor1, documentoComercial1);
        egreso1.cargarItem(item1);
        egreso1.setMedioDePago(medioDePago);
        egreso1.cargarItem(item2);
        egreso1.setRequierePresupuesto(true);
        egreso1.setPresupuestos( presupuestos());

        egresos.add(egreso1);

        final Item item3 = new Item("Compra de computadoras", ordenador, 100, 4);
        final Item item4 = new Item("Compra de teclados para gerentes", teclado, 600, 4);
        final Item item5 = new Item("Compra de mouses pro", mouse, 250, 4);

        final Proveedor proveedor2 = proveedores().get(1);
        final DocumentoComercial documentoComercial2 =
                new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "2", "path_archivo", "path_link");
        final Egreso egreso2 = new Egreso(proveedor2, documentoComercial2);
        egreso2.cargarItem(item3);
        egreso2.cargarItem(item4);
        egreso2.cargarItem(item5);
        egreso2.setMedioDePago(medioDePago);
        egreso2.setRequierePresupuesto(true);
        egresos.add(egreso2);
        return egresos;
    }

    private static List<Egreso> egresos2() {
        final List<Egreso> egresos = new ArrayList<>();
        final Producto teclado = new Producto("Teclado", "Pineapple", "Chinarda", TipoProducto.ARTICULO);
        final Item item1 = new Item("Compra de teclados", teclado, 200, 4);
        final Proveedor proveedor1 = proveedores().get(0);
        final DocumentoComercial documentoComercial1 =
                new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "1", "path_archivo", "path_link");
        final MedioDePago medioDePago = new MedioDePago("Visa", "342342423");
        final Egreso egreso1 = new Egreso(proveedor1, documentoComercial1);
        egreso1.cargarItem(item1);
        egreso1.setMedioDePago(medioDePago);
        egreso1.setRequierePresupuesto(true);
        egresos.add(egreso1);
        final Item item2 = new Item("Compra de teclados para gerentes", teclado, 600, 4);
        final Proveedor proveedor2 = proveedores().get(1);
        final DocumentoComercial documentoComercial2 =
                new DocumentoComercial(TipoDeDocumentoComercial.FACTURA, "2", "path_archivo", "path_link");
        final Egreso egreso2 = new Egreso(proveedor2, documentoComercial2);
        egreso2.cargarItem(item2);
        egreso2.setMedioDePago(medioDePago);
        egreso2.setRequierePresupuesto(true);
        egreso2.setPresupuestos(presupuestos());
        egresos.add(egreso2);
        return egresos;
    }

    private static Organizacion org() {
        final Organizacion organización = new Organizacion("ECOSISTEMA S.A", 3);
        final Categorizador categorizador = organización.getCategorizador();
        categorizador.agregarCriterio("Provincia");
        categorizador.agregarCategoría("Buenos Aires", "Provincia");
        categorizador.agregarCategoría("Entre Ríos", "Provincia");
        categorizador.agregarCategoría("CABA", "Provincia");
        categorizador.agregarCriterio("Municipio", "Buenos Aires");
        categorizador.agregarCategoría("La Matanza", "Municipio");
        categorizador.agregarCategoría("San Martín", "Municipio");
        categorizador.agregarCategoría("Moreno", "Municipio");
        categorizador.agregarCriterio("Comuna", "CABA");
        categorizador.agregarCategoría("Comuna 9", "Comuna");
        categorizador.agregarCategoría("Comuna 10", "Comuna");
        return organización;
    }

    private static Organizacion org2() {
        final Organizacion organización = new Organizacion("Organización 2", 3);
        final Categorizador categorizador = organización.getCategorizador();
        categorizador.agregarCriterio("País");
        categorizador.agregarCategoría("Argentina", "País");
        categorizador.agregarCategoría("Uruguay", "País");
        
        //Agreago por Damian
        
        categorizador.agregarCriterio("Provincia");
        categorizador.agregarCategoría("Buenos Aires", "Provincia");
        categorizador.agregarCategoría("Entre Ríos", "Provincia");
        categorizador.agregarCategoría("CABA", "Provincia");
        categorizador.agregarCriterio("Municipio", "Buenos Aires");
        categorizador.agregarCategoría("La Matanza", "Municipio");
        categorizador.agregarCategoría("San Martín", "Municipio");
        categorizador.agregarCategoría("Moreno", "Municipio");
        categorizador.agregarCriterio("Comuna", "CABA");
        categorizador.agregarCategoría("Comuna 9", "Comuna");
        categorizador.agregarCategoría("Comuna 10", "Comuna");
        return organización;
    }

    public static List<Presupuesto> presupuestos() {
        final List<Presupuesto> presupuestos = new ArrayList<>();

        final Producto ordenador = new Producto("Ordenador", "Pineapple", "Chinarda", TipoProducto.ARTICULO);
        final Producto teclado = new Producto("Teclado", "Pineapple", "Chinarda", TipoProducto.ARTICULO);
        final Producto mouse = new Producto("Mouse", "Pineapple", "Chinarda", TipoProducto.ARTICULO);

        final Item item1 = new Item("Compra de ordenadores", ordenador, 500, 4);
        final Item item2 = new Item("Compra de teclados", teclado, 200, 4);
        final Item item3 = new Item("Compra de computadoras", ordenador, 100, 4);
        final Item item4 = new Item("Compra de teclados para gerentes", teclado, 600, 4);
        final Item item5 = new Item("Compra de mouses pro", mouse, 250, 4);

        final ArrayList<Item> items1 = new ArrayList<>();
        final ArrayList<Item> items2 = new ArrayList<>();

        items1.add(item1);
        items1.add(item3);
        items1.add(item5);
        items2.add(item2);
        items2.add(item4);

        final Proveedor proveedor1 = new Proveedor("Pepsi S.A", TipoDocumento.CUIL, "111191991", "Av Rivadavia 2222");
        final Proveedor proveedor2 = new Proveedor("CocaCola S.A", TipoDocumento.CUIL, "2222222", "Av Rivadavia 299");

        final Presupuesto presupuesto1 =
                new Presupuesto(Collections.emptySet(), items1, proveedor1, Collections.emptySet());

        final Presupuesto presupuesto2 =
                new Presupuesto(Collections.emptySet(), items2, proveedor2, Collections.emptySet());

        presupuestos.add(presupuesto1);
        presupuestos.add(presupuesto2);
        return presupuestos;
    }

    public Egreso byID(long ID) {
        List<Egreso> egreso = new ArrayList<Egreso>();
        egreso = egresos.stream().filter(a -> a.getId().equals(ID)).collect(Collectors.toList());
        if (!egreso.isEmpty()) {
            return egreso.get(0);
        } else {
            return null;
        }
    }

    public Organizacion getOrg() {
        return organizacion;

    }

    public DatosPrueba() {
    }
}
