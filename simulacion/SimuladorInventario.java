package simulacion; // indica que esta clase esta en el paquete simulacion

import estructuras.AlgoritmosGrafo; // importa la clase que modela el grafo del almacen
import estructuras.GrafoAlmacen; // importa los algoritmos de grafo (bfs, dijkstra, etc.)
import modelos.ArbolBProducto; // importa la clase que simula un arbol b+ sencillo
import modelos.Producto; // importa la clase producto

/**
 * clase que simula diferentes escenarios de inventario en el almacen
 */
public class SimuladorInventario { 
    private GrafoAlmacen grafo; // grafo que contiene ubicaciones y rutas
    private ArbolBProducto[] arboles; // arreglo de arboles b+ por cada ubicacion

    public SimuladorInventario(GrafoAlmacen grafo, ArbolBProducto[] arboles) {
        this.grafo   = grafo; // asigna el grafo recibido al atributo
        this.arboles = arboles; // asigna el arreglo de arboles al atributo
    }

    /** cierra una ruta entre dos nodos (simula bloqueo de pasillo) */
    public void cerrarRuta(int origen, int destino) {
        System.out.println(">> cerrando ruta de " + origen + " a " + destino); // mensaje de accion
        grafo.eliminarRuta(origen, destino); // elimina la ruta del grafo
    }

    /** simula la adicion de un producto en una ubicacion */
    public void simularAdicionProducto(int ubicacion, Producto producto) {
        System.out.println(">> agregando producto " 
            + producto.codigo + " en ubicacion " + ubicacion); // mensaje de adicion
        arboles[ubicacion].insertarProducto(producto); // inserta el producto en el arbol correspondiente
    }

    /** simula la eliminacion de un producto por codigo en una ubicacion */
    public void simularEliminacionProducto(int ubicacion, String codigo) {
        System.out.println(">> eliminando producto " 
            + codigo + " de ubicacion " + ubicacion); // mensaje de eliminacion
        boolean exito = arboles[ubicacion].eliminarProducto(codigo); // intenta borrar el producto
        if (exito) { // si se elimino correctamente
            System.out.println("   • producto eliminado correctamente"); // mensaje de exito
        } else { // si no se encontro el producto
            System.out.println("   • no se encontro el producto para eliminar"); // mensaje de fallo
        }
    }

    /** simula crecimiento de inventario agregando un producto */
    public void simularCrecimientoInventario(int ubicacion, Producto producto) {
        System.out.println(">> simulando crecimiento: agregando " 
            + producto.codigo + " en ubicacion " + ubicacion); // mensaje de crecimiento
        // confia en insertarProducto, que internamente avisa si no hay espacio
        arboles[ubicacion].insertarProducto(producto); 
    }

    /** recalcula rutas optimas desde un nodo usando dijkstra */
    public void recalcularRutas(int inicio) {
        System.out.println(">> recalculando rutas desde nodo " + inicio); // mensaje de recalculo
        int[] dist = AlgoritmosGrafo.dijkstra(grafo, inicio); // obtiene distancias minimas
        for (int i = 0; i < dist.length; i++) { // recorre cada nodo destino
            String valor = (dist[i] == Integer.MAX_VALUE) // si no es alcanzable
                ? "∞"                                     // marca como infinito
                : String.valueOf(dist[i]);               // convierte distancia a texto
            System.out.println("   distancia a " + i + ": " + valor); // imprime la distancia
        }
    }
}
