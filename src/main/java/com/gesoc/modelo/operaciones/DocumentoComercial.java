package com.gesoc.modelo.operaciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DocumentoComercial {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private TipoDeDocumentoComercial tipo;
    private String numero;
    private String pathAlArchivo;
    private String pathAlLink;

    public DocumentoComercial() {
    }

    public DocumentoComercial(TipoDeDocumentoComercial tipoDeDocumento, String numero, String pathAlArchivo,
                              String pathAlLink) {
        this.tipo = tipoDeDocumento;
        this.numero = numero;
        this.pathAlArchivo = pathAlArchivo;
        this.pathAlLink = pathAlLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipo(TipoDeDocumentoComercial tipo) {
        this.tipo = tipo;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPathAlArchivo(String pathAlArchivo) {
        this.pathAlArchivo = pathAlArchivo;
    }

    public void setPathAlLink(String pathAlLink) {
        this.pathAlLink = pathAlLink;
    }

    public TipoDeDocumentoComercial getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public String getPathAlArchivo() {
        return pathAlArchivo;
    }

    public String getPathAlLink() {
        return pathAlLink;
    }
}
