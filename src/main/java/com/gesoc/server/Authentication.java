package com.gesoc.server;

import com.gesoc.utils.Const;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

public class Authentication {

    public static List<String> publicEndpoints = Arrays.asList("/", "/login", "/erroriniciosesion", "/logout", "/api/init", "/api/delete", "/data", "/api/proveedores");

    public static void autenticar(Request req, Response res){
        String idUsuario = req.session().attribute(Const.ID_USUARIO);
        if (idUsuario == null && !publicEndpoints.contains(req.pathInfo().toLowerCase())) {
            res.redirect("/");
        }
    }
}
