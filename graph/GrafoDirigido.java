package graph;

import linkedlist.ListaEnlazada;
import linkedlist.Nodo;

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

        Nodo<Arista<E>> nodoActual = verticeOrigen.listaAdyacencia.getFirst();

        while (nodoActual != null) {
            Arista<E> arista = nodoActual.getData();
            if (arista.getReferenciaDestino().equals(verticeDestino)) {
                throw new RuntimeException("ya existe una arista entre estos vértices");
            }
            nodoActual = nodoActual.getNext();
        }

        Arista<E> nuevaArista = new Arista<>(verticeDestino, peso); // crea la arista desde origen hacia destino
        // agrega la arista a la lista de adyacencia del vértice origen
        verticeOrigen.listaAdyacencia.insertLast(nuevaArista); // se utiliza el método inserLast de ListaEnlazada
    }

    // ELIMINAR UNA ARISTA DIRIGIDA
    public void eliminarArista(E origen, E destino) {
        // Busca los vértices de origen y destino en la lista de vértices
        Vertice<E> verticeOrigen = buscarVertice(origen);
        Vertice<E> verticeDestino = buscarVertice(destino);

        if (verticeOrigen == null || verticeDestino == null) { // verifica que ambos vértices existan
            // lanza una excepción si falta alguno
            throw new RuntimeException("uno o ambos vértices no existen");
        }

        boolean eliminado = verticeOrigen.listaAdyacencia.removeNodo(new Arista<>(verticeDestino));

        if (!eliminado) {
            throw new RuntimeException("la arista no existe");
        }
    }

    // MODIFICAR EL PESO DE UNA ARISTA EXISTENTE
    public void modificarPesoArista(E origen, E destino, int nuevoPeso) {
        // Busca los vértices de origen y destino en la lista de vértices
        Vertice<E> verticeOrigen = buscarVertice(origen);
        Vertice<E> verticeDestino = buscarVertice(destino);

        if (verticeOrigen == null || verticeDestino == null) { // verifica que ambos vértices existan
            // lanza una excepción si falta alguno
            throw new RuntimeException("uno o ambos vértices no existen");
        }

        // Se crea un nuevo nodo que apunte al primer vertice de destino que aparece en la lista de Adyacencia
        Nodo<Arista<E>> nodoActual = verticeOrigen.listaAdyacencia.getFirst();

        while (nodoActual != null) {
            Arista<E> arista = nodoActual.getData();
            if (arista.getReferenciaDestino().equals(verticeDestino)) {
                arista.setPeso(nuevoPeso);
                return;
            }
            nodoActual = nodoActual.getNext();
        }

        throw new RuntimeException("la arista no existe");   
    }

    // VERIFICAR SI EXISTE UNA ARISTA ENTRE DOS VERTICES
    public boolean existeArista(E origen, E destino) {
        Vertice<E> verticeOrigen = buscarVertice(origen); // busca el vértice origen
        Vertice<E> verticeDestino = buscarVertice(destino); // busca el vértice destino

        if (verticeOrigen == null || verticeDestino == null) {
            return false; // si uno no existe, no puede haber arista
        }

        Nodo<Arista<E>> nodoActual = verticeOrigen.listaAdyacencia.getFirst(); // inicio de lista de adyacencia

        while (nodoActual != null) {
            Arista<E> arista = nodoActual.getData(); // obtenemos la arista
            if (arista.getReferenciaDestino().equals(verticeDestino)) {
                return true; // si encontramos coincidencia, existe la arista
            }
            nodoActual = nodoActual.getNext(); // avanzar al siguiente nodo
        }

        return false; // no se encontró ninguna arista hacia el destino
    }

    // ---------------- UTILITARIO ----------------

    // MOSTRAR EL GRAFO COMO TEXTO
    @Override
    public String toString() {
        
        return "";
    }
}
