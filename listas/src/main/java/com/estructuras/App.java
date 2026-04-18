package com.estructuras;

public class App {
    public static void main(String[] args) {
        System.out.println("=== LISTA SIMPLE ===\n");
        testListaSimple();

        System.out.println("\n=== LISTA DOBLE ENLAZADA ===\n");
        testDobleLista();

        System.out.println("\n=== PILA (STACK) ===\n");
        testPila();
    }

    private static void testListaSimple() {
        ListaSimple<Integer> lista = new ListaSimple<>();

        System.out.println("Agregando al inicio: 10, luego 5");
        lista.agregarInicio(10);
        lista.agregarInicio(5);
        System.out.println("Lista: " + lista.imprimir() + ", size=" + lista.size());

        System.out.println("\nAgregando al final: 20");
        lista.agregarFinal(20);
        System.out.println("Lista: " + lista.imprimir());

        System.out.println("\nBuscando valores:");
        System.out.println("buscarValor(10): " + lista.buscarValor(10));
        System.out.println("buscarValor(99): " + lista.buscarValor(99));

        System.out.println("\nRemoviendo del inicio:");
        System.out.println("removerInicio() -> " + lista.removerInicio());
        System.out.println("Lista: " + lista.imprimir());

        System.out.println("\nRemoviendo dos veces más:");
        System.out.println("removerInicio() -> " + lista.removerInicio());
        System.out.println("removerInicio() -> " + lista.removerInicio());
        System.out.println("Lista: " + lista.imprimir() + ", size=" + lista.size());

        System.out.println("\nIntentando remover de lista vacía:");
        try {
            lista.removerInicio();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }

    private static void testDobleLista() {
        DobleLista<String> lista = new DobleLista<>();

        System.out.println("Agregando finales: A, B");
        lista.agregarFinal("A");
        lista.agregarFinal("B");

        System.out.println("Agregando al inicio: Z");
        lista.agregarInicio("Z");
        System.out.println("Lista: " + lista.imprimir() + ", size=" + lista.size());

        System.out.println("\nRemoviendo final:");
        System.out.println("removerFinal() -> " + lista.removerFinal());
        System.out.println("Lista: " + lista.imprimir());

        System.out.println("\nRemoviendo dos veces más:");
        System.out.println("removerFinal() -> " + lista.removerFinal());
        System.out.println("removerFinal() -> " + lista.removerFinal());
        System.out.println("Lista: " + lista.imprimir() + ", size=" + lista.size());

        System.out.println("\nIntentando remover de lista vacía:");
        try {
            lista.removerFinal();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }

    private static void testPila() {
        Pila<Integer> pila = new Pila<>();

        System.out.println("Haciendo push: 1, 2, 3");
        pila.push(1);
        pila.push(2);
        pila.push(3);

        System.out.println("Pila: " + pila.imprimir() + ", size=" + pila.size());

        System.out.println("\nPeek() -> " + pila.peek());
        System.out.println("Pila: " + pila.imprimir() + " (sin cambios)");

        System.out.println("\nHaciendo pop dos veces:");
        System.out.println("pop() -> " + pila.pop());
        System.out.println("pop() -> " + pila.pop());
        System.out.println("Pila: " + pila.imprimir() + ", size=" + pila.size());

        System.out.println("\nChequear isEmpty(): " + pila.isEmpty());

        System.out.println("\nHaciendo pop una vez más:");
        System.out.println("pop() -> " + pila.pop());
        System.out.println("Pila: " + pila.imprimir() + ", size=" + pila.size());

        System.out.println("\nChequear isEmpty(): " + pila.isEmpty());

        System.out.println("\nIntentando pop de pila vacía:");
        try {
            pila.pop();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }
}

