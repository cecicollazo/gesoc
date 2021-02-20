package com.gesoc.repositorios;

import com.gesoc.modelo.operaciones.Operación;

import java.util.List;

public abstract class RepositorioOperaciones<O extends Operación> extends Repositorio<O> {
    protected RepositorioOperaciones(final Class<O> clase) {
        super(clase);
    }

    public List<O> obtenerPaginadoPorOrganización(final int offset, final int cantidadPorPágina,
                                                  final Long idOrganización) {
        return obtenerPaginadoCondición(offset, cantidadPorPágina, "organizaciónId", idOrganización.toString());
    }

    public List<O> obtenerPorOrganización(final Long idOrganización) {
        return obtenerConCondición("organizaciónId", idOrganización.toString());
    }
}
