package estructuras; 

import modelos.AristaRuta; // importa la clase arista que contiene origen, destino y peso
import java.util.*; // importa colecciones como listas, pilas y colas

/**
 * algoritmos:
 * - bfs y dfs
 * - dijkstra
 * - deteccion de ciclos
 * - componentes conexas (grafo no dirigido)
 * - deteccion de nodos aislados
 */
public class AlgoritmosGrafo { // declara la clase que agrupa todos los algoritmos

    // recorre el grafo en anchura desde el nodo 'inicio'
    public static void bfs(GrafoAlmacen grafo, int inicio) {
        int n = grafo.cantidadNodos(); // obtiene numero total de nodos
        boolean[] visit = new boolean[n]; // arreglo que marca si un nodo ya se visito
        Queue<Integer> cola = new LinkedList<>(); // cola para el recorrido bfs
        visit[inicio] = true; // marca el nodo inicial como visitado
        cola.add(inicio); // encola el nodo inicial

        System.out.print("bfs: "); // imprime etiqueta antes del recorrido
        while (!cola.isEmpty()) { // mientras la cola no este vacia
            int u = cola.poll(); // desencola el siguiente nodo
            System.out.print(u + " "); // imprime el nodo actual
            for (AristaRuta ar : grafo.obtenerRutas(u)) { // por cada arista saliente de u
                int v = ar.obtenerDestino(); // obtiene el nodo destino de la arista
                if (!visit[v]) { // si el destino no ha sido visitado
                    visit[v] = true; // marca destino como visitado
                    cola.add(v); // encola el destino para explorar luego
                }
            }
        }
        System.out.println(); // salta a nueva linea despues de imprimir todo el recorrido
    }

    // recorre el grafo en profundidad desde el nodo 'inicio'
    public static void dfs(GrafoAlmacen grafo, int inicio) {
        int n = grafo.cantidadNodos(); // obtiene numero de nodos
        boolean[] visit = new boolean[n]; // arreglo de nodos visitados
        System.out.print("dfs: "); // imprime etiqueta antes del recorrido
        dfsRec(grafo, inicio, visit); // llama al metodo recursivo para el recorrido
        System.out.println(); // nueva linea al terminar
    }

    // metodo recursivo que implementa el recorrido en profundidad
    private static void dfsRec(GrafoAlmacen grafo, int u, boolean[] visit) {
        visit[u] = true; // marca el nodo actual como visitado
        System.out.print(u + " "); // imprime el nodo actual
        for (AristaRuta ar : grafo.obtenerRutas(u)) { // por cada arista saliente de u
            int v = ar.obtenerDestino(); // obtiene el nodo destino
            if (!visit[v]) { // si no ha sido visitado
                dfsRec(grafo, v, visit); // explora recursivamente desde v
            }
        }
    }

    // calcula distancias minimas desde 'inicio' usando el algoritmo de dijkstra
    public static int[] dijkstra(GrafoAlmacen grafo, int inicio) {
        int n = grafo.cantidadNodos(); // numero de nodos
        int[] dist = new int[n]; // arreglo de distancias minimas
        boolean[] used = new boolean[n]; // marca si el nodo ya se proceso
        Arrays.fill(dist, Integer.MAX_VALUE); // inicializa distancias a infinito
        dist[inicio] = 0; // distancia del nodo inicial a si mismo es 0

        for (int i = 0; i < n; i++) { // repite n veces
            int u = -1, mejor = Integer.MAX_VALUE; // busca el nodo no usado con menor distancia
            for (int j = 0; j < n; j++) { // recorre todos los nodos
                if (!used[j] && dist[j] < mejor) { // si no se uso y su distancia es menor
                    mejor = dist[j]; // actualiza mejor distancia
                    u = j; // actualiza nodo candidato
                }
            }
            if (u < 0) break; // si no encontro nodo valido sale del bucle
            used[u] = true; // marca el nodo como procesado
            for (AristaRuta ar : grafo.obtenerRutas(u)) { // recorre rutas salientes de u
                int v = ar.obtenerDestino(), w = ar.obtenerPeso(); // obtiene destino y peso
                if (dist[u] + w < dist[v]) { // si pasando por u mejora distancia
                    dist[v] = dist[u] + w; // actualiza la distancia minima
                }
            }
        }
        return dist; // devuelve el arreglo con las distancias minimas
    }

    // detecta si existe algun ciclo en el grafo dirigido
    public static boolean detectarCiclos(GrafoAlmacen grafo) {
        int n = grafo.cantidadNodos(); // numero de nodos
        boolean[] visit = new boolean[n]; // marca nodos visitados
        boolean[] stack = new boolean[n]; // pila logica para deteccion de ciclos
        for (int i = 0; i < n; i++) { // para cada nodo
            if (!visit[i] && dfsCiclo(grafo, i, visit, stack)) { // si no esta visitado y detecta ciclo
                return true; // retorna true en cuanto encuentra un ciclo
            }
        }
        return false; // si recorre todo sin encontrar retorna false
    }

    // metodo recursivo auxiliar para detectar ciclos
    private static boolean dfsCiclo(GrafoAlmacen grafo, int u, boolean[] visit, boolean[] stack) {
        visit[u] = true; // marca nodo como visitado
        stack[u] = true; // añade nodo a la pila de recursion
        for (AristaRuta ar : grafo.obtenerRutas(u)) { // por cada ruta saliente
            int v = ar.obtenerDestino(); // obtiene nodo destino
            if (!visit[v] && dfsCiclo(grafo, v, visit, stack)) { // si no visitado y ciclo en recursion
                return true; // ciclo encontrado
            }
            if (stack[v]) { // si destino ya esta en la pila actual
                return true; // ciclo encontrado
            }
        }
        stack[u] = false; // retira nodo de la pila al regresar
        return false; // no encontro ciclo en esta rama
    }

    // obtiene las componentes conexas tratando el grafo como no dirigido
    public static List<List<Integer>> componentesConexas(GrafoAlmacen grafo) {
        int n = grafo.cantidadNodos(); // numero de nodos
        // construye lista de adyacencia no dirigida
        List<List<Integer>> und = new ArrayList<>(); // lista temporal
        for (int i = 0; i < n; i++) und.add(new ArrayList<>()); // inicializa sublistas
        for (int u = 0; u < n; u++) { // para cada nodo u
            for (AristaRuta ar : grafo.obtenerRutas(u)) { // por cada ruta saliente
                int v = ar.obtenerDestino(); // obtiene destino
                und.get(u).add(v); // añade v a lista de u
                und.get(v).add(u); // añade u a lista de v (hace no dirigido)
            }
        }
        boolean[] visit = new boolean[n]; // marca nodos visitados
        List<List<Integer>> comps = new ArrayList<>(); // guardara las componentes
        for (int i = 0; i < n; i++) { // para cada nodo
            if (!visit[i]) { // si no se visito aun
                List<Integer> comp = new ArrayList<>(); // crea lista para la componente
                dfsComponente(und, i, visit, comp); // construye la componente
                comps.add(comp); // añade componente a la lista de todas
            }
        }
        return comps; // retorna lista de componentes conexas
    }

    // metodo recursivo auxiliar para rellenar cada componente conexa
    private static void dfsComponente(List<List<Integer>> und, int u, boolean[] visit, List<Integer> comp) {
        visit[u] = true; // marca nodo como visitado
        comp.add(u); // añade nodo a la componente actual
        for (int v : und.get(u)) { // por cada vecino en la lista no dirigida
            if (!visit[v]) dfsComponente(und, v, visit, comp); // explora recursivamente
        }
    }

    // devuelve la lista de nodos que no tienen aristas entrantes ni salientes
    public static List<Integer> nodosAislados(GrafoAlmacen grafo) {
        int n = grafo.cantidadNodos(); // numero de nodos
        boolean[] tiene = new boolean[n]; // marca nodos que participan en alguna arista
        for (int u = 0; u < n; u++) { // recorre cada nodo
            for (AristaRuta ar : grafo.obtenerRutas(u)) { // por cada ruta saliente
                tiene[u] = true; // marca nodo origen
                tiene[ar.obtenerDestino()] = true; // marca nodo destino
            }
        }
        List<Integer> aislados = new ArrayList<>(); // lista de nodos aislados
        for (int i = 0; i < n; i++) { // recorre todos los nodos
            if (!tiene[i]) aislados.add(i); // si no participa en aristas, es aislado
        }
        return aislados; // retorna la lista de nodos aislados
    }
}
