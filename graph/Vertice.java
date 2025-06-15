package graph;

import linkedlist.ListaEnlazada;

public class Vertice<E> {

    // ATRIBUTOS
    private E dato; //Representa la información del vértice
    protected ListaEnlazada<Arista<E>> listaAdyacencia; // Contiene todas las aristas salientes de este vértice 

    // CONSTRUCTOR
    public Vertice(E dato) {
        this.dato = dato; // Se asigna el valor recibido al atributo 'dato'
        listaAdyacencia = new ListaEnlazada<>(); // Se inicializa la lista de adyacencia vacía
    }
    
}
