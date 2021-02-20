package com.gesoc.modelo.organización;

import com.gesoc.modelo.categorización.Categorizador;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.operaciones.Ingreso;
import com.gesoc.morphia.Cambios;
import com.gesoc.morphia.Ejecucion;
import com.gesoc.utils.Const;

import spark.Request;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    // TODO: egresos por entidad
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Egreso> egresos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingreso> ingresos = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Categorizador categorizador = new Categorizador();
    private int cantidadPresupuestosRequeridos;

    public Organizacion(final String nombre, final int cantidadPresupuestosRequeridos) {
        this.nombre = nombre;
        this.cantidadPresupuestosRequeridos = cantidadPresupuestosRequeridos;
    }

    public void agregarEgreso(final Egreso egreso) {
        egreso.setOrganizaciónId(id);
        egreso.setCantidadPresupuestosRequeridos(cantidadPresupuestosRequeridos);
        egresos.add(egreso);
    }

    public void agregarIngreso(final Ingreso ingreso) {
        ingreso.setOrganizaciónId(id);
        ingresos.add(ingreso);
    }

    public void agregarOActualizarEgreso(final Egreso egreso,Request request) {
        if (egresos.contains(egreso)) {
            borrarEgreso(egreso.getId());
            Cambios cambios = new Cambios();
    		cambios.cargarEnMongo(request.session().attribute(Const.ID_USUARIO), "Egresos", Ejecucion.MODIFICACION,egreso.getId());
        }
        agregarEgreso(egreso);
        Cambios cambios = new Cambios();
		cambios.cargarEnMongo(request.session().attribute(Const.ID_USUARIO), "Egresos", Ejecucion.ALTA,egreso.getId());
        
    }

    public void borrarEgreso(Long idEgreso) {
        egresos.removeIf(egreso -> egreso.getId().equals(idEgreso));
    }

    public Categorizador getCategorizador() {
        return categorizador;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public int getCantidadPresupuestosRequeridos() {
        return cantidadPresupuestosRequeridos;
    }

    public Long getId() {
        return id;
    }

    public Organizacion() {
    }
}
