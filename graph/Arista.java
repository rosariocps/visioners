package graph;

public class Arista<E> {

    // Atributos
    private Vertice<E> referenciaDestino; // Vértice de destino de la arista
    private double peso; // Peso de la arista 

    // Constructor
    public Arista(Vertice<E> referenciaDestino, double peso) {
        this.referenciaDestino = referenciaDestino; // Asigna el parámetro al atributo del destino
        this.peso = peso; // Asigna el parámetro al atributo del peso
    }

    // Devuelve el vertice de destino de la arista
    public Vertice<E> getReferenciaDestino(){
        return referenciaDestino;
    }

    
}

