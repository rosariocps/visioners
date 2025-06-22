package modelos; // indica que esta clase esta en el paquete modelos

// clase que modela una ubicacion del almacen como un nodo
public class NodoUbicacion {
    private String nombre; // nombre descriptivo de la ubicacion
    private int indice;    // indice numerico que identifica el nodo

    public NodoUbicacion(String nombre, int indice) { // constructor con parametros
        this.nombre = nombre;  // asigna el nombre de la ubicacion
        this.indice = indice;  // asigna el indice numerico
    }

    public String obtenerNombre() { // retorna el nombre de la ubicacion
        return nombre;              // devuelve la propiedad nombre
    }

    public int obtenerIndice() {    // retorna el indice del nodo
        return indice;              // devuelve la propiedad indice
    }
}
