package aplicacion;

import estructuras.GrafoAlmacen;
import simulacion.SimuladorInventario;
import visualizacion.VisualizadorTexto;
import modelos.ArbolBProducto;
import modelos.Producto;

/**
 * Clase principal que une todas las partes del sistema:
 * - construye el grafo de ubicaciones
 * - crea árboles B+ de productos por ubicación
 * - muestra el estado inicial y final
 * - ejecuta simulaciones de inventario
 */
public class Principal {
    public static void main(String[] args) {
        // 1. Crear grafo con 5 ubicaciones
        GrafoAlmacen grafo = new GrafoAlmacen(5);
        grafo.agregarUbicacion("Recepción");
        grafo.agregarUbicacion("Pasillo 1");
        grafo.agregarUbicacion("Estante A");
        grafo.agregarUbicacion("Estante B");
        grafo.agregarUbicacion("Zona de Carga");

        // 2. Conectar ubicaciones con rutas (peso = distancia o tiempo)
        grafo.agregarRuta(0, 1, 5);
        grafo.agregarRuta(1, 2, 3);
        grafo.agregarRuta(2, 3, 2);
        grafo.agregarRuta(3, 4, 4);

        // 3. Crear un árbol B+ para cada ubicación (capacidad 10)
        ArbolBProducto[] arboles = new ArbolBProducto[grafo.cantidadNodos()];
        for (int i = 0; i < arboles.length; i++) {
            arboles[i] = new ArbolBProducto(10);
        }

        // 4. Insertar algunos productos inicialmente
        arboles[2].insertarProducto(new Producto("P001", "Tornillos"));
        arboles[3].insertarProducto(new Producto("P002", "Tuercas"));

        // 5. Mostrar estado inicial
        VisualizadorTexto.mostrarUbicaciones(grafo);
        VisualizadorTexto.mostrarRutas(grafo);
        VisualizadorTexto.mostrarProductosPorUbicacion(arboles);

        // 6. Crear simulador y ejecutar escenarios
        SimuladorInventario simulador = new SimuladorInventario(grafo, arboles);

        simulador.cerrarRuta(1, 2);                                // cierra conexión Pasillo 1 → Estante A
        simulador.simularAdicionProducto(2, new Producto("P003", "Arandelas"));  // añade producto en Estante A
        simulador.simularEliminacionProducto(3, "P002");           // elimina Tuercas de Estante B
        simulador.recalcularRutasOptimales(0);                     // recalcula distancias desde Recepción

        // 7. Mostrar estado final tras simulaciones
        System.out.println("\n=== Estado final tras simulaciones ===");
        VisualizadorTexto.mostrarRutas(grafo);
        VisualizadorTexto.mostrarProductosPorUbicacion(arboles);
    }
}
