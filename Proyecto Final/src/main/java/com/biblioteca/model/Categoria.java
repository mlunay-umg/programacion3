package com.biblioteca.model;

import java.util.Objects;

public class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Categoria no puede ser vacia");
        }
        this.nombre = nombre.trim().toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return nombre.equals(((Categoria) o).nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
