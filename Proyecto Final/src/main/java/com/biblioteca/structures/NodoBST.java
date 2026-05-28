package com.biblioteca.structures;

import com.biblioteca.model.Libro;

public class NodoBST {
    private String codigo;
    private Libro valor;
    private NodoBST izquierdo;
    private NodoBST derecho;

    public NodoBST(String codigo, Libro valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public Libro getValor() {
        return valor;
    }

    public void setValor(Libro valor) {
        this.valor = valor;
    }

    public NodoBST getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoBST izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoBST getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoBST derecho) {
        this.derecho = derecho;
    }
}
