package com.biblioteca.structures;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class Grafo<T> {

    private static final int CAPACIDAD_INICIAL = 16;

    private NodoGrafo<T>[] tabla;
    private int size;
    private int capacidad;

    public Grafo() {
        capacidad = CAPACIDAD_INICIAL;
        tabla = new NodoGrafo[capacidad];
    }

    public NodoGrafo<T>[] getTabla() {
        return tabla;
    }

    public int getCapacidad() {
        return capacidad;
    }

    private int hash(T valor) {
        int h = Objects.hashCode(valor);
        h ^= (h >>> 16);
        return Math.abs(h) % capacidad;
    }

    public NodoGrafo<T> buscarNodo(T valor) {
        int idx = hash(valor);
        NodoGrafo<T> n = tabla[idx];
        while (n != null) {
            if (Objects.equals(n.getValor(), valor)) {
                return n;
            }
            n = n.getSiguienteEnTabla();
        }
        return null;
    }

    public void agregarNodo(T valor) {
        if (buscarNodo(valor) != null) {
            return;
        }
        int idx = hash(valor);
        NodoGrafo<T> nuevo = new NodoGrafo<>(valor);
        nuevo.setSiguienteEnTabla(tabla[idx]);
        tabla[idx] = nuevo;
        size++;
    }

    public void agregarArista(T a, T b) {
        agregarNodo(a);
        agregarNodo(b);
        NodoGrafo<T> na = buscarNodo(a);
        NodoGrafo<T> nb = buscarNodo(b);
        if (!tieneArista(na, b)) {
            NodoVecino<T> v = new NodoVecino<>(b);
            v.setSiguiente(na.getPrimerVecino());
            na.setPrimerVecino(v);
        }
        if (!tieneArista(nb, a)) {
            NodoVecino<T> v = new NodoVecino<>(a);
            v.setSiguiente(nb.getPrimerVecino());
            nb.setPrimerVecino(v);
        }
    }

    private boolean tieneArista(NodoGrafo<T> nodo, T destino) {
        NodoVecino<T> v = nodo.getPrimerVecino();
        while (v != null) {
            if (Objects.equals(v.getDestino(), destino)) {
                return true;
            }
            v = v.getSiguiente();
        }
        return false;
    }

    public void eliminarNodo(T valor) {
        for (int i = 0; i < capacidad; i++) {
            NodoGrafo<T> ng = tabla[i];
            while (ng != null) {
                NodoVecino<T> prev = null;
                NodoVecino<T> v = ng.getPrimerVecino();
                while (v != null) {
                    if (Objects.equals(v.getDestino(), valor)) {
                        if (prev == null) {
                            ng.setPrimerVecino(v.getSiguiente());
                        } else {
                            prev.setSiguiente(v.getSiguiente());
                        }
                    } else {
                        prev = v;
                    }
                    v = v.getSiguiente();
                }
                ng = ng.getSiguienteEnTabla();
            }
        }
        int idx = hash(valor);
        if (tabla[idx] == null) {
            return;
        }
        if (Objects.equals(tabla[idx].getValor(), valor)) {
            tabla[idx] = tabla[idx].getSiguienteEnTabla();
            size--;
            return;
        }
        NodoGrafo<T> prev = tabla[idx];
        NodoGrafo<T> curr = prev.getSiguienteEnTabla();
        while (curr != null) {
            if (Objects.equals(curr.getValor(), valor)) {
                prev.setSiguienteEnTabla(curr.getSiguienteEnTabla());
                size--;
                return;
            }
            prev = curr;
            curr = curr.getSiguienteEnTabla();
        }
    }

    public ListaEnlazada<T> vecinos(T valor) {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        NodoGrafo<T> ng = buscarNodo(valor);
        if (ng == null) {
            return lista;
        }
        NodoVecino<T> v = ng.getPrimerVecino();
        while (v != null) {
            lista.agregar(v.getDestino());
            v = v.getSiguiente();
        }
        return lista;
    }

    public ListaEnlazada<T> bfs(T inicio, int profundidadMax) {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        if (buscarNodo(inicio) == null) {
            return resultado;
        }

        MapaHash<T, Boolean> visitados = new MapaHash<>();
        Cola<T> cola = new Cola<>();
        MapaHash<T, Integer> profundidad = new MapaHash<>();

        visitados.poner(inicio, true);
        cola.encolar(inicio);
        profundidad.poner(inicio, 0);

        while (!cola.estaVacia()) {
            T actual = cola.desencolar();
            int prof = profundidad.obtener(actual);
            if (!Objects.equals(actual, inicio)) {
                resultado.agregar(actual);
            }
            if (prof >= profundidadMax) {
                continue;
            }
            NodoGrafo<T> ng = buscarNodo(actual);
            if (ng == null) {
                continue;
            }
            NodoVecino<T> v = ng.getPrimerVecino();
            while (v != null) {
                if (!visitados.contiene(v.getDestino())) {
                    visitados.poner(v.getDestino(), true);
                    profundidad.poner(v.getDestino(), prof + 1);
                    cola.encolar(v.getDestino());
                }
                v = v.getSiguiente();
            }
        }
        return resultado;
    }

}
