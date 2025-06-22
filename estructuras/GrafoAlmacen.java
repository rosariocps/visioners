package estructuras;

import modelos.NodoUbicacion;

/**
 * Clase que modela el grafo del almacén usando una matriz de adyacencia.
 */
public class GrafoAlmacen {
    private NodoUbicacion[] nodos;       // arreglo de ubicaciones
    private MatrizAdyacencia conexiones; // matriz de rutas
    private int totalNodos;              // cuántas ubicaciones hay

    /**
     * Constructor: crea un grafo con capacidad para n nodos.
     * @param capacidad número máximo de ubicaciones
     */
    public GrafoAlmacen(int capacidad) {
        nodos = new NodoUbicacion[capacidad];
        conexiones = new MatrizAdyacencia(capacidad);
        totalNodos = 0;
    }

    /**
     * Agrega una nueva ubicación (nodo) al grafo.
     * @param nombre descripción de la ubicación
     */
    public void agregarUbicacion(String nombre) {
        if (totalNodos < nodos.length) {
            nodos[totalNodos] = new NodoUbicacion(nombre, totalNodos);
            totalNodos++;
        } else {
            System.out.println("No cabe más ubicaciones.");
        }
    }

    /**
     * Agrega una ruta dirigida entre dos ubicaciones con un peso.
     * @param origen  índice del nodo origen
     * @param destino índice del nodo destino
     * @param peso    valor de la ruta (distancia o tiempo)
     */
    public void agregarRuta(int origen, int destino, int peso) {
        conexiones.agregarRuta(origen, destino, peso);
    }

    /** Devuelve el arreglo de nodos (ubicaciones). */
    public NodoUbicacion[] obtenerNodos() {
        return nodos;
    }

    /** Devuelve la matriz de adyacencia con las rutas. */
    public MatrizAdyacencia obtenerConexiones() {
        return conexiones;
    }

    /** Número actual de ubicaciones en el grafo. */
    public int cantidadNodos() {
        return totalNodos;
    }
}
