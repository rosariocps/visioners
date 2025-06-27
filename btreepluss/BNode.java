package btreepluss;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> implements Comparable<BNode<E>> {
    public int count; // Cantidad de claves actuales
    public ArrayList<E> keys; // Lista de claves
    public ArrayList<BNode<E>> childs; // Lista de hijos
    public boolean isLeaf; // Bandera para identificar si es hoja
    public BNode<E> next; // Apunta al siguiente nodo hoja (para recorrido B+)
    public BNode<E> prev; // Apunta al nodo hoja anterior

    public BNode(int n, boolean isLeaf) {
        this.keys = new ArrayList<>(n);
        this.childs = new ArrayList<>(n);
        this.count = 0;
        this.isLeaf = isLeaf;
        this.next = null;
        this.prev = null;

        // Inicializamos con valores nulos para evitar errores en uso de set()
        // llenamos keys con n valores null para que size sea n y poder usar set(i, valor) sin error
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }

        if (!isLeaf) {
            // en nodos internos, rellenamos childs con n valores null para permitir set(i, hijo)
            for (int i = 0; i < n; i++) {
                this.childs.add(null); // añadimos null en childs en posicion i
            }
        }
    }

    public boolean nodeFull(int maxKeys) {
        return count >= maxKeys;
    }

    public boolean nodeEmpty() {
        return count == 0;
    }

    public boolean searchNode(E data, int[] pos) {
        // inicializa pos[0] en 0 para empezar la busqueda desde la primera clave
        pos[0] = 0;
        // avanza mientras queden claves en el nodo y data sea mayor que la clave actual
        while (pos[0] < count && data.compareTo(keys.get(pos[0])) > 0) {
            pos[0]++; // mueve la posicion al siguiente indice
        }
        // si el nodo es hoja, comprobamos si en pos[0] hay exactamente data
        if (isLeaf) {
            // devuelve true solo si pos[0] es valida y la clave en esa posicion iguala a data qbuscamos
            return pos[0] < count && data.compareTo(keys.get(pos[0])) == 0;
        }
        // en nodos internos no verificamos presencia aqui, la busqueda continua en hijos
        return false;
    }

    // compara este nodo con otro segun su primera clave
    @Override
    public int compareTo(BNode<E> other) {
        // obtenemos la primera clave de cada nodo
        E thisKey  = this.keys.get(0);
        E otherKey = other.keys.get(0);

        // si alguna clave es null, consideramos nodos iguales
        if (thisKey == null || otherKey == null) {
            return 0; // o lanzar excepcion si prefieres
        }

        // devolvemos el resultado de comparar las dos claves
        return thisKey.compareTo(otherKey);
    }

}