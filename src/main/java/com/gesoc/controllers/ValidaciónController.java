package com.gesoc.controllers;

import com.gesoc.excepciones.ErrorMessage;
import com.gesoc.excepciones.GeSocException;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.resultado.ResultadoValidación;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public final class ValidaciónController {
    private static final Gson GSON = new Gson();

    public static String validar(final Request request, final Response response) {
        try {
            final Long idEgreso = Long.valueOf(request.params("id"));
            // TODO: excepción
            final Egreso egreso = ControllerUtils.obtenerOrganización(request).getEgresos().stream()
                    .filter(e -> e.getId().equals(idEgreso))
                    .findAny()
                    .orElseThrow(RuntimeException::new);
            final Set<ResultadoValidación> resultadosValidación = egreso.resultadosValidación();
            return GSON.toJson(resultadosValidación);
        } catch (final GeSocException exception) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            return GSON.toJson(new ErrorMessage(exception.getMessage()));
        }
    }

    private ValidaciónController() {
    }
}
