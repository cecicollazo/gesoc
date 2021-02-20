package com.gesoc.controllers;

import com.gesoc.modelo.Proveedor;
import com.gesoc.repositorios.RepositorioProveedores;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataController {

    private static final RepositorioProveedores REPOSITORIO_PROVEEDORES = new RepositorioProveedores();
    private static final Gson GSON = new Gson();

    public static ModelAndView data(Request req, Response res) {

        Map<String, Object> model = new HashMap<>();
        return new ModelAndView(model, "data.hbs");
    }

    public static String proveedores(Request req, Response res) {
        List<Proveedor> proveedores = REPOSITORIO_PROVEEDORES.obtenerTodos();
        return GSON.toJson(proveedores);
    }

}
