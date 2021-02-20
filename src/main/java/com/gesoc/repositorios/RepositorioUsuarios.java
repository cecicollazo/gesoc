package com.gesoc.repositorios;

import com.gesoc.modelo.usuarios.Usuario;

import java.util.Optional;

public class RepositorioUsuarios extends Repositorio<Usuario> {

    public RepositorioUsuarios() {
        super(Usuario.class);
    }

    public Optional<Usuario> obtenerPorNombre(final String nombre) {
        return obtenerPor(usuario -> usuario.getNombre().equals(nombre));
    }
}
