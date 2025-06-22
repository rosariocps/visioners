package visualizacion;

import modelos.NodoUbicacion;
import modelos.Producto;
import modelos.ArbolBProducto;
import estructuras.GrafoAlmacen;
import estructuras.MatrizAdyacencia;

/**
 * Clase para mostrar en consola información del grafo,
 * las rutas y los productos en cada ubicación.
 */
public class VisualizadorTexto {

    /**
     * Muestra todas las ubicaciones (nodos) del almacén.
     * @param grafo el grafo que contiene los nodos
     */
    public static void mostrarUbicaciones(GrafoAlmacen grafo) {
        System.out.println("=== Ubicaciones del almacén ===");
        for (NodoUbicacion nodo : grafo.obtenerNodos()) {
            if (nodo != null) {
                System.out.printf("• [%d] %s%n",
                    nodo.obtenerIndice(),
                    nodo.obtenerNombre());
            }
        }
        System.out.println();
    }

    /**
     * Muestra todas las rutas dirigidas con su peso
     * recorriendo la matriz de adyacencia.
     * @param grafo el grafo con las conexiones
     */
    public static void mostrarRutas(GrafoAlmacen grafo) {
        System.out.println("=== Rutas (origen → destino : peso) ===");
        MatrizAdyacencia m = grafo.obtenerConexiones();
        int n = grafo.cantidadNodos();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int peso = m.obtenerPeso(i, j);
                if (peso > 0) {
                    System.out.printf("%d → %d : %d%n", i, j, peso);
                }
            }
        }
        System.out.println();
    }

    /**
     * Muestra los productos almacenados en cada árbol B+,
     * según la ubicación indicada por el índice.
     * @param arboles arreglo de árboles B+ por ubicación
     */
    public static void mostrarProductosPorUbicacion(ArbolBProducto[] arboles) {
        System.out.println("=== Productos por ubicación ===");
        for (int i = 0; i < arboles.length; i++) {
            System.out.println("Ubicación " + i + ":");
            if (arboles[i] != null) {
                arboles[i].mostrarProductos();
            } else {
                System.out.println("  (sin árbol de productos)");
            }
        }
        System.out.println();
    }
}
