package estructuras;

import utils.Cola;
import java.util.Arrays;

/**
 * Contiene métodos clásicos para recorrer el grafo:
 * - BFS para exploración por niveles.
 * - Dijkstra para rutas más cortas.
 */
public class AlgoritmosGrafo {

    /**
     * Recorre el grafo en anchura (BFS) desde el nodo 'inicio'.
     * @param grafo  la matriz de adyacencia del grafo
     * @param inicio índice del nodo donde comienza
     */
    public static void bfs(MatrizAdyacencia grafo, int inicio) {
        int n = grafo.obtenerMatriz().length;
        boolean[] visitado = new boolean[n];
        Cola cola = new Cola(n);

        visitado[inicio] = true;
        cola.encolar(inicio);

        System.out.print("BFS: ");
        while (!cola.estaVacia()) {
            int actual = cola.desencolar();
            System.out.print(actual + " ");

            for (int i = 0; i < n; i++) {
                if (grafo.obtenerPeso(actual, i) > 0 && !visitado[i]) {
                    visitado[i] = true;
                    cola.encolar(i);
                }
            }
        }
        System.out.println();
    }

    /**
     * Calcula distancias mínimas desde 'inicio' a todos los nodos (Dijkstra).
     * @param grafo  la matriz de adyacencia
     * @param inicio nodo de partida
     * @return arreglo de distancias mínimas
     */
    public static int[] dijkstra(MatrizAdyacencia grafo, int inicio) {
        int n = grafo.obtenerMatriz().length;
        int[] distancia = new int[n];
        boolean[] visitado = new boolean[n];

        Arrays.fill(distancia, Integer.MAX_VALUE);
        distancia[inicio] = 0;

        for (int esperado = 0; esperado < n; esperado++) {
            int u = -1, minDist = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && distancia[i] < minDist) {
                    minDist = distancia[i];
                    u = i;
                }
            }
            if (u == -1) break;
            visitado[u] = true;

            for (int v = 0; v < n; v++) {
                int peso = grafo.obtenerPeso(u, v);
                if (peso > 0 && !visitado[v]
                    && distancia[u] + peso < distancia[v]) {
                    distancia[v] = distancia[u] + peso;
                }
            }
        }

        return distancia;
    }
}
