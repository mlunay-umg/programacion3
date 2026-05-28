package com.biblioteca.structures;

public class ArbolAVL<K extends Comparable<K>, V> {

    private NodoAVL<K, V> raiz;
    private int size;

    private int altura(NodoAVL<K, V> n) {
        return n == null ? 0 : n.getAltura();
    }

    public int factorBalance(NodoAVL<K, V> n) {
        return n == null ? 0 : altura(n.getIzquierdo()) - altura(n.getDerecho());
    }

    private void actualizarAltura(NodoAVL<K, V> n) {
        n.setAltura(1 + Math.max(altura(n.getIzquierdo()), altura(n.getDerecho())));
    }

    private NodoAVL<K, V> rotarDerecha(NodoAVL<K, V> y) {
        NodoAVL<K, V> x = y.getIzquierdo();
        NodoAVL<K, V> T2 = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(T2);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private NodoAVL<K, V> rotarIzquierda(NodoAVL<K, V> x) {
        NodoAVL<K, V> y = x.getDerecho();
        NodoAVL<K, V> T2 = y.getIzquierdo();
        y.setIzquierdo(x);
        x.setDerecho(T2);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private NodoAVL<K, V> balancear(NodoAVL<K, V> n) {
        actualizarAltura(n);
        int fb = factorBalance(n);

        // LL
        if (fb > 1 && factorBalance(n.getIzquierdo()) >= 0) {
            return rotarDerecha(n);
        }
        // LR
        if (fb > 1 && factorBalance(n.getIzquierdo()) < 0) {
            n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
            return rotarDerecha(n);
        }
        // RR
        if (fb < -1 && factorBalance(n.getDerecho()) <= 0) {
            return rotarIzquierda(n);
        }
        // RL
        if (fb < -1 && factorBalance(n.getDerecho()) > 0) {
            n.setDerecho(rotarDerecha(n.getDerecho()));
            return rotarIzquierda(n);
        }
        return n;
    }

    public void insertar(K clave, V valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }

    private NodoAVL<K, V> insertarRecursivo(NodoAVL<K, V> nodo, K clave, V valor) {
        if (nodo == null) {
            size++;
            return new NodoAVL<>(clave, valor);
        }
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), clave, valor));
        } else if (cmp > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), clave, valor));
        } else {
            nodo.setValor(valor);
            return nodo;
        }
        return balancear(nodo);
    }

    public void eliminar(K clave) {
        raiz = eliminarRec(raiz, clave);
    }

    private NodoAVL<K, V> eliminarRec(NodoAVL<K, V> nodo, K clave) {
        if (nodo == null) {
            return null;
        }
        int cmp = clave.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(eliminarRec(nodo.getIzquierdo(), clave));
        } else if (cmp > 0) {
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), clave));
        } else {
            size--;
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            NodoAVL<K, V> sucesor = minimo(nodo.getDerecho());
            NodoAVL<K, V> nuevo = new NodoAVL<>(sucesor.getClave(), sucesor.getValor());
            nuevo.setIzquierdo(nodo.getIzquierdo());
            nuevo.setDerecho(eliminarMin(nodo.getDerecho()));
            size++;
            return balancear(nuevo);
        }
        return balancear(nodo);
    }

    private NodoAVL<K, V> minimo(NodoAVL<K, V> n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n;
    }

    private NodoAVL<K, V> eliminarMin(NodoAVL<K, V> n) {
        if (n.getIzquierdo() == null) {
            size--;
            return n.getDerecho();
        }
        n.setIzquierdo(eliminarMin(n.getIzquierdo()));
        return balancear(n);
    }

    public ListaEnlazada<V> enOrden() {
        ListaEnlazada<V> lista = new ListaEnlazada<>();
        enOrdenRec(raiz, lista);
        return lista;
    }

    private void enOrdenRec(NodoAVL<K, V> n, ListaEnlazada<V> lista) {
        if (n == null) {
            return;
        }
        enOrdenRec(n.getIzquierdo(), lista);
        lista.agregar(n.getValor());
        enOrdenRec(n.getDerecho(), lista);
    }

}
