package simulacion;

import estructuras.GrafoAlmacen;
import estructuras.AlgoritmosGrafo;
import modelos.ArbolBProducto;
import modelos.Producto;

/**
 * Simula escenarios de inventario:
 *  - cierre de rutas (eliminación de aristas)
 *  - adición y eliminación de productos
 *  - recálculo de rutas óptimas
 */
public class SimuladorInventario {
    private GrafoAlmacen grafo;
    private ArbolBProducto[] arbolesPorUbicacion;

    /**
     * Constructor.
     * @param grafo el grafo que modela el almacén
     * @param arboles arreglo de árboles B+ por ubicación
     */
    public SimuladorInventario(GrafoAlmacen grafo, ArbolBProducto[] arboles) {
        this.grafo = grafo;
        this.arbolesPorUbicacion = arboles;
    }

    /**
     * Simula el cierre de un pasillo o conexión:
     * pone el peso de la ruta a cero.
     */
    public void cerrarRuta(int origen, int destino) {
        System.out.println(">> Cerrando ruta de nodo " + origen + " a nodo " + destino);
        grafo.obtenerConexiones().agregarRuta(origen, destino, 0);
    }

    /**
     * Simula la adición de un producto en la ubicación dada.
     */
    public void simularAdicionProducto(int indiceUbicacion, Producto producto) {
        System.out.println(">> Agregando producto '" 
            + producto.nombre + "' (código " 
            + producto.codigo + ") en ubicación " 
            + indiceUbicacion);
        arbolesPorUbicacion[indiceUbicacion].insertarProducto(producto);
    }

    /**
     * Simula la eliminación de un producto (conceptual).
     * Busca el producto y, si existe, muestra mensaje.
     */
    public void simularEliminacionProducto(int indiceUbicacion, String codigo) {
        System.out.println(">> Eliminando producto con código " 
            + codigo + " de ubicación " + indiceUbicacion);
        Producto p = arbolesPorUbicacion[indiceUbicacion].buscarPorCodigo(codigo);
        if (p != null) {
            System.out.println("   • Producto encontrado: " + p.nombre);
            System.out.println("   • Se simula su eliminación (no se modifica físicamente el arreglo).");
        } else {
            System.out.println("   • No se encontró el producto. Nada que eliminar.");
        }
    }

    /**
     * Recalcula y muestra distancias mínimas desde un nodo de inicio
     * usando el algoritmo de Dijkstra.
     */
    public void recalcularRutasOptimales(int inicio) {
        System.out.println(">> Recalculando rutas óptimas desde nodo " + inicio);
        int[] distancias = AlgoritmosGrafo.dijkstra(
            grafo.obtenerConexiones(), inicio
        );
        for (int i = 0; i < distancias.length; i++) {
            String valor = (distancias[i] == Integer.MAX_VALUE) 
                            ? "∞" 
                            : String.valueOf(distancias[i]);
            System.out.println("   Distancia hasta nodo " + i + ": " + valor);
        }
    }
}
