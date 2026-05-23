/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.recorridografos;

/**
 *
 * @author umg
 */
public class RecorridoGrafos {

    public static void main(String[] args) {
        Grafo grafo=new Grafo();
        grafo.agregarArsita("A", "B");
        grafo.agregarArsita("A", "C");
        grafo.agregarArsita("B", "D");
        grafo.agregarArsita("B", "E");
        grafo.agregarArsita("C", "F");
        grafo.agregarArsita("D", "G");
        grafo.agregarArsita("E", "G");
        grafo.agregarArsita("F", "H");
        grafo.agregarArsita("G", "H");
        
        //mostramos lista de adyacencia
        System.out.println("Lista de adyacencia");
        grafo.imprimir();
        
         //BFS desde A
        System.out.println("\nRecorrido BFS desde A:");
        grafo.bfs("A");
        
        //DFS recursivo desde A
        System.out.println("\nRecorrido DFS recursivo desde A:");
        grafo.dfs("A");
        
          //DFS con pila desde A
        System.out.println("\nRecorrido DFS con pila desde A:");
        grafo.dfsPila("A");
    }
}
