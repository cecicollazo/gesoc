package com.gesoc.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public final class ApiController {

    public static ModelAndView documentacion(final Request req, final Response res) {
        final Map<String, Object> model = ControllerUtils.modeloConSesión(req);
        return new ModelAndView(model, "apiDocumentacion.hbs");
    }

    private ApiController() {
    }
}
