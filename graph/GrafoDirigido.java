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
        if (buscarVertice(dato) != null) { // si el vértice ya existe, no se inserta
            throw new RuntimeException("el vértice ya existe");
        }

        Vertice<E> nuevoVertice = new Vertice<>(dato); // creamos un nuevo vértice con el dato
        listaVertices.insertLast(nuevoVertice); // lo agregamos al final de la lista de vértices
    }

    // ELIMINAR UN VÉRTICE Y SUS ARISTAS ASOCIADAS
    public void eliminarVertice(E dato) {
        Vertice<E> verticeAEliminar = buscarVertice(dato);
        if (verticeAEliminar == null) {
            throw new RuntimeException("el vértice no existe");
        }
        // 1. Eliminar las aristas que llegan a este vértice desde otros vértices
        Nodo<Vertice<E>> nodoActual = listaVertices.getFirst();
        while (nodoActual != null) {
            Vertice<E> vertice = nodoActual.getData();
            if (!vertice.equals(verticeAEliminar)) {
                vertice.listaAdyacencia.removeNodo(new Arista<>(verticeAEliminar));
            }
            nodoActual = nodoActual.getNext();
        }
        // 2. Eliminar todas las aristas que salen de este vértice (vaciar su lista de adyacencia)
        verticeAEliminar.listaAdyacencia.destroyList(); 

        // 3. Eliminar el vértice de la lista de vértices
        listaVertices.removeNodo(verticeAEliminar);
    }

    // MODIFICAR EL DATO DE UN VÉRTICE
    public void modificarVertice(E datoAntiguo, E datoNuevo) {
        
    }

    // BUSCAR UN VÉRTICE POR SU DATO
    public Vertice<E> buscarVertice(E dato) {
        Nodo<Vertice<E>> nodoActual = listaVertices.getFirst(); // apunta al primer nodo de la lista

        while (nodoActual != null) { // recorre mientras haya nodos
            if (nodoActual.getData().getDato().equals(dato)) { // compara el dato del vértice con el dato buscado
                return nodoActual.getData(); // si lo encuentra, retorna el vértice
            }
            nodoActual = nodoActual.getNext(); // avanza al siguiente nodo
        }

        return null; // si no lo encuentra, retorna null
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

        if (!existeArista(origen, destino)) {
            throw new RuntimeException("la arista no existe");
        }

        verticeOrigen.listaAdyacencia.removeNodo(new Arista<>(verticeDestino));
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
        StringBuilder resultado = new StringBuilder();
        Nodo<Vertice<E>> nodoActual = listaVertices.getFirst();

        while (nodoActual != null) {
            Vertice<E> vertice = nodoActual.getData();
            resultado.append(vertice.getDato()).append(" --> ");

            Nodo<Arista<E>> nodoArista = vertice.listaAdyacencia.getFirst();
            if (nodoArista == null) {
                resultado.append("Sin conexiones");
            } else {
                while (nodoArista != null) {
                    Arista<E> arista = nodoArista.getData();
                    resultado.append(arista.getReferenciaDestino().getDato())
                            .append(" [")
                            .append(arista.getPeso())
                            .append("] ");
                    nodoArista = nodoArista.getNext();
                }
            }
            resultado.append("\n");
            nodoActual = nodoActual.getNext();
        }

        return resultado.toString();
    }
}
