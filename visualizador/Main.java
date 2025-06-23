package visualizador;

import estructuras.GrafoAlmacen;

public class Main {
    public static void main(String[] args) {
        GrafoAlmacen grafo = new GrafoAlmacen();

        // Agregar ubicaciones del almacén
        grafo.agregarUbicacion("Pasillo A");
        grafo.agregarUbicacion("Estantería B");
        grafo.agregarUbicacion("Zona de Carga");
        grafo.agregarUbicacion("Salida");
        grafo.agregarUbicacion("Refrigerados");
        grafo.agregarUbicacion("Zona de Inspección");

        // Agregar rutas entre ubicaciones (con peso = distancia)
        grafo.agregarRuta(0, 1, 5);  // Pasillo A -> Estantería B
        grafo.agregarRuta(1, 2, 3);  // Estantería B -> Zona de Carga
        grafo.agregarRuta(2, 3, 4);  // Zona de Carga -> Salida
        grafo.agregarRuta(0, 4, 7);  // Pasillo A -> Refrigerados
        grafo.agregarRuta(4, 5, 2);  // Refrigerados -> Zona de Inspección
        grafo.agregarRuta(5, 3, 6);  // Zona de Inspección -> Salida

        // Mostrar grafo visual
        VisualizadorGrafo.mostrarGrafo(grafo);
    }
}
