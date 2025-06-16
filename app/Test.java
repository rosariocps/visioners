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

        // ------------------ MOSTRAR GRAFO ------------------
        System.out.println("Grafo actual:");
        System.out.println(grafo);

        // ------------------ MODIFICAR PESO DE UNA ARISTA ------------------
        grafo.modificarPesoArista("Recepción", "Área de Empaque", 18);
        System.out.println("\nDespués de modificar el peso de la arista:");
        System.out.println(grafo);

        // ------------------ VERIFICAR SI EXISTE UNA ARISTA ------------------
        boolean existe = grafo.existeArista("Zona de Almacenamiento", "Cámara de Frío");
        System.out.println("\n¿Existe una arista entre Zona de Almacenamiento y Cámara de Frío? " + existe);

        // ------------------ ELIMINAR UNA ARISTA ------------------
        grafo.eliminarArista("Área de Empaque", "Despacho");
        System.out.println("\nDespués de eliminar la arista de Área de Empaque a Despacho:");
        System.out.println(grafo);
   
    }
}
