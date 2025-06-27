package graphArray;

import java.util.ArrayList;

public class Vertex<E> {
    private E data;
    public ArrayList<Edge<E>> listAdj;

    public Vertex(E data) {
        this.data = data;
        this.listAdj = new ArrayList<>();
    }
    public E getData() {
        return data;
    }

    public void setData(E newData) {
        this.data = newData;
    }

    //para comparar vértices por su dato
    public boolean equals(Object o) { //parametro de tipo Object para poder comparar cualquier objeto)
        //si el objeto o es una instancia de Vertex
        //se usa <?> porque el tipo generico (<E>) puede ser cualquiera (String, Integer, etc.)
        if (o instanceof Vertex<?>) { 
            Vertex<E> v = (Vertex<E>) o; //hacemos un cast de o al tipo Vertex<E>
            //compara el valor almacenado en el vertice actual (this.data) con el valor del vértice recibido como argumento (v.data)
            return this.data.equals(v.data); 
            //si son iguales devuelve true si no devuelve false
        }
        return false; //si el objeto o no era un vertice se devuelve false
    }

    @Override
    public String toString() {
        return this.data + " --> " + this.listAdj.toString() + "\n";
    }
}
