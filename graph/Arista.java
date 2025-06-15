package graph;

public class Arista<E> {

    // ATRIBUTOS
    private Vertice<E> referenciaDestino; // vértice de destino de la arista
    private double peso; // peso de la arista 

    // CONSTRUCTOR
    public Arista(Vertice<E> referenciaDestino, double peso) {
        this.referenciaDestino = referenciaDestino; // asigna el parámetro al atributo de la referencia del destino
        this.peso = peso; // asigna el parámetro al atributo del peso
    }

    // GETTERS    
    public Vertice<E> getReferenciaDestino(){ // devuelve el vertice de destino de la arista
        return referenciaDestino;
    }

    public double getPeso(){ // devuelve el peso de la arista
        return peso;
    }

    //MÉTODO QUE INDICA SI ESTA ARISTA ES IGUAL A OTRA
    @Override
    public boolean equals(Object otraArista){
        if (otraArista instanceof Arista<?>) { // verifica si el objeto recibido es una arista
            Arista<E> a = (Arista<E>) otraArista; // realiza el cast a tipo Arista
            return this.referenciaDestino.equals(a.referenciaDestino); // compara los vértices destino de ambas aristas
        }
        return false; // si no es una arista, devuelve false
    }
}

