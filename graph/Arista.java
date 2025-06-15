package graph;

public class Arista<E> {

    // Atributos
    private Vertice<E> referenciaDestino; // Vértice de destino de la arista
    private double peso; // Peso de la arista 

    // Constructor
    public Arista(Vertice<E> referenciaDestino, double peso) {
        this.referenciaDestino = referenciaDestino; // Asigna el parámetro al atributo de la referencia del destino
        this.peso = peso; // Asigna el parámetro al atributo del peso
    }

    // GETTERS    
    public Vertice<E> getReferenciaDestino(){ // Devuelve el vertice de destino de la arista
        return referenciaDestino;
    }

    public double getPeso(){ //Devuelve el peso de la arista
        return peso;
    }

}

