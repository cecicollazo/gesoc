package com.gesoc.controllers;

import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.categorización.CriterioCategorización;
import com.gesoc.modelo.operaciones.Egreso;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CategoríasController {

    public static ModelAndView listarCriterios(final Request request, final Response response) {
        final Set<CriterioCategorización> criterios = ControllerUtils.obtenerCategorizador(request).getCriterios();

        final Map<String, Set<CriterioCategorización>> model = new HashMap<>();
        model.put("criterios", criterios);
        return new ModelAndView(model, "criterios.hbs");
    }

    public static ModelAndView listarCategorías(final Request request, final Response response) {
        final String nombreCriterio = request.params("criterio");
        // TODO: excepción específica
        final CriterioCategorización criterio =
                ControllerUtils.obtenerCategorizador(request).obtenerCriterio(nombreCriterio)
                        .orElseThrow(RuntimeException::new);
        final Set<Categoría> categorías = criterio.getCategorías();
        final Map<String, Set<Categoría>> model = new HashMap<>();
        model.put("categorías", categorías);
        return new ModelAndView(model, "categorías.hbs");
    }

    public static ModelAndView listarCategorizables(final Request request, final Response response) {
        final String nombreCategoría = request.params("categoria");
        // TODO: excepción específica
        final Categoría categoría =
                ControllerUtils.obtenerCategorizador(request).obtenerCategoría(nombreCategoría)
                        .orElseThrow(RuntimeException::new);
        final Set<Egreso> egresos = ControllerUtils.obtenerOrganización(request).getEgresos().stream()
                .filter(egreso -> egreso.categorías().stream()
                        .anyMatch(c -> c.perteneceA(categoría))
                )
                .collect(Collectors.toSet());
        Map<String, Object> model = new HashMap<>();
        model.put("categoría", categoría);
        model.put("egresos", egresos);
        return new ModelAndView(model, "contenido-categoría.hbs");
    }

    private CategoríasController() {
    }
}
