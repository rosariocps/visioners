package visualizador;

import estructuras.GrafoAlmacen;
import modelos.AristaRuta;
import modelos.NodoUbicacion;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class VisualizadorGrafo {
    public static void mostrarGrafo(GrafoAlmacen grafo) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Grafo Visual");

        // Agregar nodos con nombres
        for (NodoUbicacion nodo : grafo.obtenerNodos()) {
            String id = String.valueOf(nodo.obtenerIndice());
            Node n = graph.addNode(id);
            n.setAttribute("ui.label", nodo.obtenerNombre());
        }

        // Agregar aristas con pesos
        for (int u = 0; u < grafo.cantidadNodos(); u++) {
            for (AristaRuta arista : grafo.obtenerRutas(u)) {
                String id = u + "-" + arista.obtenerDestino();
                if (graph.getEdge(id) == null) {
                    Edge e = graph.addEdge(id, String.valueOf(u), String.valueOf(arista.obtenerDestino()), true);
                    e.setAttribute("ui.label", String.valueOf(arista.obtenerPeso()));
                }
            }
        }

        graph.display();
    }
}
