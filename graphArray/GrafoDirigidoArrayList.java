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


    // busca en listvertex un vertice cuyo dato coincida con data
    public Vertex<E> searchVertex(E data) {
        // recorremos cada vertice en la lista
        for (Vertex<E> vertex : listVertex) {
            // si el dato del vertice es igual a data, retornamos ese vertice
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        // si no encontramos ningun vertice con ese dato, devolvemos null
        return null;
    }


    // busca si existe una arista de v hacia z en el grafo
    public boolean searchEdge(E v, E z) {
        // obtenemos el vertice origen con dato v
        Vertex<E> vertV = searchVertex(v);
        // obtenemos el vertice destino con dato z
        Vertex<E> vertZ = searchVertex(z);

        // si alguno de los dos vertices no existe, no puede haber arista
        if (vertV == null || vertZ == null) return false;

        // recorremos la lista de adyacencia del vertice origen
        for (Edge<E> edge : vertV.listAdj) {
            // si el destino de la arista coincide con vertZ, existe la arista
            if (edge.getRefDest().equals(vertZ)) {
                return true;
            }
        }
        // si terminamos el bucle sin encontrar la arista, devolvemos false
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

    // modifica el dato de un vertice de oldData a newData en el grafo
    public boolean updateVertex(E oldData, E newData) {
        // busca el vertice con el dato antiguo
        Vertex<E> vertex = searchVertex(oldData);
        // si no existe, no se puede modificar
        if (vertex == null) return false;
        // si ya hay otro vertice con newData, evitamos duplicados
        if (searchVertex(newData) != null) return false;
        // actualizamos el dato del vertice
        vertex.setData(newData);
        // recorremos todos los vertices para actualizar referencias de aristas
        for (Vertex<E> actual : listVertex) {
            // por cada arista en la lista de adyacencia
            for (Edge<E> edge : actual.listAdj) {
                // si la arista apunta al vertice modificado
                if (edge.getRefDest().equals(vertex)) {
                    // aqui podria actualizarse algun dato asociado a la arista si hiciera falta
                }
            }
        }
        // modificacion realizada con exito
        return true;
    }


    // cambia el peso de la arista de v1 hacia v2 a newWeight
    public boolean updateEdgeWeight(E v1, E v2, int newWeight) {
        // buscamos el vertice origen con dato v1
        Vertex<E> origen = searchVertex(v1);
        // buscamos el vertice destino con dato v2
        Vertex<E> destino = searchVertex(v2);

        // si origen o destino no existen, no se puede modificar
        if (origen == null || destino == null) return false;

        // recorremos la lista de adyacencia del vertice origen
        for (Edge<E> edge : origen.listAdj) {
            // si encontramos la arista que apunta al vertice destino
            if (edge.getRefDest().equals(destino)) {
                // actualizamos el peso de esa arista
                edge.setWeight(newWeight);
                return true; // modificacion exitosa
            }
        }
        // no se encontro la arista correcta
        return false;
    }


    // imprime el grafo concatenando la representacion de cada vertice
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();  // builder para armar la cadena resultado
        for (Vertex<E> vertex : listVertex) {       // itera sobre cada vertice en la lista
            result.append(vertex.toString());       // agrega la cadena de cada vertice
        }
        return result.toString();                   // retorna la cadena completa del grafo
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


  
    // clase interna que asocia un vertice con su distancia acumulada desde el nodo origen
    private class ParValor {
        Vertex<E> vertice; // vertice al que corresponde esta distancia
        int distancia;     // distancia acumulada desde el origen hasta este vertice

        // constructor que inicializa el par con el vertice y su distancia
        public ParValor(Vertex<E> vertice, int distancia) {
            this.vertice = vertice;   // asigna el vertice
            this.distancia = distancia; // asigna la distancia acumulada
        }
    }



    // metodo que devuelve la distancia asociada al vertice v segun la lista de pares
    private int getDistancia(ArrayList<ParValor> distancias, Vertex<E> v) {
        // recorremos cada par de vertice y distancia en la lista
        for (ParValor pv : distancias) {
            // si el vertice del par coincide con v
            if (pv.vertice.equals(v)) {
                // retornamos la distancia asociada
                return pv.distancia;
            }
        }
        // si no se encontro, devolvemos el valor maximo entero (infinito)
        return Integer.MAX_VALUE;
    }

    // metodo que busca el vertice v en la lista y asigna nuevaDistancia a su par
    private void actualizarDistancia(ArrayList<ParValor> distancias, Vertex<E> v, int nuevaDistancia) {
        // recorremos cada par de vertice y distancia en la lista
        for (ParValor pv : distancias) {
            // si el vertice del par es igual a v
            if (pv.vertice.equals(v)) {
                // asignamos la nueva distancia al par correspondiente
                pv.distancia = nuevaDistancia;
                // salimos del metodo tras actualizar la distancia
                return;
            }
        }
    }


     // MÉTODO PRINCIPAL DIJKSTRA: Calcula el camino más corto desde el vértice 'v' a todos los vertices
    public void Dijkstra(E v) {
        // buscamos el vertice origen por su dato v
        Vertex<E> origen = searchVertex(v);
        // si el vertice no existe, mostramos mensaje y salimos
        if (origen == null) {
            System.out.println("El vértice no existe.");
            return;
        }

        // lista para llevar control de vertices ya visitados
        ArrayList<Vertex<E>> visitados = new ArrayList<>();
        // lista de pares vertice-distancia acumulada
        ArrayList<ParValor> distancias = new ArrayList<>();
        // cola de prioridad para seleccionar el siguiente vertice con menor distancia
        PriorityQueueLinkSort<Vertex<E>, Integer> cola = new PriorityQueueLinkSort<>();

        // inicializamos las distancias: 0 para el origen, infinito para los demas
        for (Vertex<E> vert : listVertex) {
            int distanciaInicial = vert.equals(origen) ? 0 : Integer.MAX_VALUE;
            distancias.add(new ParValor(vert, distanciaInicial));
        }

        // insertamos el vertice origen en la cola con prioridad 0
        cola.enqueue(origen, 0);

        // iteramos mientras queden vertices por procesar en la cola
        while (!cola.isEmpty()) {
            Vertex<E> actual;
            try {
                // sacamos el vertice con menor distancia acumulada
                actual = cola.dequeue();
            } catch (ExceptionIsEmpty e) {
                // si la cola esta vacia lanzando excepcion, salimos del bucle
                break;
            }

            // si ya procesamos este vertice, lo saltamos
            if (visitados.contains(actual)) continue;
            // marcamos el vertice actual como visitado
            visitados.add(actual);

            // recorremos las aristas salientes del vertice actual
            for (Edge<E> arista : actual.listAdj) {
                Vertex<E> vecino = arista.getRefDest(); // destino de la arista
                int peso = arista.getWeight();          // peso de la arista

                // si el destino aun no fue visitado
                if (!visitados.contains(vecino)) {
                    // obtenemos la distancia actual del vertice actual
                    int distanciaActual = getDistancia(distancias, actual);
                    // calculamos la distancia posible hacia el vecino
                    int nuevaDistancia = distanciaActual + peso;

                    // si esta ruta es mejor que la almacenada para el vecino
                    if (nuevaDistancia < getDistancia(distancias, vecino)) {
                        // actualizamos la distancia en la lista de distancias
                        actualizarDistancia(distancias, vecino, nuevaDistancia);
                        // encolamos el vecino con su nueva prioridad
                        cola.enqueue(vecino, nuevaDistancia);
                    }
                }
            }
        }

        // mostramos todas las distancias finales desde el origen
        System.out.println("Distancias desde " + v + ":");
        for (ParValor pv : distancias) {
            // si la distancia es infinito, lo marcamos como inalcanzable
            String salida = (pv.distancia == Integer.MAX_VALUE) ? "Inalcanzable" : String.valueOf(pv.distancia);
            System.out.println("- Hasta " + pv.vertice.getData() + ": " + salida);
        }
    }


    // detecta si existe al menos un ciclo en el grafo dirigido
    public boolean hasCycle() {
        // lista de vertices completamente visitados
        ArrayList<Vertex<E>> visitados = new ArrayList<>();
        // lista de vertices en la pila de recursion (camino actual)
        ArrayList<Vertex<E>> enRecursion = new ArrayList<>();

        // recorremos cada vertice del grafo
        for (Vertex<E> v : listVertex) {
            // si aun no hemos explorado este vertice
            if (!visitados.contains(v)) {
                // iniciamos DFS desde v; si detectamos ciclo, devolvemos true
                if (dfsDetectCycle(v, visitados, enRecursion)) {
                    return true; // ciclo encontrado
                }
            }
        }
        // si terminamos sin hallar ciclo, devolvemos false
        return false; // no hay ciclos
    }


    // rutina DFS que detecta ciclos usando la lista enRecursion como pila lógica
    private boolean dfsDetectCycle(Vertex<E> v, ArrayList<Vertex<E>> visitados, ArrayList<Vertex<E>> enRecursion) {
        visitados.add(v);       // marcamos v como visitado globalmente
        enRecursion.add(v);     // y lo añadimos al camino actual (pila lógica)

        // recorremos cada arista saliente de v
        for (Edge<E> arco : v.listAdj) {
            Vertex<E> vecino = arco.getRefDest(); // obtenemos el vertice destino
            if (!visitados.contains(vecino)) {
                // si el vecino no ha sido visitado, descendemos recursivamente
                if (dfsDetectCycle(vecino, visitados, enRecursion)) {
                    return true; // si cualquier llamada detecta ciclo, lo propagamos
                }
            } else if (enRecursion.contains(vecino)) {
                // si el vecino ya está en el camino actual, hay un ciclo
                return true; // ciclo detectado
            }
        }

        enRecursion.remove(v); // al salir de v, lo retiramos de la pila lógica
        return false;          // no se detectó ciclo en esta rama
    }


}
