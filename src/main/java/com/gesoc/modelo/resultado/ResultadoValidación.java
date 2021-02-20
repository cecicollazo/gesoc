package com.gesoc.modelo.resultado;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gesoc.modelo.Presupuesto;
import com.gesoc.modelo.Proveedor;
import com.gesoc.modelo.operaciones.DocumentoComercial;
import com.gesoc.modelo.operaciones.Egreso;
import com.gesoc.modelo.organización.Organizacion;
import com.google.gson.annotations.Expose;

import dev.morphia.annotations.Id;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class ResultadoValidación {
   
    @Expose
    private Long id;
    @Expose
    private Integer Code;
    private String nombreOrganizacion;
    @Expose
    private Long idCompra;
    @Expose
    private LocalDate fechaHoy;
    private Presupuesto presupuestoelegidoMenorCosto;
    private List<Presupuesto> presupuestos;
    @Expose
    private String Mensaje;
    private String detalleCantidadPresupuesto;
    @Expose
    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;
    private Boolean requierePresupuesto; 
    private Organizacion org;
    @Expose
	public int CantidadPresupuesto;


    public String validarCantidadPresupuesto(List<Presupuesto> list) {

        Boolean validar = ResultadoValidaciónCantidadPresupuestos.verificarCantidad(list);
        if (validar) {
        	Mensaje = "La cantidad de presupuestos es optima";
            return Mensaje;
        } else {
        	Mensaje = "Supera el limite maximo presupuestos permitidos(3)";
            return Mensaje;
        }

    }

    public Presupuesto presupuestoMenorCosto(List<Presupuesto> set) {
        return ResultadoValidaciónSelecciónPresupuesto.menorCosto(set);
       
    }

    public LocalDate fechaHoy() {
        fechaHoy = LocalDate.now();
        return fechaHoy;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getError() {
        return Mensaje;
    }

    public void setError(String error) {
    	Mensaje = error;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public DocumentoComercial getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public Boolean getRequierePresupuesto() {
        return requierePresupuesto;
    }

    public void setRequierePresupuesto(Boolean requierePresupuesto) {
        this.requierePresupuesto = requierePresupuesto;
    }


	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public Presupuesto getPresupuestoelegidoMenorCosto() {
		return presupuestoelegidoMenorCosto;
	}

	public void setPresupuestoelegidoMenorCosto(Presupuesto presupuestoelegidoMenorCosto) {
		this.presupuestoelegidoMenorCosto = presupuestoelegidoMenorCosto;
	}

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void setPresupuestos(List<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}

	
		
	

}
