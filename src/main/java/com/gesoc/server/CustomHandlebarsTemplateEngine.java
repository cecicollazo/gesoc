package com.gesoc.server;

import spark.template.handlebars.HandlebarsTemplateEngine;

public class CustomHandlebarsTemplateEngine extends HandlebarsTemplateEngine {

    public CustomHandlebarsTemplateEngine() {
        super();
        handlebars.setInfiniteLoops(true);
    }
}
