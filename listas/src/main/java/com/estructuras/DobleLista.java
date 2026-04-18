package com.estructuras;

public class DobleLista<T> {
    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola;
    private int tamanio;

    public DobleLista() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public void agregarInicio(T valor) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(valor);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        }
        tamanio++;
    }

    public void agregarFinal(T valor) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(valor);
        if (cola == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
        tamanio++;
    }

    public T removerFinal() {
        if (cola == null) {
            throw new IllegalStateException("La lista está vacía");
        }
        T dato = cola.getDato();
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        }
        tamanio--;
        return dato;
    }

    public int size() {
        return tamanio;
    }

    public String imprimir() {
        if (cabeza == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        NodoDoble<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(" <-> ");
            }
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }
}
