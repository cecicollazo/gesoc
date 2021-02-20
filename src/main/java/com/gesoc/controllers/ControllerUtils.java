package com.gesoc.controllers;

import com.gesoc.modelo.categorización.Categorizable;
import com.gesoc.modelo.categorización.Categorizador;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.operaciones.Operación;
import com.gesoc.modelo.organización.Organizacion;
import com.gesoc.modelo.usuarios.Usuario;
import com.gesoc.repositorios.RepositorioOperaciones;
import com.gesoc.repositorios.RepositorioUsuarios;
import com.gesoc.utils.Const;
import spark.Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

final class ControllerUtils {
    static final String FORM_NAME_CATEGORÍAS = "categorías";
    static final String MODEL_KEY_CATEGORIZADOR = "categorizador";
    static final String MODEL_KEY_USERNAME = "logged";
    static final String MODEL_KEY_CATEGORÍAS_FILTRADAS = "categoríasFiltradas";

    private static final String QUERY_PARAM_NÚMERO_PÁGINA = "página";
    private static final String QUERY_PARAM_CANTIDAD_POR_PÁGINA = "cantidadPorPágina";

    private static final int CANTIDAD_POR_PÁGINA_DEFAULT = 6;

    private static final RepositorioUsuarios REPOSITORIO_USUARIOS = new RepositorioUsuarios();

    static Organizacion obtenerOrganización(final Request request) {
        return obtenerUsuario(request).getOrganización();
    }

    static Categorizador obtenerCategorizador(final Request request) {
        return obtenerOrganización(request).getCategorizador();
    }

    static Map<String, Object> modeloConSesión(final Request request) {
        final Map<String, Object> model = new HashMap<>();
        model.put(MODEL_KEY_USERNAME, request.session().attribute(Const.ID_USUARIO));
        return model;
    }

    static <O extends Operación> void agregarOperacionesPaginadasCategorizadas(
            final Request request,
            final RepositorioOperaciones<O> repositorioOperaciones,
            final Map<String, Object> modelo,
            final String modelKeyOperaciones) {
        final String páginaString = request.queryParams(QUERY_PARAM_NÚMERO_PÁGINA);
        final int página = páginaString != null
                ? Integer.parseInt(páginaString)
                : 1;
        final String cantidadPorPáginaString = request.queryParams(QUERY_PARAM_CANTIDAD_POR_PÁGINA);
        final int cantidadPorPágina = cantidadPorPáginaString != null
                ? Integer.parseInt(cantidadPorPáginaString)
                : CANTIDAD_POR_PÁGINA_DEFAULT;
        modelo.put("páginaAnterior", página - 1);
        modelo.put("páginaSiguiente", página + 1);
        final long últimaPágina = (repositorioOperaciones
                .cantidadPorCondición("organizaciónId", obtenerOrganización(request).getId().toString()) - 1) /
                cantidadPorPágina + 1;
        final boolean esÚltimaPágina = página == últimaPágina;
        modelo.put("esÚltimaPágina", esÚltimaPágina);
        final List<O> operaciones =
                obtenerOperacionesPaginadas(request, página, cantidadPorPágina, repositorioOperaciones);
        agregarCategorización(request, modelo, operaciones, modelKeyOperaciones);
    }

    static void agregarCategorización(final Request request, final Map<String, Object> model,
                                      final List<? extends Categorizable> categorizables,
                                      final String modelKeyCategorizables) {
        final Categorizador categorizador = obtenerCategorizador(request);
        if (Objects.nonNull(request.queryParamsValues(FORM_NAME_CATEGORÍAS))) {
            // TODO: excepción
            final Set<Categoría> categorías = Arrays.stream(request.queryParamsValues(FORM_NAME_CATEGORÍAS))
                    .map(idCategoría -> categorizador.obtenerCategoría(Long.valueOf(idCategoría))
                            .orElseThrow(RuntimeException::new)
                    )
                    .collect(Collectors.toSet());
            final List<Categorizable> categorizablesFiltrados = categorizables.stream()
                    .filter(categorizable -> categorías.stream()
                            .anyMatch(categoría -> categorizable.perteneceA(categoría))
                    )
                    .collect(Collectors.toList());
            model.put(modelKeyCategorizables, categorizablesFiltrados);
            model.put(MODEL_KEY_CATEGORÍAS_FILTRADAS, categorías);
        } else {
            model.put(modelKeyCategorizables, categorizables);
        }
        model.put(MODEL_KEY_CATEGORIZADOR, categorizador);
    }

    private static <O extends Operación> List<O> obtenerOperacionesPaginadas(
            final Request request,
            final int página,
            final int cantidadPorPágina,
            final RepositorioOperaciones<O> repositorioOperaciones) {

        final int offset = (página - 1) * cantidadPorPágina;

        final Organizacion organizacion = obtenerOrganización(request);
        return repositorioOperaciones.obtenerPaginadoPorOrganización(offset, cantidadPorPágina, organizacion.getId());
    }

    private static String obtenerUsername(final Request request) {
        return request.session().attribute(Const.ID_USUARIO);
    }

    private static Usuario obtenerUsuario(final Request request) {
        final String username = obtenerUsername(request);
        // TODO: excepción específica
        return REPOSITORIO_USUARIOS.obtenerPorNombre(username).orElseThrow(RuntimeException::new);
    }

    private ControllerUtils() {
    }
}
