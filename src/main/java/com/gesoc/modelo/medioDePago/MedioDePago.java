package com.gesoc.modelo.medioDePago;

import com.gesoc.modelo.Site;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MedioDePago {

    public MedioDePago() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String identificador;
    private String tipoDePago;
    private String descripcion;
    private Site site;

    public MedioDePago(String tipoPago, String identificador) {
        this.tipoDePago = tipoDePago;
        this.identificador = identificador;
        this.site = Site.MLA;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoDeMedioDePago() {
        return tipoDePago;
    }

    public void setTipoDeMedioDePago(String tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
