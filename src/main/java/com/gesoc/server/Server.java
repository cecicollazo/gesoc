package com.gesoc.server;

import spark.Spark;

import static spark.debug.DebugScreen.enableDebugScreen;

public final class Server {
    private static final int DEFAULT_PORT = 4567;

    public static void main(final String[] args) {
        Spark.port(getPort());
        Spark.staticFiles.location("/public");
        Spark.init();

        enableDebugScreen();
        Router.configureRoutes();
    }

    private static int getPort() {
        final ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return DEFAULT_PORT;
    }

    private Server() {
    }

}
