package com.gesoc.dto;

import com.gesoc.excepciones.CategoríaInexistenteException;
import com.gesoc.excepciones.ProveedorInexistenteException;
import com.gesoc.excepciones.SinÍtemsException;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.categorización.Categorizador;
import com.gesoc.modelo.categorización.Categoría;
import com.gesoc.modelo.item.Item;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.repositorios.RepositorioProveedores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PresupuestoDTO {
    private static final RepositorioProveedores REPOSITORIO_PROVEEDORES = new RepositorioProveedores();

    private Categorizador categorizador;

    private final Collection<DocumentoComercial> documentos = new ArrayList<>();
    private final Collection<Item> ítems = new ArrayList<>();
    private final Long idProveedor;
    private final Collection<String> nombresCategoría = new ArrayList<>();

    public PresupuestoDTO(final Collection<DocumentoComercial> documentos,
                          final Collection<Item> ítems,
                          final Long idProveedor,
                          final Collection<String> nombresCategoría) {
        this.documentos.addAll(documentos);
        this.ítems.addAll(ítems);
        this.idProveedor = idProveedor;
        this.nombresCategoría.addAll(nombresCategoría);
    }

    public Presupuesto parse() {
        if (ítems.isEmpty()) {
            throw new SinÍtemsException();
        }
        final Proveedor proveedor = REPOSITORIO_PROVEEDORES.obtenerById(idProveedor);
        if (Objects.isNull(proveedor)) {
            throw new ProveedorInexistenteException(idProveedor);
        }
        final Set<Categoría> categorías = nombresCategoría.stream()
                .map(nombre -> categorizador.obtenerCategoría(nombre)
                        .orElseThrow(() -> new CategoríaInexistenteException(nombre)))
                .collect(Collectors.toSet());
        return new Presupuesto(documentos, ítems, proveedor, categorías);
    }

    public void setCategorizador(final Categorizador categorizador) {
        this.categorizador = categorizador;
    }
}
