package com.gesoc.controllers;

import com.gesoc.dto.PresupuestoDTO;
import com.gesoc.excepciones.GeSocException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.TipoDeDocumentoComercial;
import com.gesoc.modelo.organización.Organizacion;
import com.gesoc.morphia.Cambios;
import com.gesoc.morphia.Ejecucion;
import com.gesoc.repositorios.RepositorioDocumentos;
import com.gesoc.repositorios.RepositorioEgresos;
import com.gesoc.repositorios.RepositorioOrganizaciones;
import com.gesoc.repositorios.RepositorioPresupuestos;
import com.gesoc.utils.Const;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DocumentosController {
    private static final RepositorioPresupuestos REPOSITORIO_PRESUPUESTOS = new RepositorioPresupuestos();
    private static final RepositorioEgresos REPOSITORIO_EGRESOS = new RepositorioEgresos();

    public static ModelAndView detalleDocumento(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("idEgreso"));
        final Map<String, Object> model = new HashMap<>();
        final DocumentoComercial documento = new RepositorioEgresos().byIDEgresoSQL(idEgreso).getDocumentoComercial();
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("documento", documento);
        model.put("idEgreso", idEgreso);
        return new ModelAndView(model, "detalleDocumento.hbs");
    }

    public static ModelAndView formularioDocumento(final Request request, final Response response) {
        final Map<String, Object> model = new HashMap<>();
        model.put("logged", request.session().attribute(Const.ID_USUARIO));
        model.put("idEgreso", request.params("idEgreso"));
        return new ModelAndView(model, "documento-carga.hbs");
    }

    public static String guardarDocumento(final Request request, final Response response) {
        final Long idEgreso = Long.valueOf(request.params("idEgreso"));
        final JSONObject documentoJson = new JSONObject(request.body());
        try {
            TipoDeDocumentoComercial tipo = TipoDeDocumentoComercial.valueOf(documentoJson.getString("tipo"));
            String numero = documentoJson.getString("numero");
            String pathAlArchivo = documentoJson.getString("pathAlArchivo");
            String pathAlLink = documentoJson.getString("pathAlLink");

            DocumentoComercial documento = new DocumentoComercial(tipo, numero, pathAlArchivo, pathAlLink);
            RepositorioEgresos repo = new RepositorioEgresos();
            final Egreso egreso = repo.byIDEgresoSQL(idEgreso);
            final Egreso egreso1 = egreso;
            egreso.setDocumentoComercial(documento);

            final Organizacion organizacion = ControllerUtils.obtenerOrganización(request);
            organizacion.agregarOActualizarEgreso(egreso,request);

            Cambios cambios = new Cambios();
    		cambios.cargarEnMongo(request.session().attribute(Const.ID_USUARIO), "Documentos", Ejecucion.ALTA,documento.getId());
            final RepositorioOrganizaciones repo2 = new RepositorioOrganizaciones();
            repo2.guardar(organizacion);

            response.status(HttpServletResponse.SC_OK);
            response.type("application/json");
            return "{\"status\":\"200 OK\"}";
        } catch (final GeSocException e) {
            response.status(HttpServletResponse.SC_BAD_REQUEST);
            response.type("application/json");
            e.printStackTrace();
            return "{\"Error message\":\"###\"}".replace("###", e.getMessage());
        } catch (Exception e) {
            response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.type("application/json");
            e.printStackTrace();
            return "{\"Error message\":\"###\"}".replace("###", e.getMessage());
        }
    }

    private DocumentosController() {
    }
}
