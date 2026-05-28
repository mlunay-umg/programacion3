package com.biblioteca.structures;

public class NodoAVL<K, V> {
    private K clave;
    private V valor;
    private NodoAVL<K, V> izquierdo;
    private NodoAVL<K, V> derecho;
    private int altura;

    public NodoAVL(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.altura = 1;
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

    public NodoAVL<K, V> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVL<K, V> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVL<K, V> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL<K, V> derecho) {
        this.derecho = derecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
