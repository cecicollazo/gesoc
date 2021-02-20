package com.gesoc.controllers;

import com.gesoc.dto.PresupuestoDTO;
import com.gesoc.excepciones.GeSocException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.organización.Organizacion;
import com.gesoc.morphia.Cambios;
import com.gesoc.morphia.Ejecucion;
import com.gesoc.repositorios.RepositorioEgresos;
import com.gesoc.repositorios.RepositorioOrganizaciones;
import com.gesoc.repositorios.RepositorioPresupuestos;
import com.gesoc.utils.Const;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PresupuestosController {
    private static final RepositorioPresupuestos REPOSITORIO_PRESUPUESTOS = new RepositorioPresupuestos();
    private static final RepositorioEgresos REPOSITORIO_EGRESOS = new RepositorioEgresos();

    public static ModelAndView listarPresupuestos(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("idEgreso"));
        final Organizacion organización = ControllerUtils.obtenerOrganización(request);
        // TODO: excepción
        final List<Presupuesto> presupuestos = REPOSITORIO_EGRESOS.obtenerPorOrganización(organización.getId()).stream()
                .filter(egreso -> egreso.getId().equals(idEgreso))
                .findFirst()
                .map(egreso -> new ArrayList<>(egreso.getPresupuestos()))
                .orElseThrow(RuntimeException::new);
        final Map<String, Object> model = new HashMap<>();
        ControllerUtils.agregarCategorización(request, model, presupuestos, "presupuestos");
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("esÚltimaPágina", true);
        model.put("idEgreso", idEgreso);
        return new ModelAndView(model, "presupuestos.hbs");
    }

    public static ModelAndView detallePresupuesto(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("idEgreso"));
        final Organizacion organización = ControllerUtils.obtenerOrganización(request);
        // TODO: excepción
        final List<Presupuesto> presupuestos = REPOSITORIO_EGRESOS.obtenerPorOrganización(organización.getId()).stream()
                .filter(egreso -> egreso.getId().equals(idEgreso))
                .findFirst()
                .map(egreso -> new ArrayList<>(egreso.getPresupuestos()))
                .orElseThrow(RuntimeException::new);
        final Map<String, Object> model = new HashMap<>();
        final Long idPresupuesto = Long.valueOf(request.params("id"));
        final Presupuesto presupuesto = REPOSITORIO_PRESUPUESTOS.obtenerById(idPresupuesto);

        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("presupuesto", presupuesto);
        model.put("idEgreso", idEgreso);
        return new ModelAndView(model, "detallePresupuesto.hbs");
    }

    public static ModelAndView formularioPresupuesto(final Request request, final Response response) {
        final Map<String, Object> model = new HashMap<>();
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("idEgreso", request.params("idEgreso"));
        return new ModelAndView(model, "presupuesto-carga.hbs");
    }

    public static String guardarPresupuesto(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("idEgreso"));
        // TODO: validar que tenga permisos
        final String presupuestoJson = request.body();
        try {
            final PresupuestoDTO presupuestoDTO = new Gson().fromJson(presupuestoJson, PresupuestoDTO.class);
            presupuestoDTO.setCategorizador(ControllerUtils.obtenerCategorizador(request));
            final Presupuesto presupuestoParse = presupuestoDTO.parse();
            Presupuesto presupuesto = new Presupuesto();
            presupuestoParse.setId(presupuesto.getId());
            //final Egreso egreso = REPOSITORIO_EGRESOS.obtenerById(idEgreso);
            RepositorioEgresos repo = new RepositorioEgresos();
            final Egreso egreso = repo.byIDEgresoSQL(idEgreso);
            
            egreso.agregarPresupuesto(presupuestoParse);
            
// a revisar
            final Organizacion organizacion = ControllerUtils.obtenerOrganización(request);
            organizacion.agregarOActualizarEgreso(egreso,request);
            
            Cambios cambios = new Cambios();
    		
            final RepositorioOrganizaciones repo2 = new RepositorioOrganizaciones();
            repo2.guardar(organizacion);
            Egreso egreso1 = repo.byIDEgresoSQL(idEgreso);    
            cambios.cargarEnMongo(request.session().attribute(Const.ID_USUARIO), "Presupuesto", Ejecucion.ALTA,presupuestoParse.getId());
            //REPOSITORIO_EGRESOS.guardar(egreso);
            response.status(HttpServletResponse.SC_OK);
            response.type("application/json");
            return "{\"status\":\"200 OK\"}";
        } catch (final GeSocException e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            response.type("application/json");
            e.printStackTrace();
            return "{\"Error message\":\"###\"}".replace("###", e.getMessage());
        } catch (Exception e) {
            response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.type("application/json");
            e.printStackTrace();
            return "{\"Error message\":\"###\"}".replace("###", e.getMessage());
        }
    }

    private PresupuestosController() {
    }
}
