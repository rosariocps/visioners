package graphLink;

import exceptions.ExceptionIsEmpty;
import linkedlist.ListaEnlazada;
import linkedlist.Nodo;
import queuelink.PriorityQueueLinkSort;
import queuelink.QueueLink;
import stacklink.StackLink;

// ----------------- GRAFO A PARTIR DE LISTAS ENLAZADAS -----------------

public class GrafoDirigidoListLink<E> {
    protected ListaEnlazada<Vertex<E>> listVertex;

    public GrafoDirigidoListLink() {
        listVertex = new ListaEnlazada<>();
    }

    // INSERTAR VERTICE 
    public void insertVertex(E dato) {
        //1ro buscamos el dato en la listaVertex del grafo
        if (searchVertex(dato) != null) {
            //si esta lanzamos excepcion
            throw new RuntimeException("El vértice ya existe");
        }
        //si no existe
        Vertex<E> nuevoVertex = new Vertex<>(dato); // creamos un nuevoVertex(dato)
        listVertex.insertLast(nuevoVertex);         // y lo añadimos a ListaVertex
    }

    // INSERTAR ARISTA EN GRAFO DIRIGIDO (con peso)
    public void insertEdge(E vertexO, E vertexD, int weight) {
        Vertex<E> origen = searchVertex(vertexO);
        Vertex<E> destino = searchVertex(vertexD);

        if (origen == null || destino == null) {
            throw new RuntimeException("Uno o ambos vértices no existen.");
        }

        // Verificar si ya existe una arista desde origen → destino
        if (searchEdge(vertexO, vertexD)) {
            System.out.println("La arista ya existe entre " + vertexO + " y " + vertexD);
            return;
        }

        // Insertamos solo una arista desde origen → destino
        Edge<E> nuevaArista = new Edge<>(destino, weight);
        origen.listAdj.insertLast(nuevaArista);
    }

    

    //BUSCAR UN VERTICE
    public Vertex<E> searchVertex(E data) {
        //creamos un nodo current que va apuntar al 1er nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        //mientras no lleguemos al final de la lista
        while (current != null) {
            //Comparamos el valor dentro del vértice actual con el dato que estamos buscando
            //current.getData() de Nodo:Nos da el vertice actual (un objeto Vertex<E>)
            //.getData() de Vertex:esto accede al dato dentro de ese vertice, que es de tipo E
            //.equals(data) de Vertex:esto compara ese dato con el dato que estas buscando, usando .equals
            if (current.getData().getData().equals(data)) {
                return current.getData();  //si se encontro retornamos el vertice (objeto Vertex)
            }
            current = current.getNext(); //si no se encontro se pasa al siguiente nodo de la lista
        }
        return null; //si ya se recorrio la lista y no se encontro, retonamos null
    }
    //BUSCAR UNA ARISTA
    public boolean searchEdge(E v, E z) {
        // buscamos el vertice que contiene el dato v
        Vertex<E> vertV = searchVertex(v);   
        // buscamos el vertice que contiene el dato z
        Vertex<E> vertZ = searchVertex(z);
        // si alguno de los dos vertices no existe, no puede haber arista
        if (vertV == null || vertZ == null) return false;
        // obtenemos el primer nodo de la lista de adyacencia del vertice v
        Nodo<Edge<E>> current = vertV.listAdj.getFirst();
        // recorremos la lista de aristas del vertice v
        while (current != null) {
            // si el destino de la arista actual es igual al vertice z
            if (current.getData().getRefDest().equals(vertZ)) {
                // entonces existe una arista entre v y z
                return true;
            }
            // pasamos al siguiente nodo en la lista
            current = current.getNext();
        }
        // si no encontramos la arista, retornamos false
        return false;
    }

    public String toString() {
        // obtenemos el primer nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        // inicializamos una cadena vacia para guardar el resultado
        String result = "";
        // recorremos la lista de vertices
        while (current != null) {
            // agregamos la representacion del vertice actual al resultado
            result += current.getData().toString();
            // pasamos al siguiente nodo de la lista
            current = current.getNext();
        }
        // retornamos el texto con todos los vertices y sus conexiones
        return result;
    }


    //ELIMINAR VERTICE EN GRAFO NO DIRIGIDO
    public void removeVertex(E v) {
        // buscamos el vertice que contiene el dato v
        Vertex<E> vertexToRemove = searchVertex(v);
        // si no existe el vertice, salimos del metodo
        if (vertexToRemove == null) return;
        // obtenemos el primer nodo de la lista de vertices
        Nodo<Vertex<E>> current = listVertex.getFirst();
        // recorremos todos los vertices del grafo
        while (current != null) {
            // en el nodo actual (vertice) buscamos en su lista de adyacencia el vertice a eliminar
            current.getData().listAdj.removeNodo(new Edge<>(vertexToRemove));
            // pasamos al siguiente vertice
            current = current.getNext();
        }
        // finalmente, eliminamos el vertice de la lista de vertices
        listVertex.removeNodo(vertexToRemove);
    }

    // ELIMINAR ARISTA EN GRAFO DIRIGIDO
    public void removeEdge(E vertexO, E vertexD) {
        Vertex<E> origen = searchVertex(vertexO);
        Vertex<E> destino = searchVertex(vertexD);

        if (origen == null || destino == null) return;

        // Eliminamos la arista solo desde origen → destino
        origen.listAdj.removeNodo(new Edge<>(destino));
    }



    //UPDATES

    public boolean updateVertex(E oldData, E newData) {
        Vertex<E> vertex = searchVertex(oldData);
        if (vertex == null) return false;

        // Verifica que no exista ya un vértice con newData
        if (searchVertex(newData) != null) return false;

        vertex.setData(newData);
        
        // Actualizar referencias en aristas salientes (no necesario en tu modelo)
        // Pero sí debes actualizar las aristas entrantes de otros vértices
        Nodo<Vertex<E>> actual = listVertex.getFirst();
        while (actual != null) {
            ListaEnlazada<Edge<E>> adj = actual.getData().listAdj;
            Nodo<Edge<E>> arco = adj.getFirst();
            while (arco != null) {
                if (arco.getData().getRefDest().equals(vertex)) {
                    // no cambia la referencia, pero cambia cómo se muestra en toString()
                    // y en posibles equals
                }
                arco = arco.getNext();
            }
            actual = actual.getNext();
        }

        return true;
    }

    
    public boolean updateEdgeWeight(E v1, E v2, int newWeight) {
        Vertex<E> origen = searchVertex(v1);
        Vertex<E> destino = searchVertex(v2);

        if (origen == null || destino == null) return false;

        Nodo<Edge<E>> arco = origen.listAdj.getFirst();
        while (arco != null) {
            if (arco.getData().getRefDest().equals(destino)) {
                // Crea una nueva arista con el mismo destino y nuevo peso
                arco.getData().setWeight(newWeight);
                return true;
            }
            arco = arco.getNext();
        }

        return false; // arista no encontrada
    }

  

    //RECORRIDO DE PROFUNDIDAD
    public void dfs(E v) {
        // buscamos el vertice inicial
        Vertex<E> start = searchVertex(v);
        if (start == null) {
            System.out.println("vertice no encontrado.");
            return;
        }
        // lista de vertices visitados
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        // usamos StackLink (pila con lista enlazada)
        StackLink<Vertex<E>> pila = new StackLink<>();
        try {
            pila.push(start); // agregamos el vertice inicial a la pila
            while (!pila.isEmpty()) {
                Vertex<E> actual = pila.pop(); // sacamos el ultimo vertice agregado
                if (visitados.search(actual) == -1) {// si no fue visitado
                    System.out.print(actual.getData() + " "); // mostramos el dato del vertice
                    visitados.insertLast(actual); // lo marcamos como visitado
                    Nodo<Edge<E>> adyacente = actual.listAdj.getFirst(); // recorremos sus aristas
                    while (adyacente != null) {
                        Vertex<E> destino = adyacente.getData().getRefDest();
                        if (visitados.search(destino) == -1) {
                            pila.push(destino); // si no fue visitado, lo agregamos a la pila
                        }
                        adyacente = adyacente.getNext(); // siguiente arista
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            System.out.println("error en dfs: " + e.getMessage());
        }
    }


    //MÉTODO BFS (RECORRRIDO EN ANCHURA DESDE EL VERTICE "v")
    public void bfs(E v) {
        Vertex<E> verticeInicial = searchVertex(v);
        if (verticeInicial == null) {
            System.out.println("El vértice inicial no existe.");
            return;
        }

        QueueLink<Vertex<E>> cola = new QueueLink<>();
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();

        cola.enqueue(verticeInicial);
        visitados.insertLast(verticeInicial);

        System.out.print("Recorrido en anchura: ");

        while (!cola.isEmpty()) {
            try {
                Vertex<E> verticeActual = cola.dequeue();
                System.out.print(verticeActual.getData() + " ");

                Nodo<Edge<E>> nodoArista = verticeActual.listAdj.getFirst();
                while (nodoArista != null) {
                    Vertex<E> verticeVecino = nodoArista.getData().getRefDest();

                    if (visitados.search(verticeVecino) == -1) {
                        cola.enqueue(verticeVecino);
                        visitados.insertLast(verticeVecino);
                    }

                    nodoArista = nodoArista.getNext();
                }
            } catch (ExceptionIsEmpty e) {
                System.out.println("Error en la cola: " + e.getMessage());
            }
        }

        System.out.println();
    }

    // Clase auxiliar para guardar pares hijo -> padre
    private class Par {
        Vertex<E> hijo;
        Vertex<E> padre;

        public Par(Vertex<E> hijo, Vertex<E> padre) {
            this.hijo = hijo;
            this.padre = padre;
        }
    }

    private Vertex<E> buscarPadre(Vertex<E> hijo, ListaEnlazada<Par> padres) {
        Nodo<Par> actual = padres.getFirst();
        while (actual != null) {
            if (actual.getData().hijo.equals(hijo)) {
                return actual.getData().padre;
            }
            actual = actual.getNext();
        }
        return null;
    }


    private class ParValor {
        Vertex<E> vertice;
        int distancia;

        public ParValor(Vertex<E> vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }

    private int getDistancia(ListaEnlazada<ParValor> distancias, Vertex<E> v) {
        Nodo<ParValor> actual = distancias.getFirst();
        while (actual != null) {
            if (actual.getData().vertice.equals(v)) {
                return actual.getData().distancia;
            }
            actual = actual.getNext();
        }
        return Integer.MAX_VALUE;
    }

    private void actualizarDistancia(ListaEnlazada<ParValor> distancias, Vertex<E> v, int nuevaDistancia) {
        Nodo<ParValor> actual = distancias.getFirst();
        while (actual != null) {
            if (actual.getData().vertice.equals(v)) {
                actual.getData().distancia = nuevaDistancia;
                return;
            }
            actual = actual.getNext();
        }
    }

    private void reemplazarPadre(ListaEnlazada<Par> padres, Vertex<E> hijo, Vertex<E> nuevoPadre) {
        Nodo<Par> actual = padres.getFirst();
        while (actual != null) {
            if (actual.getData().hijo.equals(hijo)) {
                actual.getData().padre = nuevoPadre;
                return;
            }
            actual = actual.getNext();
        }
        padres.insertLast(new Par(hijo, nuevoPadre));
    }


    // MÉTODO DIJKSTRA (retorna un stack con la ruta más corta desde el vértice v hasta w)
    public StackLink<E> Dijkstra(E v, E w) {
        StackLink<E> camino = new StackLink<>();
        Vertex<E> origen = searchVertex(v);
        Vertex<E> destino = searchVertex(w);

        if (origen == null || destino == null) {
            System.out.println("Uno de los vértices no existe.");
            return camino;
        }

        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        ListaEnlazada<Par> padres = new ListaEnlazada<>();
        ListaEnlazada<ParValor> distancias = new ListaEnlazada<>();
        PriorityQueueLinkSort<Vertex<E>, Integer> cola = new PriorityQueueLinkSort<>();

        Nodo<Vertex<E>> actualV = listVertex.getFirst();
        while (actualV != null) {
            Vertex<E> vert = actualV.getData();
            int distanciaInicial = vert.equals(origen) ? 0 : Integer.MAX_VALUE;
            distancias.insertLast(new ParValor(vert, distanciaInicial));
            actualV = actualV.getNext();
        }

        cola.enqueue(origen, 0);
        padres.insertLast(new Par(origen, null));

        while (!cola.isEmpty()) {
            Vertex<E> actual;
            try {
                actual = cola.dequeue();
            } catch (ExceptionIsEmpty e) {
                break;
            }

            if (visitados.search(actual) != -1) continue;
            visitados.insertLast(actual);

            Nodo<Edge<E>> arista = actual.listAdj.getFirst();
            while (arista != null) {
                Vertex<E> vecino = arista.getData().getRefDest();
                int peso = arista.getData().getWeight();

                if (visitados.search(vecino) == -1) {
                    int distanciaActual = getDistancia(distancias, actual);
                    int nuevaDistancia = distanciaActual + peso;

                    if (nuevaDistancia < getDistancia(distancias, vecino)) {
                        actualizarDistancia(distancias, vecino, nuevaDistancia);
                        cola.enqueue(vecino, nuevaDistancia);
                        reemplazarPadre(padres, vecino, actual);
                    }
                }

                arista = arista.getNext();
            }
        }

        if (buscarPadre(destino, padres) == null && !origen.equals(destino)) {
            System.out.println("No existe un camino de " + v + " a " + w);
            return camino;
        }

        Vertex<E> actual = destino;
        while (actual != null) {
            camino.push(actual.getData());
            actual = buscarPadre(actual, padres);
        }

        return camino;
    }



    public boolean hasCycle() {
        ListaEnlazada<Vertex<E>> visitados = new ListaEnlazada<>();
        ListaEnlazada<Vertex<E>> enRecursion = new ListaEnlazada<>();

        Nodo<Vertex<E>> actual = listVertex.getFirst();
        while (actual != null) {
            Vertex<E> v = actual.getData();
            if (!visitados.contains(v)) {
                if (dfsDetectCycle(v, visitados, enRecursion)) {
                    return true; // ciclo encontrado
                }
            }
            actual = actual.getNext();
        }

        return false; // no hay ciclos
    }


    private boolean dfsDetectCycle(Vertex<E> v, ListaEnlazada<Vertex<E>> visitados, ListaEnlazada<Vertex<E>> enRecursion) {
        visitados.insertLast(v);
        enRecursion.insertLast(v);

        Nodo<Edge<E>> arco = v.listAdj.getFirst();
        while (arco != null) {
            Vertex<E> vecino = arco.getData().getRefDest();
            if (!visitados.contains(vecino)) {
                if (dfsDetectCycle(vecino, visitados, enRecursion)) {
                    return true;
                }
            } else if (enRecursion.contains(vecino)) {
                return true; // ciclo detectado
            }
            arco = arco.getNext();
        }

        enRecursion.removeNodo(v);
        return false;
    }



}
