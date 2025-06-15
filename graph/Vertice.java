package graph;

import linkedlist.ListaEnlazada;

public class Vertice<E> {

    // ATRIBUTOS
    private E dato; //Representa la información del vértice
    protected ListaEnlazada<Arista<E>> listaAdyacencia; // Contiene todas las aristas salientes de este vértice 

    // CONSTRUCTOR
    public Vertice(E dato) { 
        this.dato = dato; // Se asigna el valor recibido al atributo 'dato'
        listaAdyacencia = new ListaEnlazada<>(); //Se inicializa a lista de adyacencia vacía
    }
    
    // Metodo para obtener el dato almacenado en el vertice
    public E getDato() {
    	return dato; // Retorna el valor almacenado en el atributo 'dato'
    }
    
    // Metodo para obtener la lista de adyacencia (las aristas conectadas a este vertice) 
    public ListaEnlazada<Arista<E>> getListaAdyacencia() {
        return listaAdyacencia; // Retorna la lista de aristas que representan las conexiones de este vertice
    }
}
