package com.gesoc.modelo.usuarios;

import com.gesoc.modelo.organización.Organizacion;

import javax.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String password;
    @OneToOne
    private Organizacion organización;

    public Usuario(final String nombre, final String password, final Organizacion organización) {
        this.nombre = nombre;
        this.password = password;
        this.organización = organización;
    }

    public Organizacion getOrganización() {
        return organización;
    }
    public Usuario() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    // TODO: manejo seguro de contraseñas
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

}
