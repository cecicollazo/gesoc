package com.gesoc.controllers;

import com.gesoc.dto.balance.BalanceDTO;
import com.gesoc.excepciones.ErrorMessage;
import com.gesoc.mappers.VinculadorMapper;
import com.gesoc.modelo.vinculador.balances.Balance;
import com.gesoc.modelo.vinculador.condiciones.CondicionVinculacion;
import com.gesoc.modelo.vinculador.condiciones.PeriodoAceptable;
import com.gesoc.modelo.vinculador.criterios.Criterio;
import com.gesoc.services.VinculadorService;
import com.google.gson.Gson;
import org.eclipse.jetty.util.StringUtil;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VinculadorController {

    private static Gson gson = new Gson();

    public static String vinculadorAPI(Request req, Response res) {
        String criterio = req.queryParams("criterio");
        String fechaDesde;
        String fechaHasta;
        LocalDate desde;
        LocalDate hasta;
        Set<CondicionVinculacion> condiciones = new HashSet();

        // TODO: si está vacío es null
        if (criterio.isEmpty()) {
            res.status(404);
            return gson.toJson(new ErrorMessage("El parametro criterio es obligatorio para realizar una vinculacion"));
        }

        if (Criterio.FECHA.toString().equals(criterio.toUpperCase())) {
            fechaDesde = req.queryParams("fechaDesde");
            fechaHasta = req.queryParams("fechaHasta");
            if (StringUtil.isEmpty(fechaDesde) || StringUtil.isEmpty(fechaHasta)) {
                res.status(404);
                return gson.toJson(new ErrorMessage("Las fechas de vinculacion son obligatorias"));
            }
            try {
                desde = LocalDate.parse(fechaDesde);
                hasta = LocalDate.parse(fechaHasta);
                condiciones.add(new PeriodoAceptable(desde, hasta));
            } catch (Exception e) {
                res.status(404);
                return gson.toJson(new ErrorMessage("Las fechas de vinculacion no son correctas deben cumplir con el formato: yyyy-MM-dd"));
            }
        }

        Long organizacionId = ControllerUtils.obtenerOrganización(req).getId();

        Set<? extends Balance<?, ?>> balances = new VinculadorService(criterio, organizacionId).vincular(condiciones);
        List<BalanceDTO> balancesDTO = new VinculadorMapper().mapearRespuestaBalance(balances);
        res.type("application/json");
        return gson.toJson(balancesDTO);
    }

}
