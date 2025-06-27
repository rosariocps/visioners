package almacen;

import btreepluss.BTreePluss;
import almacen.Producto;

public class UbicacionAlmacen {
    private String nombre;
    private BTreePluss<Producto> arbolProductos;

    // Constructor que recibe el nombre de la ubicación y el orden del árbol B+
    public UbicacionAlmacen(String nombre, int ordenArbol) {
        this.nombre = nombre;
        this.arbolProductos = new BTreePluss<>(ordenArbol);
    }

    // Devuelve el nombre de la ubicación (ej. "Estantería A")
    public String getNombre() {
        return nombre;
    }

    // Devuelve el árbol B+ asociado a esta ubicación
    public BTreePluss<Producto> getArbolProductos() {
        return arbolProductos;
    }

    // Sobrescribimos toString para mostrar el nombre directamente
    @Override
    public String toString() {
        return nombre;
    }
}
