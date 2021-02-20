package com.gesoc.modelo;

import com.gesoc.enums.TipoDocumento;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombrePersona;
    private String numeroDocumento;
    private String direccionPostal;
    private TipoDocumento tipoDocumento;

    public Proveedor() {
    }

    public Proveedor(String nombrePersona, TipoDocumento tipoDocumento, String numeroDocumento,
                     String direccionPostal) {
        this.nombrePersona = nombrePersona;
        this.numeroDocumento = numeroDocumento;
        this.direccionPostal = direccionPostal;
        this.tipoDocumento = tipoDocumento;
    }

    public Long getId() {
        return id;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
