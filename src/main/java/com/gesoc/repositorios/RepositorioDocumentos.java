package com.gesoc.repositorios;

import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.operaciones.DocumentoComercial;

import java.util.List;

public class RepositorioDocumentos extends Repositorio<DocumentoComercial> {

    public RepositorioDocumentos() {
        super(DocumentoComercial.class);
    }

    @Override
    public List<DocumentoComercial> obtenerTodos() {
        return super.obtenerTodos();
    }
}
