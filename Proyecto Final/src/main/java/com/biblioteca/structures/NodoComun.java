package com.biblioteca.structures;

public class NodoComun<T> {
    private T valor;
    private NodoComun<T> siguiente;

    public NodoComun(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public NodoComun<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoComun<T> siguiente) {
        this.siguiente = siguiente;
    }
}
