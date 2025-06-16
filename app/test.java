package app;

import graph.GrafoDirigido;

public class test {
    public static void main(String[] args) {
        GrafoDirigido<String> grafo = new GrafoDirigido<>();

        // ------------------ INSERTAR VÉRTICES ------------------
        grafo.insertarVertice("Recepción");
        grafo.insertarVertice("Zona de Almacenamiento");
        grafo.insertarVertice("Cámara de Frío");
        grafo.insertarVertice("Área de Empaque");
        grafo.insertarVertice("Despacho");

        // ------------------ INSERTAR ARISTAS ------------------
        grafo.insertarArista("Recepción", "Zona de Almacenamiento", 10);
        grafo.insertarArista("Zona de Almacenamiento", "Cámara de Frío", 20);
        grafo.insertarArista("Cámara de Frío", "Área de Empaque", 15);
        grafo.insertarArista("Área de Empaque", "Despacho", 12);
        grafo.insertarArista("Recepción", "Área de Empaque", 25);
        
    }
}
