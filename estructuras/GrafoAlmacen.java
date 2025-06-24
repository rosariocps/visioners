package estructuras; // indica que esta clase esta en el paquete estructuras

import modelos.NodoUbicacion; // importa la clase nodo que guarda nombre e indice
import modelos.AristaRuta; // importa la clase arista con origen, destino y peso
import java.util.ArrayList; // importa la implementacion de lista dinamica
import java.util.List; // importa la interfaz list

/**
 * grafo de ubicaciones usando lista de adyacencia.
 * incluye metodos para agregar, eliminar y modificar.
 */
public class GrafoAlmacen { // declara la clase que modela el grafo
    private List<NodoUbicacion> nodos; // lista de nodos (ubicaciones)
    private ListaAdyacencia adyacencia; // lista de adyacencia para rutas

    public GrafoAlmacen() { // constructor sin parametros
        nodos      = new ArrayList<>(); // inicializa lista de ubicaciones vacia
        adyacencia = new ListaAdyacencia(); // inicializa la lista de adyacencia
    }

    // agrega una nueva ubicacion (nodo) 
    public void agregarUbicacion(String nombre) {
        // crea y añade nodo con indice
        //usamos nodos.size() como indice del nuevo nodo porque size() devuelve el numero de nodos actuales 
        //ejemplo: si hay 3 nodos (índices 0,1,2), size() es 3, así el nuevo nodo recibe índice 3 y se añade en la posición 3 indice
        nodos.add(new NodoUbicacion(nombre, nodos.size())); 
        adyacencia.agregarNodo(); // añade una lista vacia de rutas para el nuevo nodo
    }

    // agrega o actualiza una ruta dirigida con peso 
    public void agregarRuta(int origen, int destino, int peso) {
        adyacencia.agregarRuta(origen, destino, peso); // llama al metodo de lista de adyacencia
    }

    // elimina la ruta origen→destino 
    public void eliminarRuta(int origen, int destino) {
        adyacencia.eliminarRuta(origen, destino); // llama al metodo que quita la arista
    }

    // cambia el peso de una ruta existente 
    public void modificarRuta(int origen, int destino, int nuevoPeso) {
        adyacencia.modificarRuta(origen, destino, nuevoPeso); // elimina y vuelve a agregar con nuevo peso
    }

    /**
     * elimina un nodo entero:
     * - quita sus rutas salientes (aristas que salen de ese nodo)
     * - quita todas las rutas que apunten a el (aristas que apuntan a ese nodo)
     */
    public void eliminarNodo(int indice) {
        // para asegurar que indice este entre 0 y cantidadNodos()-1
        if (indice < adyacencia.cantidadNodos()) { // verifica que el nodo exista
            adyacencia.obtenerRutas(indice).clear(); // borra todas las rutas salientes del nodo
            for (int i = 0; i < adyacencia.cantidadNodos(); i++) { // recorre todos los nodos
                adyacencia.eliminarRuta(i, indice); // elimina rutas entrantes hacia el nodo eliminado
            }
            // no marcamos nodos como null tras eliminarlos para evitar tener que comprobar null en todos los recorridos y algoritmos
        }
    }

    // lista de todas las ubicaciones */
    public List<NodoUbicacion> obtenerNodos() {
        return nodos; // devuelve la lista de nodos
    }

    // rutas salientes de un nodo 
    public List<AristaRuta> obtenerRutas(int origen) {
        return adyacencia.obtenerRutas(origen); // obtiene las aristas desde el nodo origen
    }

    // cuantos nodos hay 
    public int cantidadNodos() {
        return nodos.size(); // devuelve el tamaño de la lista de nodos
    }
}
