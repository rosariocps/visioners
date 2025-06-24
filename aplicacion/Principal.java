package aplicacion; 

import estructuras.GrafoAlmacen; // importa la clase que modela el grafo del almacen
import modelos.ArbolBProducto; // importa la clase que simula un arbol b+ sencillo
import modelos.Producto; // importa la clase producto con codigo y nombre
import simulacion.SimuladorInventario; // importa la clase que simula escenarios
import visualizacion.VisualizadorTexto; // importa la clase que muestra info en consola

public class Principal { // declara la clase principal
    public static void main(String[] args) { // metodo principal, punto de entrada
        GrafoAlmacen grafo = new GrafoAlmacen(); // crea un grafo vacio
        grafo.agregarUbicacion("recepcion"); // agrega nodo "recepcion"
        grafo.agregarUbicacion("pasillo 1"); // agrega nodo "pasillo 1"
        grafo.agregarUbicacion("estante a"); // agrega nodo "estante a"
        grafo.agregarUbicacion("estante b"); // agrega nodo "estante b"
        grafo.agregarUbicacion("zona carga"); // agrega nodo "zona carga"

        grafo.agregarRuta(0, 1, 5); // crea ruta recepcion→pasillo1 con peso 5
        grafo.agregarRuta(1, 2, 3); // crea ruta pasillo1→estanteA con peso 3
        grafo.agregarRuta(2, 3, 2); // crea ruta estanteA→estanteB con peso 2
        grafo.agregarRuta(3, 4, 4); // crea ruta estanteB→zonaCarga con peso 4

        int n = grafo.cantidadNodos(); // obtiene numero de ubicaciones
        ArbolBProducto[] arboles = new ArbolBProducto[n]; // crea arreglo de arboles
        for (int i = 0; i < n; i++) { // para cada ubicacion
            arboles[i] = new ArbolBProducto(10); // crea un arbol b+ con capacidad 10
        }

        arboles[2].insertarProducto(new Producto("p001", "tornillos")); // inserta producto en estante a
        arboles[3].insertarProducto(new Producto("p002", "tuercas")); // inserta producto en estante b

        VisualizadorTexto.mostrarUbicaciones(grafo); // muestra todas las ubicaciones
        VisualizadorTexto.mostrarRutas(grafo); // muestra todas las rutas con sus pesos
        VisualizadorTexto.mostrarProductosPorUbicacion(arboles); // muestra productos iniciales

        SimuladorInventario simulador = new SimuladorInventario(grafo, arboles); // crea el simulador

        simulador.cerrarRuta(1, 2); // simula cierre de pasillo1→estanteA
        simulador.simularAdicionProducto(2, new Producto("p003", "arandelas")); // añade arandelas
        simulador.simularEliminacionProducto(3, "p002"); // elimina tuercas
        simulador.simularCrecimientoInventario(4, new Producto("p004", "clavos")); // simula crecimiento
        simulador.recalcularRutas(0); // recalcula rutas desde recepcion

        System.out.println(); // imprime linea vacia de separacion
        VisualizadorTexto.mostrarRutas(grafo); // muestra rutas tras simulaciones
        VisualizadorTexto.mostrarProductosPorUbicacion(arboles); // muestra productos tras simulaciones
    }
}
