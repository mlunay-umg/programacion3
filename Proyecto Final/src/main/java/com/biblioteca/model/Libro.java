package com.biblioteca.model;

import java.util.Objects;

public class Libro {
    private long codigo;
    private String isbn;
    private String titulo;
    private String autor;
    private Categoria categoria;
    private boolean disponible;

    public Libro(long codigo, String isbn, String titulo, String autor, Categoria categoria) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo no puede ser vacio");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria no puede ser nula");
        }
        this.codigo = codigo;
        this.isbn = isbn != null ? isbn.trim() : "";
        this.titulo = titulo.trim();
        this.autor = autor != null ? autor.trim() : "";
        this.categoria = categoria;
        this.disponible = true;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Libro)) {
            return false;
        }
        return codigo == ((Libro) o).codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(codigo).append("] ");
        sb.append(titulo);
        sb.append(" | ISBN: ").append(isbn);
        sb.append(" | Cat: ").append(categoria.getNombre());
        sb.append(" | ").append(disponible ? "Disponible" : "No disponible");
        return sb.toString();
    }
}
