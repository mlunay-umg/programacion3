package com.biblioteca.structures;

public class NodoVecino<T> {
    private T destino;
    private NodoVecino<T> siguiente;

    public NodoVecino(T destino) {
        this.destino = destino;
    }

    public T getDestino() {
        return destino;
    }

    public NodoVecino<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoVecino<T> siguiente) {
        this.siguiente = siguiente;
    }
}
