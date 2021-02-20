package com.gesoc.services;

import com.gesoc.modelo.vinculador.Vinculador;
import com.gesoc.modelo.vinculador.balances.Balance;
import com.gesoc.modelo.vinculador.condiciones.CondicionVinculacion;
import com.gesoc.modelo.vinculador.criterios.Criterio;
import com.gesoc.repositorios.RepositorioEgresos;
import com.gesoc.repositorios.RepositorioIngresos;

import java.util.HashSet;
import java.util.Set;

public class VinculadorService {

    private static final RepositorioEgresos REPOSITORIO_EGRESOS = new RepositorioEgresos();
    private static final RepositorioIngresos REPOSITORIO_INGRESOS = new RepositorioIngresos();
    private Vinculador vinculador;
    private Long idOrganizacion;

    public VinculadorService(String criterio, Long idOrganizacion) {
        Criterio criterioEnum;
        try{
            criterioEnum = Criterio.valueOf(criterio.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("No es un criterio valido");
        }
        this.vinculador = new Vinculador(criterioEnum);
        this.idOrganizacion = idOrganizacion;
    }

    public Set<? extends Balance<?, ?>> vincular(Set<CondicionVinculacion> condiciones) {
        return vinculador.vincular(new HashSet<>(REPOSITORIO_INGRESOS.obtenerPorOrganización(idOrganizacion)),
                new HashSet<>(REPOSITORIO_EGRESOS.obtenerPorOrganización(idOrganizacion)), condiciones);
    }

}
