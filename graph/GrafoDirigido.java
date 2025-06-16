package graph;

import linkedlist.ListaEnlazada;

public class GrafoDirigido<E> {

    //Lista de todos los vértices (ubicaciones del almacén)
    protected ListaEnlazada<Vertice<E>> listaVertices;

    // Constructor
    public GrafoDirigido() {
        listaVertices = new ListaEnlazada<>();
    }

    // ---------------- MÉTODOS PARA VÉRTICES ----------------

    // INSERTAR UN NUEVO VERTICE
    public void insertarVertice(E dato) {

    }

    // ELIMINAR UN VÉRTICE Y SUS ARISTAS ASOCIADAS
    public void eliminarVertice(E dato) {
        
    }

    // MODIFICAR EL DATO DE UN VÉRTICE
    public void modificarVertice(E datoAntiguo, E datoNuevo) {
        
    }

    // BUSCAR UN VÉRTICE POR SU DATO
    public Vertice<E> buscarVertice(E dato) {
        
        return null;
    }

    // ---------------- MÉTODOS PARA ARISTAS ----------------

    // INSERTAR UNA ARISTA DIRIGIDA CON PESO
    public void insertarArista(E origen, E destino, int peso) {
        // Busca los vértices de origen y destino en la lista de vértices
        Vertice<E> verticeOrigen = buscarVertice(origen);
        Vertice<E> verticeDestino = buscarVertice(destino);

        if (verticeOrigen == null || verticeDestino == null) { // verifica que ambos vértices existan
             // lanza una excepción si falta alguno
            throw new RuntimeException("Uno o ambos vértices no existen");
        }

        Arista<E> nuevaArista = new Arista<>(verticeDestino, peso); // crea la arista desde origen hacia destino
        verticeOrigen.listaAdyacencia.insertLast(nuevaArista); // agrega la arista a la lista del vértice origen
    }

    // ELIMINAR UNA ARISTA DIRIGIDA
    public void eliminarArista(E origen, E destino) {
        
    }

    // MODIFICAR EL PESO DE UNA ARISTA EXISTENTE
    public void modificarPesoArista(E origen, E destino, int nuevoPeso) {
        
    }

    // VERIFICAR SI EXISTE UNA ARISTA ENTRE DOS VERTICES
    public boolean existeArista(E origen, E destino) {
        
        return false;
    }

    // ---------------- UTILITARIO ----------------

    // MOSTRAR EL GRAFO COMO TEXTO
    @Override
    public String toString() {
        
        return "";
    }
}
