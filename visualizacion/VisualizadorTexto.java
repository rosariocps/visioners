package visualizacion; 

import modelos.NodoUbicacion; // importa la clase nodo con nombre e indice
import estructuras.GrafoAlmacen; // importa la clase que modela el grafo
import modelos.ArbolBProducto; // importa la clase que simula un arbol b+
import modelos.Producto; // importa la clase producto (para su metodo mostrarDatos)
import modelos.AristaRuta; // importa la clase que contiene origen, destino y peso

public class VisualizadorTexto { // declara la clase de visualizacion

    public static void mostrarUbicaciones(GrafoAlmacen grafo) { // muestra todos los nodos
        System.out.println("=== ubicaciones del almacen ==="); // titulo de seccion
        for (NodoUbicacion nodo : grafo.obtenerNodos()) { // recorre cada nodo
            if (nodo != null) { // si el nodo existe
                System.out.println( // imprime indice y nombre
                    "• [" + nodo.obtenerIndice() + "] " + nodo.obtenerNombre()
                );
            }
        }
        System.out.println(); // linea vacia para separacion
    }

    public static void mostrarRutas(GrafoAlmacen grafo) { // muestra todas las rutas
        System.out.println("=== rutas (origen -> destino : peso) ==="); // titulo
        int n = grafo.cantidadNodos(); // obtiene numero de nodos
        for (int i = 0; i < n; i++) { // recorre cada nodo origen
            for (AristaRuta ar : grafo.obtenerRutas(i)) { // por cada ruta saliente
                System.out.println( // imprime origen, destino y peso
                    i + " -> " + ar.obtenerDestino() + " : " + ar.obtenerPeso()
                );
            }
        }
        System.out.println(); // linea vacia para separacion
    }

    public static void mostrarProductosPorUbicacion(ArbolBProducto[] arboles) { // muestra productos
        System.out.println("=== productos por ubicacion ==="); // titulo
        for (int i = 0; i < arboles.length; i++) { // recorre cada arbol
            System.out.println("ubicacion " + i + ":"); // imprime indice de ubicacion
            arboles[i].mostrarProductos(); // llama al metodo que imprime cada producto
        }
        System.out.println(); // linea vacia al terminar
    }
}
