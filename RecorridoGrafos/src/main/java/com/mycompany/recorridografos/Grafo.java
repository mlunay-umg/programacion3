/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.recorridografos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
/**
 *
 * @author umg
 */
public class Grafo {
    private Map<String, List<String>> adyacencia;
    public Grafo()
    {
        adyacencia= new HashMap<>();
    }
    //Agregar nodo
    public void agregarNodo (String nodo)
    {
        adyacencia.putIfAbsent(nodo, new ArrayList<>());
    }
    //agregar arista
    public void agregarArsita(String origen, String destino)
    {
        agregarNodo(origen);
        agregarNodo(destino);
        adyacencia.get(origen).add(destino);
        adyacencia.get(destino).add(origen);
    }
    //imprimir 
    public void imprimir()
    {
        for (String nodo : adyacencia.keySet() )
        {
            System.out.println(nodo + " -> " + adyacencia.get(nodo));
        }
    }
    //recorrido BFS
    public void bfs(String inicio)
    {
        //validar si el nodo inicial existe 
        if(!adyacencia.containsKey(inicio))
        {
            System.out.println("El nodo inicial no existe");
            return;
        }
        //se crea visistados
        Set<String> visitado= new HashSet<>();
        //Crear Cola
        Queue<String> cola= new LinkedList<>();
        
        //marcar el nodo incial como visitado
        visitado.add(inicio);
        //agregar el nodo inicial a la cola
        cola.add(inicio);
        //recorremos la lista
        //mientras queda pendiente en la cola
        while (!cola.isEmpty())
        {
            //Extraer
            String actual=cola.poll();
            System.out.println(actual + " ");
            //recoremos vecinos 
            for(String vecino : adyacencia.get(actual))
            {
                //comprobamos que no se haya visitado
                if (!visitado.contains(vecino))
                {
                    //lo agregamos a visitado
                    visitado.add(vecino);
                    //lo agregamos a la cola
                    cola.add(vecino);
                }
            }
            
        }
        
    }
    
    //recorrido DFS recursivo
    public void dfs(String inicio)
    {
        //validar si el nodo inicial exsite
        if(!adyacencia.containsKey(inicio))
        {
            System.out.println("El nodo incinal no existe");
            return;
        }
        //crear el arreglo de visitado
        Set<String> visitado=new HashSet<>();
        
        //Llammos al recursivo
        dfsRecursivo(inicio,visitado);
        
        
    }
    // recursivo
    private void dfsRecursivo(String actual, Set<String> visitado)
    {
        //marcar el actual como visitado
        visitado.add(actual);
        //Procesamos el actual
        System.out.print(actual + " ");
        //recorremos los vecinos
        for (String vecino : adyacencia.get(actual))
        {
            //comprobar que no se haya visitado
            if(!visitado.contains(vecino))
            {
                //continuamos explorando 
                dfsRecursivo(vecino,visitado);
            }
        }
    }
    //DFS con pila
    public void dfsPila(String inicio)
    {
        //validamos si el nodo inicial existe 
         if(!adyacencia.containsKey(inicio))
        {
            System.out.println("El nodo incinal no existe");
            return;
        }
         //creamos visitado
         Set<String> visitado=new HashSet<>();
         
         //creamos la pila
         Stack<String> pila= new Stack<>();
         //agregar el nodo inicial a la pila
         while(!pila.isEmpty())
         {
             //Extraer el ultimo nodo agreado a la pila
             String actual=pila.pop();
             //comprobamos si ya lo visitamos antes
             if (!visitado.contains(actual))
             {
                 //marcamos como visitado
                 visitado.add(actual);
                 System.out.print(actual + " ");
                 
                 //agregamos vecinos a la pila
                 for (String vecino : adyacencia.get(actual))
                 {
                     // solo vamos agregar los no visitados
                     if (!visitado.contains(vecino))
                     {
                         pila.push(vecino);
                     }
                 }
             }
         }
    }
}
