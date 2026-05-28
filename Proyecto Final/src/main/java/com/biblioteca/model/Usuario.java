package com.biblioteca.model;

import com.biblioteca.structures.ListaEnlazada;
import java.util.Objects;

public class Usuario {
    private String carnet;
    private String nombre;
    private ListaEnlazada<Long> prestamosActivos;

    public Usuario(String carnet, String nombre) {
        if (carnet == null || carnet.isBlank()) {
            throw new IllegalArgumentException("Carnet no puede ser vacio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre no puede ser vacio");
        }
        this.carnet = carnet.trim();
        this.nombre = nombre.trim();
        this.prestamosActivos = new ListaEnlazada<>();
    }

    public ListaEnlazada<Long> getPrestamosActivos() {
        return prestamosActivos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return carnet.equals(((Usuario) o).carnet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carnet);
    }

    @Override
    public String toString() {
        return "[" + carnet + "] " + nombre + " (prestamos activos: " + prestamosActivos.getSize() + ")";
    }
}
