package graph;

import linkedlist.ListaEnlazada;

public class Vertice<E> {

    // Atributos
    private E dato; //Representa la información del vértice
    protected ListaEnlazada<Arista<E>> listaAdyacencia; // Contiene todas las aristas salientes de este vértice 
}
