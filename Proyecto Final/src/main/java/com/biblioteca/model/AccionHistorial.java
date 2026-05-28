package com.biblioteca.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccionHistorial {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LocalDateTime momento;
    private TipoAccionHistorial tipo;
    private String descripcion;

    public AccionHistorial(TipoAccionHistorial tipo, String descripcion) {
        this.momento = LocalDateTime.now();
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    public TipoAccionHistorial getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return momento.format(formatter) + " [" + tipo + "] " + descripcion;
    }
}
