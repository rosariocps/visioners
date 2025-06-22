package modelos; // indica que esta clase esta en el paquete modelos

// clase que representa una ruta entre dos nodos con un peso
public class AristaRuta {
    private int origen;   // indice del nodo origen
    private int destino;  // indice del nodo destino
    private int peso;     // valor asociado a la ruta

    public AristaRuta(int origen, int destino, int peso) { // constructor con parametros
        this.origen = origen;     // asigna el indice de origen
        this.destino = destino;   // asigna el indice de destino
        this.peso = peso;         // asigna el peso de la ruta
    }

    public int obtenerOrigen() {  // retorna el nodo origen
        return origen;            // devuelve el valor de origen
    }

    public int obtenerDestino() { // retorna el nodo destino
        return destino;           // devuelve el valor de destino
    }

    public int obtenerPeso() {    // retorna el peso de la ruta
        return peso;              // devuelve el valor de peso
    }
}
