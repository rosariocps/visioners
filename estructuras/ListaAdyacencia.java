package estructuras;

import modelos.AristaRuta;
import java.util.ArrayList;
import java.util.List;

/**
 * Lista de adyacencia para grafo dirigido y ponderado.
 */
public class ListaAdyacencia {
    // Para cada nodo, guardamos su lista de aristas salientes
    private List<List<AristaRuta>> lista;

    public ListaAdyacencia() {
        lista = new ArrayList<>();
    }

    /** Añade un nuevo nodo (ubicación) al grafo */
    public void agregarNodo() {
        lista.add(new ArrayList<>());
    }

    /** Añade una ruta dirigida con peso */
    public void agregarRuta(int origen, int destino, int peso) {
        if (origen < lista.size() && destino < lista.size()) {
            lista.get(origen).add(new AristaRuta(origen, destino, peso));
        }
    }

    /** Elimina la ruta origen->destino si existe */
    public void eliminarRuta(int origen, int destino) {
        if (origen < lista.size()) {
            lista.get(origen).removeIf(ar -> ar.obtenerDestino() == destino);
        }
    }

    /** Modifica (o define de nuevo) el peso de la ruta */
    public void modificarRuta(int origen, int destino, int nuevoPeso) {
        eliminarRuta(origen, destino);
        agregarRuta(origen, destino, nuevoPeso);
    }

    /** Devuelve las rutas salientes de un nodo */
    public List<AristaRuta> obtenerRutas(int origen) {
        if (origen < lista.size()) {
            return lista.get(origen);
        }
        return new ArrayList<>();
    }

    /** Número total de nodos en el grafo */
    public int cantidadNodos() {
        return lista.size();
    }
}
