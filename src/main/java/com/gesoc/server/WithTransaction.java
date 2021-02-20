package com.gesoc.server;

import spark.Request;
import spark.Response;


import javax.persistence.EntityManager;

@FunctionalInterface
public interface WithTransaction<E> {
    E method(Request req, Response res, EntityManager em);
}