package com.gesoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gesoc.excepciones.ErrorMessage;
import com.gesoc.services.Resultado;
import com.gesoc.utils.Const;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class CriterioValidacion {

    public static ModelAndView validar(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        return new ModelAndView(model, "validar.hbs");
    }

    public static ModelAndView validarSQL(Request request, Response response, EntityManager entityManager) {

        Map<String, Object> model = new HashMap<>();
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        return new ModelAndView(model, "validar.hbs");
    }

    public static Object resultadosDeCompra(Request request, Response response) {
        String idEgreso = request.queryParams("egreso");

        return Resultado.resultados(request, response, idEgreso);
    }

    private static Gson gson = new Gson();

    public static Object validarByParams(Request request, Response response) {
        String idEgreso = request.params("id");

        if (idEgreso.isEmpty()) {
            response.status(404);
            return gson.toJson(new ErrorMessage("El id no debe estar vacío"));
        }
        return Resultado.resultados(request, response, idEgreso);

    }

    public static Object validarByParamsSQL(Request request, Response response ,EntityManager em)  {
    	String idEgreso = request.params("id");
        if (idEgreso.isEmpty()) {
            response.status(404);
            return gson.toJson(new ErrorMessage("El id no debe estar vacío"));
        }
        
        return Resultado.resultadosSQL(request, response, idEgreso, em);
    }
    
    public static Object mongoEgreso(Request request, Response response ,EntityManager em)  {
    	
        
        return Resultado.mongoEgreso(request, response, em);
    }
    
    

    public static Object redireccionar(Request request, Response response) {
        response.redirect("/validar");
        return null;
    }


}	

