package com.biblioteca.structures;

public class ListaEnlazada<T> {

    private NodoComun<T> cabeza;
    private NodoComun<T> cola;
    private int size;

    public NodoComun<T> getCabeza() {
        return cabeza;
    }

    public int getSize() {
        return size;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void agregar(T valor) {
        NodoComun<T> nodo = new NodoComun<>(valor);
        if (cola == null) {
            cabeza = nodo;
            cola = nodo;
        } else {
            cola.setSiguiente(nodo);
            cola = nodo;
        }
        size++;
    }

    public boolean eliminar(T valor) {
        if (cabeza == null) {
            return false;
        }
        if (equals(cabeza.getValor(), valor)) {
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) {
                cola = null;
            }
            size--;
            return true;
        }
        NodoComun<T> prev = cabeza;
        NodoComun<T> curr = cabeza.getSiguiente();
        while (curr != null) {
            if (equals(curr.getValor(), valor)) {
                prev.setSiguiente(curr.getSiguiente());
                if (curr == cola) {
                    cola = prev;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.getSiguiente();
        }
        return false;
    }

    private boolean equals(T a, T b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        NodoComun<T> n = cabeza;
        while (n != null) {
            sb.append(n.getValor());
            if (n.getSiguiente() != null) {
                sb.append(", ");
            }
            n = n.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }
}
