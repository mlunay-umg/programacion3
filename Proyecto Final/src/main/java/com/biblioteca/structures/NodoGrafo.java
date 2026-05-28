package com.biblioteca.structures;

public class NodoGrafo<T> {
    private T valor;
    private NodoVecino<T> primerVecino;
    private NodoGrafo<T> siguienteEnTabla;

    public NodoGrafo(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public NodoVecino<T> getPrimerVecino() {
        return primerVecino;
    }

    public void setPrimerVecino(NodoVecino<T> primerVecino) {
        this.primerVecino = primerVecino;
    }

    public NodoGrafo<T> getSiguienteEnTabla() {
        return siguienteEnTabla;
    }

    public void setSiguienteEnTabla(NodoGrafo<T> siguienteEnTabla) {
        this.siguienteEnTabla = siguienteEnTabla;
    }
}
