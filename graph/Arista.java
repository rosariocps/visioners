package graph;

public class Arista<E> {

    //Atributos
    private Vertice<E> referenciaDestino; // Vértice de destino de la arista
    private double peso; // Peso de la arista 

}

public Arista(Vertice<E> destino, double peso) {
        this.destino = destino;
        this.peso = peso;
}