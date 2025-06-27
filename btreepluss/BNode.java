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
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }

        if (!isLeaf) {
            for (int i = 0; i < n; i++) {
                this.childs.add(null);
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
        pos[0] = 0;
        while (pos[0] < count && data.compareTo(keys.get(pos[0])) > 0) {
            pos[0]++;
        }
        if (isLeaf) {
            return pos[0] < count && data.compareTo(keys.get(pos[0])) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(BNode<E> other) {
        E thisKey = this.keys.get(0);
        E otherKey = other.keys.get(0);

        if (thisKey == null || otherKey == null) {
            return 0; // o puedes lanzar una excepción si prefieres
        }

        return thisKey.compareTo(otherKey);
    }
}