package visualizador;

import estructuras.GrafoAlmacen;
import modelos.NodoUbicacion;
import modelos.AristaRuta;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class VisualizadorGrafoAlmacen {
    public static void visualizar(GrafoAlmacen grafo) {
        System.setProperty("org.graphstream.ui", "swing"); // importante para mostrarlo en VS Code

        Graph graph = new SingleGraph("Visualización del Almacén");

        // 1. Agregamos los nodos
        for (NodoUbicacion nodo : grafo.obtenerNodos()) {
            String id = String.valueOf(nodo.obtenerIndice());
            String label = nodo.obtenerNombre();

            Node visualNode = graph.addNode(id);
            visualNode.setAttribute("ui.label", label);
        }

        // 2. Agregamos las aristas (rutas)
        for (NodoUbicacion origen : grafo.obtenerNodos()) {
            int i = origen.obtenerIndice();
            for (AristaRuta arista : grafo.obtenerRutas(i)) {
                String idArista = i + "-" + arista.obtenerDestino(); // id único
                String origenId = String.valueOf(i);
                String destinoId = String.valueOf(arista.obtenerDestino());

                if (graph.getEdge(idArista) == null) {
                    Edge e = graph.addEdge(idArista, origenId, destinoId, true); // dirigido
                    e.setAttribute("ui.label", String.valueOf(arista.obtenerPeso()));
                }
            }
        }

        graph.display(); // Mostrar la ventana
    }
}
