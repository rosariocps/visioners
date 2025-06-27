package graphArray;

import exceptions.ExceptionIsEmpty;
import exceptions.ItemDuplicated;
import java.util.ArrayList;
import queuelink.PriorityQueueLinkSort;
import queuelink.QueueLink;
import stacklink.StackLink;

/* Esta clase implementa un grafo dirigido genérico usando listas de adyacencia.
   Sirve para representar ubicaciones (nodos) y rutas con peso (aristas) entre almacenes. */

public class GrafoDirigidoArrayList<E> {
    
    // Lista de todos los vértices del grafo
    protected ArrayList<Vertex<E>> listVertex;

    // CONSTRUCTOR
    public GrafoDirigidoArrayList() {
        listVertex = new ArrayList<>(); //Inicializa la lista de vértices vacía
    }

    // Método que devuelve una lista con los datos de todos los vértices del grafo
    // Devuelve todas las ubicaciones del mapa del almacén
    public ArrayList<E> getListVertices() {
        ArrayList<E> lista = new ArrayList<>(); // Creamos una lista vacía

        // Para cada vértice v que está dentro de la lista de vértices listVertex
        for (Vertex<E> v : listVertex) {
            // Obtenemos el dato del vértice (o sea, la ubicación del almacén)
            lista.add(v.getData()); // y lo agregamos a la nueva lista
        }
        return lista; //
    }


    // INSERTAR VÉRTICE: Insertar una nueva ubicación en el almacén
    public void insertVertex(E dato) throws ItemDuplicated {
        if (searchVertex(dato) != null) { // Primero buscamos el dato con el método searchVertex() y si lo encontramos:
            throw new ItemDuplicated("El vértice ya existe"); // Entonces nos saldrá la excepcion de Item Duplicado
        }
        //Caso contrario, creamos un vertice nuevo y lo agregamos a la lista de vertices
        listVertex.add(new Vertex<>(dato)); 
    }

    // INSERTAR ARISTA: Insertar una nueva ruta entre dos vertices (ubicaciones) en la lista adyacente
    public void insertEdge(E vertexO, E vertexD, int weight) throws ItemDuplicated{
        // Primero verificamos si existen los vertices de origen y destino
        Vertex<E> origen = searchVertex(vertexO); //searchVertex nos permite buscar un vertice a partir de su data
        Vertex<E> destino = searchVertex(vertexD);

        // Si uno de los dos vertices no existe lanzamos una excepcion para avisar que no se puede crear la ruta
        if (origen == null || destino == null) {
            throw new RuntimeException("Uno o ambos vértices no existen.");
        }

        if (searchEdge(vertexO, vertexD)) { // Luego buscamos si ya existe esa arista 
            throw new ItemDuplicated("La arista ya existe entre " + vertexO + " y " + vertexD);
        }

        // Si no existe, la insertamos
        origen.listAdj.add(new Edge<>(destino, weight));
    }


     // BUSCAR UN VERTICE
    public Vertex<E> searchVertex(E data) {
        for (Vertex<E> vertex : listVertex) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

    // BUSCAR UNA ARISTA
    public boolean searchEdge(E v, E z) {
        Vertex<E> vertV = searchVertex(v);
        Vertex<E> vertZ = searchVertex(z);

        if (vertV == null || vertZ == null) return false;

        for (Edge<E> edge : vertV.listAdj) {
            if (edge.getRefDest().equals(vertZ)) {
                return true;
            }
        }
        return false;
    }

    // ELIMINAR VÉRTICE
    public void removeVertex(E v) {
        // Primero buscamos el vértice a eliminar usando el método searchVertex
        Vertex<E> vertexToRemove = searchVertex(v);

        // Si no se encuentra el vértice (es null), no hacemos nada y salimos del método
        if (vertexToRemove == null) return;

        // Recorremos todos los vértices del grafo
        for (Vertex<E> vertex : listVertex) {
            // Para cada vértice, recorremos su lista de adyacencia
            for (int i = 0; i < vertex.listAdj.size(); i++) {
                Edge<E> edge = vertex.listAdj.get(i);

                // Si encontramos una arista cuya referencia de destino es el vértice a eliminar
                if (edge.getRefDest().equals(vertexToRemove)) {
                    // La eliminamos
                    vertex.listAdj.remove(i);
                    // Disminuimos el índice para no saltarnos ningún elemento
                    i--;
                }
            }
        }

        // Finalmente eliminamos el vértice de la lista de vértices
        listVertex.remove(vertexToRemove);
    }

    // ELIMINAR ARISTA
    public void removeEdge(E vertexO, E vertexD) {
        // Buscamos el vértice de origen y de destino
        Vertex<E> origen = searchVertex(vertexO);
        Vertex<E> destino = searchVertex(vertexD);

        // Si uno de los dos no existe, salimos del método sin hacer nada
        if (origen == null || destino == null) return;

        // Recorremos la lista de adyacencia del vértice de origen
        for (int i = 0; i < origen.listAdj.size(); i++) {
            Edge<E> edge = origen.listAdj.get(i);

            // Si encontramos una arista cuya referencia de destino es igual al vértice destino
            if (edge.getRefDest().equals(destino)) {
                // Eliminamos esa arista
                origen.listAdj.remove(i);
                // Disminuimos el índice para evitar saltar elementos tras la eliminación
                i--;
            }
        }
    }

    // MODIFICAR VERTICE
    public boolean updateVertex(E oldData, E newData) {
        Vertex<E> vertex = searchVertex(oldData);
        if (vertex == null) return false;

        if (searchVertex(newData) != null) return false;

        vertex.setData(newData);

        for (Vertex<E> actual : listVertex) {
            for (Edge<E> edge : actual.listAdj) {
                if (edge.getRefDest().equals(vertex)) {
                    
                }
            }
        }

        return true;
    }

    // MODIFICAR PESO DE ARISTA
    public boolean updateEdgeWeight(E v1, E v2, int newWeight) {
        Vertex<E> origen = searchVertex(v1);
        Vertex<E> destino = searchVertex(v2);

        if (origen == null || destino == null) return false;

        for (Edge<E> edge : origen.listAdj) {
            if (edge.getRefDest().equals(destino)) {
                edge.setWeight(newWeight);
                return true;
            }
        }
        return false;
    }

    // IMPRIMIR GRAFO
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex<E> vertex : listVertex) {
            result.append(vertex.toString());
        }
        return result.toString();
    }

    // MÉTODO PARA REALIZAR UNA BÚSQUEDA EN PROFUNDIDAD (DFS)
    public void dfs(E v) {
        // Primero buscamos el vértice a partir de la data que se pasa como parámetro
        Vertex<E> start = searchVertex(v);

        // Verificamos si ese vértice realmente existe; si no existe, simplemente mostramos un mensaje y detenemos el recorrido
        if (start == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        // Creamos una lista para ir guardando los vértices que ya hemos visitado, esto para no repetirnos en el recorrido
        ArrayList<Vertex<E>> visitados = new ArrayList<>();

        // Creamos una pila, porque DFS funciona con una estructura LIFO (último en entrar, primero en salir)
        StackLink<Vertex<E>> pila = new StackLink<>();

        try {
            // Insertamos el vértice de inicio en la pila, porque ahí comenzará todo el recorrido
            pila.push(start);

            // Entramos en un bucle que se repite mientras la pila tenga elementos, es decir, mientras haya vértices por recorrer
            while (!pila.isEmpty()) {
                // Sacamos el último vértice que hemos insertado en la pila
                Vertex<E> actual = pila.pop();

                // Verificamos si ese vértice ya fue visitado; si no lo fue, entonces lo procesamos
                if (!visitados.contains(actual)) {
                    // Imprimimos su contenido en consola (su data)
                    System.out.print(actual.getData() + " ");

                    // Lo marcamos como visitado para no volver a recorrerlo
                    visitados.add(actual);

                    // Ahora revisamos todas las aristas (rutas) que salen desde ese vértice actual
                    for (Edge<E> arista : actual.listAdj) {
                        // Obtenemos el vértice de destino al que apunta esa arista
                        Vertex<E> destino = arista.getRefDest();

                        // Si ese vértice aún no ha sido visitado, lo agregamos a la pila para seguir el recorrido desde ahí más adelante
                        if (!visitados.contains(destino)) {
                            pila.push(destino);
                        }
                    }
                }
            }
        } catch (ExceptionIsEmpty e) {
            // En caso ocurra un error con la pila (como intentar hacer pop cuando está vacía), lo capturamos y mostramos el mensaje
            System.out.println("Error en dfs: " + e.getMessage());
        }
    }


    // MÉTODO PARA REALIZAR UN RECORRIDO EN ANCHURA (BFS)
    public void bfs(E v) {
        // Lo primero que hacemos es buscar el vértice inicial, usando la data que se pasó como parámetro
        Vertex<E> verticeInicial = searchVertex(v);

        // Verificamos si el vértice inicial realmente existe; si no existe, mostramos un mensaje y salimos del método
        if (verticeInicial == null) {
            System.out.println("El vértice inicial no existe.");
            return;
        }

        // Creamos una cola, porque BFS trabaja con una estructura FIFO (primero en entrar, primero en salir)
        QueueLink<Vertex<E>> cola = new QueueLink<>();

        // Creamos una lista para guardar los vértices que ya hemos visitado, para evitar repetirlos
        ArrayList<Vertex<E>> visitados = new ArrayList<>();

        // Insertamos el vértice inicial en la cola y también lo agregamos a la lista de visitados
        cola.enqueue(verticeInicial);
        visitados.add(verticeInicial);

        // Mostramos el mensaje inicial para indicar el tipo de recorrido
        System.out.print("Recorrido en anchura: ");

        // Entramos en un bucle que se ejecutará mientras haya vértices en la cola por procesar
        while (!cola.isEmpty()) {
            try {
                // Sacamos el primer vértice de la cola, ese será el actual que vamos a procesar
                Vertex<E> verticeActual = cola.dequeue();

                // Imprimimos su contenido (su data) por consola
                System.out.print(verticeActual.getData() + " ");

                // Recorremos todas las aristas del vértice actual
                for (Edge<E> arista : verticeActual.listAdj) {
                    // Obtenemos el vértice vecino (el de destino de la arista)
                    Vertex<E> verticeVecino = arista.getRefDest();

                    // Si ese vecino aún no ha sido visitado, lo agregamos a la cola y lo marcamos como visitado
                    if (!visitados.contains(verticeVecino)) {
                        cola.enqueue(verticeVecino);
                        visitados.add(verticeVecino);
                    }
                }

            } catch (ExceptionIsEmpty e) {
                // Si ocurre un error al intentar sacar de la cola (como si estuviera vacía por error), mostramos el mensaje
                System.out.println("Error en la cola: " + e.getMessage());
            }
        }

        // Saltamos línea al final del recorrido
        System.out.println();
    }


  
    //  CLASE INTERNA ParValor: Esta clase almacena 
     //un vértice y su distancia acumulada desde el origen
    private class ParValor {
        Vertex<E> vertice;
        int distancia;

        public ParValor(Vertex<E> vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }


    /// MÉTODO getDistancia: Busca en la lista de distancias cuál es la distancia actual asociada al vértice 'v'
    private int getDistancia(ArrayList<ParValor> distancias, Vertex<E> v) {
        for (ParValor pv : distancias) {
            if (pv.vertice.equals(v)) {
                return pv.distancia;
            }
        }
        return Integer.MAX_VALUE;
    }

    // MÉTODO actualizarDistancia: Reemplaza la distancia del vértice 'v' por la nueva que se le pasa
    private void actualizarDistancia(ArrayList<ParValor> distancias, Vertex<E> v, int nuevaDistancia) {
        for (ParValor pv : distancias) {
            if (pv.vertice.equals(v)) {
                pv.distancia = nuevaDistancia;
                return;
            }
        }
    }

     // MÉTODO PRINCIPAL DIJKSTRA: Calcula el camino más corto desde el vértice 'v' a todos los vertices
    public void Dijkstra(E v) {
        Vertex<E> origen = searchVertex(v);
        if (origen == null) {
            System.out.println("El vértice no existe.");
            return;
        }

        ArrayList<Vertex<E>> visitados = new ArrayList<>();
        ArrayList<ParValor> distancias = new ArrayList<>();
        PriorityQueueLinkSort<Vertex<E>, Integer> cola = new PriorityQueueLinkSort<>();

        // Inicializamos las distancias: 0 para el origen, infinito para los demás
        for (Vertex<E> vert : listVertex) {
            int distanciaInicial = vert.equals(origen) ? 0 : Integer.MAX_VALUE;
            distancias.add(new ParValor(vert, distanciaInicial));
        }

        // Insertamos el origen en la cola con prioridad 0
        cola.enqueue(origen, 0);

        while (!cola.isEmpty()) {
            Vertex<E> actual;
            try {
                actual = cola.dequeue(); // Sacamos el vértice con menor distancia
            } catch (ExceptionIsEmpty e) {
                break;
            }

            if (visitados.contains(actual)) continue;
            visitados.add(actual);

            for (Edge<E> arista : actual.listAdj) {
                Vertex<E> vecino = arista.getRefDest();
                int peso = arista.getWeight();

                if (!visitados.contains(vecino)) {
                    int distanciaActual = getDistancia(distancias, actual);
                    int nuevaDistancia = distanciaActual + peso;

                    if (nuevaDistancia < getDistancia(distancias, vecino)) {
                        actualizarDistancia(distancias, vecino, nuevaDistancia);
                        cola.enqueue(vecino, nuevaDistancia);
                    }
                }
            }
        }

        // Mostramos las distancias finales
        System.out.println("Distancias desde " + v + ":");
        for (ParValor pv : distancias) {
            System.out.println("- Hasta " + pv.vertice.getData() + ": " +
                (pv.distancia == Integer.MAX_VALUE ? "Inalcanzable" : pv.distancia));
        }
    }


    public boolean hasCycle() {
        ArrayList<Vertex<E>> visitados = new ArrayList<>();
        ArrayList<Vertex<E>> enRecursion = new ArrayList<>();

        for (Vertex<E> v : listVertex) {
            if (!visitados.contains(v)) {
                if (dfsDetectCycle(v, visitados, enRecursion)) {
                    return true; // ciclo encontrado
                }
            }
        }

        return false; // no hay ciclos
    }

    private boolean dfsDetectCycle(Vertex<E> v, ArrayList<Vertex<E>> visitados, ArrayList<Vertex<E>> enRecursion) {
        visitados.add(v);
        enRecursion.add(v);

        for (Edge<E> arco : v.listAdj) {
            Vertex<E> vecino = arco.getRefDest();
            if (!visitados.contains(vecino)) {
                if (dfsDetectCycle(vecino, visitados, enRecursion)) {
                    return true;
                }
            } else if (enRecursion.contains(vecino)) {
                return true; // ciclo detectado
            }
        }

        enRecursion.remove(v);
        return false;
    }

}
