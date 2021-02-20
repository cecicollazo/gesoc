package com.gesoc.controllers;

import com.gesoc.utils.Const;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {

    public static ModelAndView home(Request req, Response res) {

        Map<String, Object> model = new HashMap<>();
        model.put("logged", req.session().attribute(Const.ID_USUARIO));
        return new ModelAndView(model, "home.hbs");
    }

}
