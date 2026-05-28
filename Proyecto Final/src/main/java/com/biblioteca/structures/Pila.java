package com.biblioteca.structures;

public class Pila<T> {

    private NodoComun<T> cima;
    private int size;
    private final int capacidadMax;

    public Pila(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public NodoComun<T> getCima() {
        return cima;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public void apilar(T valor) {
        NodoComun<T> nodo = new NodoComun<>(valor);
        nodo.setSiguiente(cima);
        cima = nodo;
        size++;
        if (capacidadMax > 0 && size > capacidadMax) {
            // descarta el elemento del fondo
            if (cima.getSiguiente() == null) {
                cima = null;
                size = 0;
                return;
            }
            NodoComun<T> prev = cima;
            while (prev.getSiguiente().getSiguiente() != null) {
                prev = prev.getSiguiente();
            }
            prev.setSiguiente(null);
            size--;
        }
    }
}
