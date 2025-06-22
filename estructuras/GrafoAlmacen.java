package estructuras;

import modelos.NodoUbicacion;
import modelos.AristaRuta;
import java.util.ArrayList;
import java.util.List;

/**
 * Grafo de ubicaciones usando lista de adyacencia.
 * Incluye métodos para agregar, eliminar y modificar.
 */
public class GrafoAlmacen {
    private List<NodoUbicacion> nodos;
    private ListaAdyacencia adyacencia;

    public GrafoAlmacen() {
        nodos      = new ArrayList<>();
        adyacencia = new ListaAdyacencia();
    }

    /** Agrega una nueva ubicación (nodo) */
    public void agregarUbicacion(String nombre) {
        nodos.add(new NodoUbicacion(nombre, nodos.size()));
        adyacencia.agregarNodo();
    }

    /** Agrega o actualiza una ruta dirigida con peso */
    public void agregarRuta(int origen, int destino, int peso) {
        adyacencia.agregarRuta(origen, destino, peso);
    }

    /** Elimina la ruta origen→destino */
    public void eliminarRuta(int origen, int destino) {
        adyacencia.eliminarRuta(origen, destino);
    }

    /** Cambia el peso de una ruta existente */
    public void modificarRuta(int origen, int destino, int nuevoPeso) {
        adyacencia.modificarRuta(origen, destino, nuevoPeso);
    }

    /**
     * Elimina un nodo entero:
     * - quita sus rutas salientes
     * - quita todas las rutas que apunten a él
     */
    public void eliminarNodo(int indice) {
        if (indice < adyacencia.cantidadNodos()) {
            adyacencia.obtenerRutas(indice).clear();
            for (int i = 0; i < adyacencia.cantidadNodos(); i++) {
                adyacencia.eliminarRuta(i, indice);
            }
            // Nota: el objeto NodoUbicacion queda en la lista; podrías marcarlo null.
        }
    }

    /** Lista de todas las ubicaciones */
    public List<NodoUbicacion> obtenerNodos() {
        return nodos;
    }

    /** Rutas salientes de un nodo */
    public List<AristaRuta> obtenerRutas(int origen) {
        return adyacencia.obtenerRutas(origen);
    }

    /** Cuántos nodos hay */
    public int cantidadNodos() {
        return nodos.size();
    }
}
