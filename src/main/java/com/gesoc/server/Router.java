package com.gesoc.server;

import com.gesoc.controllers.*;
import com.gesoc.repositorios.DatosPrueba;
import com.gesoc.repositorios.RepositorioEgresos;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static spark.Spark.*;

public final class Router {

    private static final CustomHandlebarsTemplateEngine CUSTOM_HANDLEBARS_TEMPLATE_ENGINE =
            new CustomHandlebarsTemplateEngine();
    private static HandlebarsTemplateEngine handlebarsTemplateEngine = new HandlebarsTemplateEngine();
    private static Gson gson = new Gson();
	static EntityManagerFactory entityManagerFactory;

    public static void configureRoutes() {

        before(Authentication::autenticar);

        //AUTENTICACION
        post("/login", LoginController::verificarUsuario);
        get("/errorInicioSesion", LoginController::errorInicioSesion, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/logout", LoginController::logout, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);

        //HOME
        get("/", HomeController::home, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);

        //INGRESOS
        get("/ingresos", IngresosController::listarIngresos, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/ingresos/detalle/:id", IngresosController::detalleIngreso, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);

        //EGRESOS
        get("/egresos", EgresosController::listarEgresos, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/egresos/nuevo", EgresosController::crearNuevo, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        post("/guardarEgreso/:id", EgresosController::guardarEgreso);
        post("/guardarEgreso/", EgresosController::guardarEgreso);
        get("/egresos/detalle/:id", EgresosController::detalleEgreso, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/egresos/editar/:id", EgresosController::editarEgreso, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        delete("/eliminarEgreso/:id", EgresosController::eliminarEgreso);

        //PRESUPUESTOS
        get("/egresos/:idEgreso/presupuestos", PresupuestosController::listarPresupuestos,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/egresos/:idEgreso/presupuestos/nuevo", PresupuestosController::formularioPresupuesto,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        post("/egresos/:idEgreso/presupuestos", PresupuestosController::guardarPresupuesto);
        get("/egresos/:idEgreso/presupuestos/:id", PresupuestosController::detallePresupuesto,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);

        //DOCUMENTOS
        get("/egresos/:idEgreso/documentos", DocumentosController::detalleDocumento,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/egresos/:idEgreso/documentos/nuevo", DocumentosController::formularioDocumento,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        post("/egresos/:idEgreso/documentos", DocumentosController::guardarDocumento);

        //VINCULADOR
        get("/api/balances", VinculadorController::vinculadorAPI);
        get("/api/movimientos",RouteWithTransaction(CriterioValidacion::mongoEgreso));
        // VALIDACIÓN
        //get("/api/validar/:id", ValidaciónController::validar);

        //validar
        //get("/api/validar/:id",RouteWithTransaction(ValidaciónController::validarByParamsSQL));
        
        get("/api/validar/:id",RouteWithTransaction(CriterioValidacion::validarByParamsSQL));
        /*
        get("/api/validar/:id",RouteWithTransaction((req, res, em) -> {
			try {
				return CriterioValidacion.validarByParamsSQL(req, res, em);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return req;
		}));
		
		*/
        //DOCUMENTACION API
        get("/api", ApiController::documentacion, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);

        //CATEGORIAS
        get("/criterios-categorizacion", CategoríasController::listarCriterios, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/criterios-categorizacion/:criterio", CategoríasController::listarCategorías,
                CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        get("/categorias/:categoria", CategoríasController::listarCategorizables, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
       



        exception(Exception.class, (e, request, response) -> {
            response.status(500);
            response.body(e.getMessage());
        });
        //ENDPOINT DATOS
        get("/data", DataController::data, CUSTOM_HANDLEBARS_TEMPLATE_ENGINE);
        post("/api/init", DatosPrueba::inicializarDatos);
        post("/api/delete", DatosPrueba::borrarTodosLosDatos);
        get("/api/proveedores", DataController::proveedores);

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"404 - Recurso no encontrado o mal solicitado\"}";
        });

    }
    private static TemplateViewRoute TemplWithTransaction(WithTransaction<ModelAndView> fn) {
        TemplateViewRoute r = (req, res) -> {
        	 EntityManager em = RepositorioEgresos.ENTITY_MANAGER;
            em.getTransaction().begin();
            try {
                ModelAndView result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
}

    private static Route RouteWithTransaction(WithTransaction<Object> fn) {
        Route r = (req, res) -> {
            EntityManager em = RepositorioEgresos.ENTITY_MANAGER;
            em.getTransaction().begin();
            try {
                Object result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            }
        };
        return r;
    }

    private Router() {
    }

}

