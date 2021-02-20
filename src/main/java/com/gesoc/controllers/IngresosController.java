package com.gesoc.controllers;

import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.repositorios.RepositorioIngresos;
import com.gesoc.utils.Const;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public final class IngresosController {
    private static final String MODEL_KEY_INGRESOS = "ingresos";

    private static final RepositorioIngresos REPOSITORIO_INGRESOS = new RepositorioIngresos();

    public static ModelAndView listarIngresos(final Request request, final Response response) {
        final Map<String, Object> model = ControllerUtils.modeloConSesión(request);
        ControllerUtils.agregarOperacionesPaginadasCategorizadas(request, REPOSITORIO_INGRESOS, model, MODEL_KEY_INGRESOS);
        return new ModelAndView(model, "ingresos.hbs");
    }

    public static ModelAndView detalleIngreso(final Request request, final Response response) {
        final Map<String, Object> model = new HashMap<>();
        final String idIngreso = request.params("id");
        final Ingreso ingreso = REPOSITORIO_INGRESOS.obtenerById(Long.valueOf(idIngreso));
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("ingreso", ingreso);
        return new ModelAndView(model, "detalleIngreso.hbs");
    }

    private IngresosController() {
    }
}
