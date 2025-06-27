package visualizador;

import graphArray.GrafoDirigidoArrayList;
import almacen.UbicacionAlmacen;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class VisualizadorGrafo {

    public static void mostrar(GrafoDirigidoArrayList<UbicacionAlmacen> grafo) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Almacén");

        // Agregar nodos (ubicaciones)
        for (UbicacionAlmacen ubic : grafo.getVertices()) {
            String id = ubic.getNombre(); // usamos el nombre como ID
            Node node = graph.addNode(id);
            node.setAttribute("ui.label", ubic.getNombre());
        }

        // Agregar aristas dirigidas
        for (UbicacionAlmacen origen : grafo.getVertices()) {
            for (UbicacionAlmacen destino : grafo.getAdyacentes(origen)) {
                String idOrigen = origen.getNombre();
                String idDestino = destino.getNombre();
                String edgeId = idOrigen + "->" + idDestino;

                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, idOrigen, idDestino, true);
                }
            }
        }

        graph.display();
    }
}
