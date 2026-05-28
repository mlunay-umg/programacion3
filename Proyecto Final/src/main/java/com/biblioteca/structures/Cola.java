package com.biblioteca.structures;

import java.util.NoSuchElementException;

public class Cola<T> {

    private NodoComun<T> frente;
    private NodoComun<T> ultimo;
    private int size;

    public NodoComun<T> getFrente() {
        return frente;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void encolar(T valor) {
        NodoComun<T> nodo = new NodoComun<>(valor);
        if (ultimo == null) {
            frente = nodo;
            ultimo = nodo;
        } else {
            ultimo.setSiguiente(nodo);
            ultimo = nodo;
        }
        size++;
    }

    public T desencolar() {
        if (frente == null) {
            throw new NoSuchElementException("Cola vacia");
        }
        T valor = frente.getValor();
        frente = frente.getSiguiente();
        if (frente == null) {
            ultimo = null;
        }
        size--;
        return valor;
    }
}
