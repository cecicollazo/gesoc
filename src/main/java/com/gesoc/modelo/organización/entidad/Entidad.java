package com.gesoc.modelo.organización.entidad;

import com.gesoc.modelo.operaciones.Egreso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public abstract class Entidad {

	private Integer ID;

    private Set<Egreso> egresos = new HashSet<>();

    public Set<Egreso> egresos() {
        return new HashSet<>(egresos);
    }

    public void agregarEgreso(Egreso egreso) {
        egresos.add(egreso);
    }
}
