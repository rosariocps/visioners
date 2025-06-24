package estructuras; // especifica que esta clase esta en el paquete estructuras

import modelos.AristaRuta; // importa la clase arista con origen, destino y peso
import java.util.ArrayList; // importa la implementacion de lista dinamica
import java.util.List; // importa la interfaz list

/**
 * lista de adyacencia para grafo dirigido y ponderado.
 */
public class ListaAdyacencia {
    // es una lista donde en cada nodo guardamos su lista de aristas salientes
    private List<List<AristaRuta>> lista; 

    // constructor: crea la lista principal vacia
    public ListaAdyacencia() {
        lista = new ArrayList<>(); //crea la lista principal vacía para empezar a añadir nodos
    }

    // añade un nuevo nodo (ubicacion) al grafo //
    public void agregarNodo() {
        lista.add(new ArrayList<>()); // añade una lista vacia para el nuevo nodo
    }

    // añade una ruta dirigida con peso //
    public void agregarRuta(int origen, int destino, int peso) {
        // verifica que origen y destino existan en el grafo
        if (origen < lista.size() && destino < lista.size()) {
            // añade una nueva arista a la lista del nodo origen
            lista.get(origen).add(new AristaRuta(origen, destino, peso));
        }
    }

    // elimina la ruta origen->destino si existe //
    public void eliminarRuta(int origen, int destino) {
        // solo opera si el nodo origen existe
        if (origen < lista.size()) {
            // remueve cualquier arista cuyo destino coincida
            lista.get(origen).removeIf(ar -> ar.obtenerDestino() == destino);
        }
    }

    // modifica (o define de nuevo) el peso de la ruta //
    public void modificarRuta(int origen, int destino, int nuevoPeso) {
        eliminarRuta(origen, destino); // primero elimina la ruta existente
        agregarRuta(origen, destino, nuevoPeso); // luego la vuelve a agregar con el nuevo peso
    }

    /** devuelve las rutas salientes de un nodo */
    public List<AristaRuta> obtenerRutas(int origen) {
        // si el nodo existe, retorna su lista de aristas
        if (origen < lista.size()) {
            return lista.get(origen);
        }
        // si no existe, retorna lista vacia para evitar null
        return new ArrayList<>();
    }

    /** numero total de nodos registrados en el grafo */
    public int cantidadNodos() {
        return lista.size(); // devuelve el tamano de la lista principal
    }
}
