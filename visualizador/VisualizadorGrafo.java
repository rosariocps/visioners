import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class VisualizadorGrafo {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Almacén");

        graph.addNode("0").setAttribute("ui.label", "Pasillo A");
        graph.addNode("1").setAttribute("ui.label", "Estantería B");
        graph.addEdge("0-1", "0", "1", true); // true = dirigido

        graph.display();
    }
}