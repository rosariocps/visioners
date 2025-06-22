package modelos;

// esta clase representa una ruta o camino entre dos ubicaciones
public class AristaRuta {
    private int origen;   // nodo de donde sale
    private int destino;  // nodo al que llega
    private int peso;     // valor de la ruta (puede ser distancia o tiempo)

    public AristaRuta(int origen, int destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    // devuelve el nodo origen
    public int obtenerOrigen() {
        return origen;
    }

    // devuelve el nodo destino
    public int obtenerDestino() {
        return destino;
    }

    // devuelve el valor de la ruta
    public int obtenerPeso() {
        return peso;
    }
}
