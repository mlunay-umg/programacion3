package com.biblioteca.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Prestamo {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private long id;
    private String carnetUsuario;
    private long codigoLibro;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;

    public Prestamo(long id, String carnetUsuario, long codigoLibro) {
        this.id = id;
        this.carnetUsuario = carnetUsuario;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = LocalDateTime.now();
        this.fechaDevolucion = null;
    }

    public long getId() {
        return id;
    }

    public String getCarnetUsuario() {
        return carnetUsuario;
    }

    public long getCodigoLibro() {
        return codigoLibro;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        if (fechaDevolucion != null && fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("Fecha de devolucion no puede ser anterior al prestamo");
        }
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isActivo() {
        return fechaDevolucion == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prestamo#").append(id);
        sb.append(" | Libro:").append(codigoLibro);
        sb.append(" | Usuario:").append(carnetUsuario);
        sb.append(" | Desde:").append(fechaPrestamo.format(formatter));
        if (fechaDevolucion != null) {
            sb.append(" | Devuelto:").append(fechaDevolucion.format(formatter));
        } else {
            sb.append(" | ACTIVO");
        }
        return sb.toString();
    }
}
