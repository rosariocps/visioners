package visualizador;

import estructuras.GrafoAlmacen;
import modelos.AristaRuta;
import modelos.NodoUbicacion;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import javax.swing.*;

public class VisualizadorGrafoAlmacen {

    public static void visualizar(GrafoAlmacen grafo) {
        Graph graph = new SingleGraph("Mapa del Almacén");

        // Estilo CSS embebido
        String estilo = """
            node {
                fill-color: #f06292;
                size: 25px;
                text-size: 14px;
                text-alignment: center;
                text-color: white;
                stroke-mode: plain;
                stroke-color: black;
            }
            edge {
                fill-color: #555;
                text-color: black;
                text-size: 12px;
            }
        """;
        graph.setAttribute("ui.stylesheet", estilo);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        // Agregar nodos
        for (NodoUbicacion nodo : grafo.obtenerNodos()) {
            Node n = graph.addNode(String.valueOf(nodo.obtenerIndice()));
            n.setAttribute("ui.label", nodo.obtenerNombre());
        }

        // Agregar aristas
        for (NodoUbicacion nodo : grafo.obtenerNodos()) {
            int origen = nodo.obtenerIndice();
            for (AristaRuta arista : grafo.obtenerRutas(origen)) {
                String id = origen + "-" + arista.obtenerDestino();
                if (graph.getEdge(id) == null) {
                    Edge e = graph.addEdge(id,
                        String.valueOf(origen),
                        String.valueOf(arista.obtenerDestino()),
                        true); // dirigido
                    e.setAttribute("ui.label", arista.obtenerPeso() + "");
                }
            }
        }

        // Mostrar en Swing
        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        ViewPanel panel = viewer.addDefaultView(false);
        JFrame frame = new JFrame("Visualizador de Grafo - Almacén");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
