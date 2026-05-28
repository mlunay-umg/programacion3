package com.biblioteca.structures;

import com.biblioteca.model.Libro;

public class ArbolBST {

    private NodoBST raiz;
    private int size;

    public void insertar(String codigo, Libro valor) {
        raiz = insertarRec(raiz, codigo, valor);
    }

    private NodoBST insertarRec(NodoBST nodo, String codigo, Libro valor) {
        if (nodo == null) {
            size++;
            return new NodoBST(codigo, valor);
        }
        int cmp = codigo.compareTo(nodo.getCodigo());
        if (cmp < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), codigo, valor));
        } else if (cmp > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), codigo, valor));
        } else {
            nodo.setValor(valor);
        }
        return nodo;
    }

    public Libro buscar(String codigo) {
        NodoBST n = raiz;
        while (n != null) {
            int cmp = codigo.compareTo(n.getCodigo());
            if (cmp < 0) {
                n = n.getIzquierdo();
            } else if (cmp > 0) {
                n = n.getDerecho();
            } else {
                return n.getValor();
            }
        }
        return null;
    }

    public void eliminar(String codigo) {
        raiz = eliminarRecursivo(raiz, codigo);
    }

    private NodoBST eliminarRecursivo(NodoBST nodo, String codigo) {
        if (nodo == null) {
            return null;
        }
        int cmp = codigo.compareTo(nodo.getCodigo());
        if (cmp < 0) {
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), codigo));
        } else if (cmp > 0) {
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), codigo));
        } else {
            size--;
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            NodoBST sucesor = minimo(nodo.getDerecho());
            NodoBST nuevoNodo = new NodoBST(sucesor.getCodigo(), sucesor.getValor());
            nuevoNodo.setIzquierdo(nodo.getIzquierdo());
            nuevoNodo.setDerecho(eliminarMin(nodo.getDerecho()));
            size++;
            return nuevoNodo;
        }
        return nodo;
    }

    private NodoBST minimo(NodoBST nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

    private NodoBST eliminarMin(NodoBST nodo) {
        if (nodo.getIzquierdo() == null) {
            size--;
            return nodo.getDerecho();
        }
        nodo.setIzquierdo(eliminarMin(nodo.getIzquierdo()));
        return nodo;
    }

    public ListaEnlazada<Libro> enOrden() {
        ListaEnlazada<Libro> lista = new ListaEnlazada<>();
        enOrdenRec(raiz, lista);
        return lista;
    }

    private void enOrdenRec(NodoBST nodo, ListaEnlazada<Libro> lista) {
        if (nodo == null) {
            return;
        }
        enOrdenRec(nodo.getIzquierdo(), lista);
        lista.agregar(nodo.getValor());
        enOrdenRec(nodo.getDerecho(), lista);
    }
}
