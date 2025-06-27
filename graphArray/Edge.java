package graphArray;

public class Edge<E> {
    private Vertex<E> refDest; //referencia al vertice destino
    private int weight;
    
    //constructor en tal caso no le des un peso
    public Edge(Vertex<E> refDest) {
        this(refDest, -1); //asigna un peso por defecto de -1
        //luego llama al otro constructor pasándole -1 como peso
    }
    //constructor principal 
    public Edge(Vertex<E> refDest, int weight) {
        this.refDest = refDest;
        this.weight = weight;
    }
    //getter devuelve el vertice destino de esta arista
    public Vertex<E> getRefDest() {
        return refDest;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    //para comparar si dos aristas son iguales 
    public boolean equals(Object o) { //parametro de tipo Object para poder comparar cualquier objeto)
        //si el objeto o es una instancia de Edge
        //se usa <?> porque el tipo generico (<E>) puede ser cualquiera (String, Integer, etc.)
        if (o instanceof Edge<?>) {
            Edge<E> e = (Edge<E>) o; //hacemos un cast de o al tipo Edge<E>
            //compara el vertice destino (refDest) de la arista actual (this) con el vertice destino de la otra arista (e)
            //el peso no se toma en cuenta aqui
            return this.refDest.equals(e.refDest);
            //si son iguales devuelve true si no devuelve false
        }
        return false; //si el objeto o no era una arista retorna false
    }
    //para imprimir la arista
    public String toString() {
        if (this.weight > -1)
            return refDest.getData() + " [" + this.weight + "], ";
        else
            return refDest.getData() + ", ";
    }
}
