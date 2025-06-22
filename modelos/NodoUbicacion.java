package modelos;

// esta clase representa un punto del almacen como un nodo del grafo
public class NodoUbicacion {
    private String nombre; // nombre como "Estante A" o "Pasillo 1"
    private int indice;    // posicion del nodo en el grafo (numerico)

    public NodoUbicacion(String nombre, int indice) {
        this.nombre = nombre;
        this.indice = indice;
    }

    // devuelve el nombre del nodo
    public String obtenerNombre() {
        return nombre;
    }

    // devuelve el indice numerico del nodo
    public int obtenerIndice() {
        return indice;
    }
}
