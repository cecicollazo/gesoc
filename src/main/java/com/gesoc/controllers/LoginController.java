package com.gesoc.controllers;

import com.gesoc.modelo.usuarios.Usuario;
import com.gesoc.repositorios.RepositorioUsuarios;
import com.gesoc.utils.Const;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Optional;

public final class LoginController {

    private static final RepositorioUsuarios REPOSITORIO_USUARIOS = new RepositorioUsuarios();

    public static Object verificarUsuario(final Request request, final Response response) {

        final String username = request.queryParams("username");
        final String password = request.queryParams("password");
        Optional<Usuario> usuario = REPOSITORIO_USUARIOS.obtenerPorNombre(username);

        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            request.session(true);
            request.session().attribute(Const.ID_USUARIO, usuario.get().getNombre());
            response.redirect("/");
        } else {
            response.redirect("/errorInicioSesion");
        }
        return null;
    }

    public static ModelAndView errorInicioSesion(final Request req, final Response res) {
        return new ModelAndView(null, "errorInicioSesion.hbs");
    }

    public static ModelAndView logout(Request req, Response res) {
        req.session(false);
        req.session().attribute(Const.ID_USUARIO, null);
        res.redirect("/");
        return null;
    }

    private LoginController() {
    }
}
