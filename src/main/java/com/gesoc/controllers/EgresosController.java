package com.gesoc.controllers;


import com.gesoc.dto.PresupuestoDTO;
import com.gesoc.enums.TipoProducto;
import com.gesoc.modelo.medioDePago.MedioDePago;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.medioDePago.MercadoPagoApi;
import com.gesoc.modelo.medioDePago.TipoDeMedioDePago;
import com.gesoc.modelo.categorización.Categorizador;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.item.Producto;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.TipoDeDocumentoComercial;
import com.gesoc.modelo.organización.Organizacion;
import com.gesoc.morphia.Cambios;
import com.gesoc.morphia.Ejecucion;
import com.gesoc.repositorios.RepositorioEgresos;
import com.gesoc.repositorios.RepositorioOrganizaciones;
import com.gesoc.repositorios.RepositorioProveedores;
import com.gesoc.utils.Const;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gesoc.controllers.ControllerUtils.MODEL_KEY_CATEGORIZADOR;

public final class EgresosController {
    private static final String FORM_NAME_FECHA = "fecha";

    private static final String MODEL_KEY_EGRESOS = "egresos";
    private static final String MODEL_KEY_EGRESO = "egreso";
    private static final String MODEL_KEY_PROVEEDORES = "proveedores";

    private static final RepositorioProveedores REPOSITORIO_PROVEEDORES = new RepositorioProveedores();
    private static final RepositorioOrganizaciones REPOSITORIO_ORGANIZACIONES = new RepositorioOrganizaciones();
    private static final RepositorioEgresos REPOSITORIO_EGRESOS = new RepositorioEgresos();

    public static ModelAndView listarEgresos(final Request request, final Response response) {
        final Map<String, Object> model = ControllerUtils.modeloConSesión(request);
        ControllerUtils.agregarOperacionesPaginadasCategorizadas(
                request, REPOSITORIO_EGRESOS, model, MODEL_KEY_EGRESOS
        );
        return new ModelAndView(model, "egresos.hbs");
    }

    public static ModelAndView crearNuevo(final Request request, final Response response) {
        final Map<String, Object> model = ControllerUtils.modeloConSesión(request);
        model.put(MODEL_KEY_PROVEEDORES, REPOSITORIO_PROVEEDORES.obtenerTodos());
        model.put(MODEL_KEY_CATEGORIZADOR, ControllerUtils.obtenerCategorizador(request));
        model.put("medioDePago", (new MercadoPagoApi().obtenerListaMediosDePago()));

        return new ModelAndView(model, "cargarEgreso.hbs");
    }

    public static ModelAndView detalleEgreso(final Request request, final Response response) {
        return formularioEgresoConDatos(request, "detalleEgreso.hbs");
    }

    public static ModelAndView editarEgreso(final Request request, final Response response) {
    	
        return formularioEgresoConDatos(request, "editarEgreso.hbs");
    }

    public static Object eliminarEgreso(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("id"));
        final Organizacion organizacion = ControllerUtils.obtenerOrganización(request);
        organizacion.borrarEgreso(idEgreso);
        
        
        REPOSITORIO_ORGANIZACIONES.guardar(organizacion);
        REPOSITORIO_EGRESOS.borrarPorId(idEgreso);
        response.status(HttpServletResponse.SC_ACCEPTED);
        Cambios cambios = new Cambios();
		cambios.cargarEnMongo(request.session().attribute(Const.ID_USUARIO), "Egresos", Ejecucion.BAJA,idEgreso);
        
        return "Egreso eliminado correctamente";
    }

    public static ModelAndView guardarEgreso(final Request request, final Response response) {
        final Organizacion organizacion = ControllerUtils.obtenerOrganización(request);
        final Proveedor proveedor =
                REPOSITORIO_PROVEEDORES.obtenerById(Long.valueOf(request.queryParams("idProveedor")));

        final Egreso egreso = construirEgreso(request, ControllerUtils.obtenerCategorizador(request), proveedor);
        
        organizacion.agregarOActualizarEgreso(egreso,request);
        
        
        REPOSITORIO_ORGANIZACIONES.guardar(organizacion);
        
		
        response.redirect("/egresos");
        // TODO: esto está bien? creo que debería retornar algo
        return null;
    }

    private static ModelAndView formularioEgresoConDatos(final Request request, final String nombreTemplate) {
        final Map<String, Object> model = new HashMap<>();

        final List<Proveedor> proveedores = REPOSITORIO_PROVEEDORES.obtenerTodos();
        final Egreso egreso = obtenerEgreso(request);
        proveedores.removeIf(proveedor -> proveedor.getId().equals(egreso.getProveedor().getId()));
        model.put(ControllerUtils.MODEL_KEY_USERNAME, request.session().attribute(Const.ID_USUARIO));
        model.put(MODEL_KEY_EGRESO, egreso);
        model.put(MODEL_KEY_PROVEEDORES, proveedores);
        model.put("itemsLength", egreso.getItems().size());
        model.put(MODEL_KEY_CATEGORIZADOR, ControllerUtils.obtenerCategorizador(request));
        model.put("medioDePago", (new MercadoPagoApi().obtenerListaMediosDePago()));
        return new ModelAndView(model, nombreTemplate);
    }

    private static Egreso obtenerEgreso(final Request request) {
        final Long idEgreso = Long.valueOf(request.params("id"));
        // TODO: excepción
        return ControllerUtils.obtenerOrganización(request).getEgresos().stream()
                .filter(e -> e.getId().equals(idEgreso))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private static Egreso construirEgreso(final Request request, final Categorizador categorizador,
                                          final Proveedor proveedor) {
        final String idEgreso = request.params("id");
        final LocalDate fecha = obtenerFecha(request);
        final Boolean requierePresupuesto = requierePresupuesto(request);
        final String[] paramIdsCategorías = request.queryParamsValues(ControllerUtils.FORM_NAME_CATEGORÍAS);
        final Set<Long> idsCategorías;
        if (paramIdsCategorías != null) {
            idsCategorías =
                    Arrays.stream(paramIdsCategorías)
                            .map(Long::valueOf)
                            .collect(Collectors.toSet());
        } else {
            idsCategorías = new HashSet<>();
        }

        //DocumentoComercial documentoComercial = obtenerDocumentoComercial(request);
        final MedioDePago medioDePago = obtenerMedioDePago(request);
        final Set<Item> items = obtenerItems(request);
        //final Presupuesto presupuesto = obtenerPresupuesto(request);

        final Egreso egreso;
        if (Objects.nonNull(idEgreso)) {
            egreso = obtenerEgreso(request);
        } else {
            egreso = new Egreso();
        }
        egreso.setFecha(fecha);
        egreso.setRequierePresupuesto(requierePresupuesto);
        egreso.setProveedor(proveedor);
        //egreso.agregarPresupuesto(presupuesto);
        final Set<Categoría> categorías = new HashSet<>();
        // TODO: excepción
        idsCategorías.forEach(
                id -> categorías.add(categorizador.obtenerCategoría(id).orElseThrow(RuntimeException::new))
        );
        egreso.setCategorías(categorías);
        egreso.setItems(items);
        egreso.setMedioDePago(medioDePago);
        //egreso.setDocumentoComercial(documentoComercial);
        return egreso;
    }

    private static Presupuesto obtenerPresupuesto(Request request) {
    	 
         // TODO: validar que tenga permisos
         final String presupuestoJson = request.body();
         //final String presupuestoJson = request.queryParams("archivoDocumentoInput");
             final PresupuestoDTO presupuestoDTO = new Gson().fromJson(presupuestoJson, PresupuestoDTO.class);
            // presupuestoDTO.setCategorizador(ControllerUtils.obtenerCategorizador(request));
             final Presupuesto presupuesto = presupuestoDTO.parse();
             //final Egreso egreso = REPOSITORIO_EGRESOS.obtenerById(idEgreso);
             
             
             
             
             return presupuesto;
         
         }
		
	

	private static LocalDate obtenerFecha(final Request request) {
        final String stringFecha = request.queryParams(FORM_NAME_FECHA);
        return LocalDate.parse(stringFecha);
    }

    private static Boolean requierePresupuesto(final Request request) {
        final String requierePresupuestoString = request.queryParams("requierePresupuesto");
        return "on".equalsIgnoreCase(requierePresupuestoString);
    }

    private static Set<Item> obtenerItems(final Request request) {
        final Set<Item> ítems = new HashSet<>();
        request.queryMap().toMap().entrySet().stream()
                .filter(param -> param.getKey().startsWith("item-"))
                .collect(Collectors.groupingBy(param -> param.getKey().split("-")[2]))
                .values().stream()
                .map(params -> params.stream()
                        .collect(Collectors.toMap(param -> param.getKey().split("-")[1], param -> param.getValue()[0]))
                )
                .forEach((params) -> {
                    final Producto producto = new Producto(
                            params.get("descripción"),
                            params.get("marca"),
                            params.get("origen"),
                            TipoProducto.valueOf(params.get("tipo"))
                    );
                    final Item ítem = new Item(
                            params.get("nombre"),
                            producto,
                            Integer.parseInt(params.get("precio")),
                            Integer.parseInt(params.get("cantidad"))
                    );
                    ítems.add(ítem);
                });
        return ítems;
    }

    private static DocumentoComercial obtenerDocumentoComercial(final Request request) {
        final String tipoDocumentoComercial = request.queryParams("tipoDocumentoComercial");
        final String numeroDocumentoComercial = request.queryParams("numeroDocumentoComercial");
        return new DocumentoComercial(
                TipoDeDocumentoComercial.valueOf(tipoDocumentoComercial), numeroDocumentoComercial, "", "");
    }

    private static MedioDePago obtenerMedioDePago(final Request request) {
        final String tipoDePago = request.params("medioDePago");
        final String identificador = request.queryParams("identificador");
        return new MedioDePago(tipoDePago, identificador);
    }

    private EgresosController() {
    }
}
