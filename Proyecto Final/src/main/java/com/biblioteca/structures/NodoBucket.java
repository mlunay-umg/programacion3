package com.biblioteca.structures;

public class NodoBucket<K, V> {
    private K clave;
    private V valor;
    private NodoBucket<K, V> siguiente;

    public NodoBucket(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public NodoBucket<K, V> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoBucket<K, V> siguiente) {
        this.siguiente = siguiente;
    }
}
