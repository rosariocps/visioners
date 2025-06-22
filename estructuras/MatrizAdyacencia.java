package estructuras;

/**
 * Representa un grafo dirigido y ponderado con una matriz de adyacencia.
 */
public class MatrizAdyacencia {
    private int[][] matriz;

    /**
     * Constructor: crea una matriz cuadrada de tamaño n.
     * @param tamaño número de nodos del grafo
     */
    public MatrizAdyacencia(int tamaño) {
        matriz = new int[tamaño][tamaño];
    }

    /**
     * Agrega o actualiza el peso de la ruta de origen a destino.
     * @param origen  índice del nodo origen
     * @param destino índice del nodo destino
     * @param peso    peso de la ruta (p.ej. distancia)
     */
    public void agregarRuta(int origen, int destino, int peso) {
        matriz[origen][destino] = peso;
    }

    /**
     * Obtiene el peso de la ruta de origen a destino.
     * Si no hay ruta, devuelve 0.
     */
    public int obtenerPeso(int origen, int destino) {
        return matriz[origen][destino];
    }

    /** Devuelve la matriz interna (para usar en los algoritmos). */
    public int[][] obtenerMatriz() {
        return matriz;
    }
}
