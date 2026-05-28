package com.biblioteca.structures;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class MapaHash<K, V> {

    private static final int CAPACIDAD_INICIAL = 16;
    private static final float FACTOR_CARGA_MAX = 0.75f;

    private NodoBucket<K, V>[] tabla;
    private int size;
    private int capacidad;

    public MapaHash() {
        capacidad = CAPACIDAD_INICIAL;
        tabla = new NodoBucket[capacidad];
    }

    public int getSize() {
        return size;
    }

    private int hash(K clave) {
        int h = Objects.hashCode(clave);
        h ^= (h >>> 16);
        return h & (capacidad - 1);
    }

    public void poner(K clave, V valor) {
        if ((float) size / capacidad >= FACTOR_CARGA_MAX) {
            rehash();
        }
        int idx = hash(clave);
        NodoBucket<K, V> n = tabla[idx];
        while (n != null) {
            if (Objects.equals(n.getClave(), clave)) {
                n.setValor(valor);
                return;
            }
            n = n.getSiguiente();
        }
        NodoBucket<K, V> nuevo = new NodoBucket<>(clave, valor);
        nuevo.setSiguiente(tabla[idx]);
        tabla[idx] = nuevo;
        size++;
    }

    public V obtener(K clave) {
        int idx = hash(clave);
        NodoBucket<K, V> n = tabla[idx];
        while (n != null) {
            if (Objects.equals(n.getClave(), clave)) {
                return n.getValor();
            }
            n = n.getSiguiente();
        }
        return null;
    }

    public boolean contiene(K clave) {
        return obtener(clave) != null;
    }

    public boolean eliminar(K clave) {
        int idx = hash(clave);
        if (tabla[idx] == null) {
            return false;
        }
        if (Objects.equals(tabla[idx].getClave(), clave)) {
            tabla[idx] = tabla[idx].getSiguiente();
            size--;
            return true;
        }
        NodoBucket<K, V> prev = tabla[idx];
        NodoBucket<K, V> curr = prev.getSiguiente();
        while (curr != null) {
            if (Objects.equals(curr.getClave(), clave)) {
                prev.setSiguiente(curr.getSiguiente());
                size--;
                return true;
            }
            prev = curr;
            curr = curr.getSiguiente();
        }
        return false;
    }

    public ListaEnlazada<V> valores() {
        ListaEnlazada<V> lista = new ListaEnlazada<>();
        for (int i = 0; i < capacidad; i++) {
            NodoBucket<K, V> n = tabla[i];
            while (n != null) {
                lista.agregar(n.getValor());
                n = n.getSiguiente();
            }
        }
        return lista;
    }

    public ListaEnlazada<K> claves() {
        ListaEnlazada<K> lista = new ListaEnlazada<>();
        for (int i = 0; i < capacidad; i++) {
            NodoBucket<K, V> n = tabla[i];
            while (n != null) {
                lista.agregar(n.getClave());
                n = n.getSiguiente();
            }
        }
        return lista;
    }

    private void rehash() {
        int nuevaCapacidad = capacidad * 2;
        NodoBucket<K, V>[] nuevaTabla = new NodoBucket[nuevaCapacidad];
        for (int i = 0; i < capacidad; i++) {
            NodoBucket<K, V> n = tabla[i];
            while (n != null) {
                NodoBucket<K, V> siguiente = n.getSiguiente();
                int h = Objects.hashCode(n.getClave());
                h ^= (h >>> 16);
                int idx = h & (nuevaCapacidad - 1);
                n.setSiguiente(nuevaTabla[idx]);
                nuevaTabla[idx] = n;
                n = siguiente;
            }
        }
        tabla = nuevaTabla;
        capacidad = nuevaCapacidad;
    }
}
