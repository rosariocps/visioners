package btreepluss;

import exceptions.ExceptionIsEmpty;
import exceptions.ItemDuplicated;
import exceptions.ItemNotFound;

public class BTreePluss<E extends Comparable<E>> {

    private BNode<E> root;
    private int order;
    private boolean up;
    private BNode<E> nDes;

    public BTreePluss(int orden) {
        this.order = orden;
        this.root = null;
        this.up = false;
        this.nDes = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int size() {
        return this.order;
    }

    public void insert(E cl) throws ItemDuplicated {
        // si no hay raiz, creamos una hoja y ponemos la clave en la posicion 0
        if (root == null) {
            root = new BNode<>(this.order, true); // raiz es hoja de orden this.order
            root.keys.set(0, cl);                 // insertamos cl en la primera posicion
            root.count = 1;                       // actualizamos el numero de claves
            return;                               // terminamos la insercion inicial
        }

        up = false;                               // bandera de split
        E mediana = push(this.root, cl);          // intentamos insertar y obtenemos mediana si hubo split

        // si push genero un split en la raiz (up==true), creamos nueva raiz interna
        if (up) {
            BNode<E> pnew = new BNode<>(this.order, false); //creamos un nodo nuevo con su orden (este sera la raiz)
            pnew.count = 1;                               //deicmos que solo va tener una clave q va ser la mediana
            pnew.keys.set(0, mediana);                     //colocamos la mediana en la posicion 0 de pnew
            pnew.childs.set(0, this.root);                 // primer hijo es la raiz antigua
            pnew.childs.set(1, nDes);                      //childs[1] xq aounta a nDes, q es el subarbol derecho creado en el split
            this.root = pnew;                              //x utlimo asignamos pnew como la nuevz raiz del arbol
        }
    }

    private E push(BNode<E> current, E cl) throws ItemDuplicated {
        // arreglo de una posicion para devolver la posicion dentro del nodo
        // donde esta o deberia ir la clave
        int pos[] = new int[1];
        
        // variable que guardara la clave mediana para subir al padre tras un split
        E mediana;

        // caso base: al pasar de la hoja (current == null), simulamos que cl sube como mediana
        if (current == null) {
            up = true;    // indicamos al nivel superior que hay que insertar una mediana
            nDes = null;  // aun no existe subarbol derecho para enlazar
            return cl;    // la clave cl sube como mediana hacia el padre
        }

        // si estamos en un nodo hoja
        if (current.isLeaf) {
            // verificamos si ya existe la clave en la hoja
            boolean found = current.searchNode(cl, pos);
            if (found) {
                up = false; 
                throw new ItemDuplicated("el elemento ya existe en el arbol: " + cl);
            }

            // si hay espacio en la hoja (menor que maxClaves = order-1)
            if (!current.nodeFull(this.order - 1)) {
                insertInLeaf(current, cl, pos[0]); // insertamos la clave en la posicion correcta
                up = false; 
                return null;  // no sube mediana
            } else {
                // la hoja esta llena, hay que dividirla y obtener mediana
                return splitLeaf(current, cl, pos[0]);
            }
        } else {
            // en nodo interno: buscamo posicion de hijo y descendemos recursivamente
            current.searchNode(cl, pos);
            mediana = push(current.childs.get(pos[0]), cl);

            if (up) {
                // si el hijo subio una mediana y el nodo interno no tiene espacio
                if (current.nodeFull(this.order - 1)) {
                    // dividimos el nodo interno y actualizamos mediana y nDes
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    // hay espacio para la mediana y el nuevo subarbol derecho
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;  // split resuelto
                }
            }
            return mediana;  // mediana si sube, o null si no
        }
    }

    // inserta la clave cl en el nodo hoja leaf en la posicion pos
    private void insertInLeaf(BNode<E> leaf, E cl, int pos) {
        // desplazamos cada clave desde leaf.count-1 hasta pos una posicion a la derecha
        for (int i = leaf.count; i > pos; i--) {
            leaf.keys.set(i, leaf.keys.get(i - 1));
        }
        // colocamos la nueva clave cl en la posicion pos
        leaf.keys.set(pos, cl);
        // incrementamos el contador de claves validas en el nodo hoja
        leaf.count++;
    }

    // inserta la clave cl y el subarbol derecho rd en el nodo interno current en la posicion k
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        // desplazamos las claves y los hijos a la derecha para dejar espacio en k
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));         // mueve clave i a i+1
            current.childs.set(i + 2, current.childs.get(i + 1)); // mueve hijo i+1 a i+2
        }
        // colocamos la nueva clave cl en la posicion k
        current.keys.set(k, cl);
        // enlazamos el subarbol derecho rd como hijo en la posicion k+1
        current.childs.set(k + 1, rd);
        // aumentamos el contador de claves validas en el nodo interno
        current.count++;
    }

    // divide un nodo interno current tras insertar cl en posicion k y devuelve la clave mediana
    private E dividedNode(BNode<E> current, E cl, int k) {
        // calculamos el indice de la mediana segun k y order
        int posMdna = (k <= this.order / 2) 
                    ? this.order / 2 
                    : this.order / 2 + 1;
        // creamos el nuevo nodo derecho tras el split
        nDes = new BNode<>(this.order, false);

        // movemos claves y punteros de hijos de current a nDes desde posMdna hasta order-2
        for (int i = posMdna; i < this.order - 1; i++) {
            // trasladamos la clave i de current a la posicion i-posMdna de nDes
            nDes.keys.set(i - posMdna, current.keys.get(i));
            // trasladamos el puntero de hijo i+1 de current a la posicion i-posMdna+1 de nDes
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        // ajustamos el numero de claves en nDes
        nDes.count = (this.order - 1) - posMdna;
        // reducimos el numero de claves en current al tamano antes de la mediana
        current.count = posMdna;

        // insertamos cl en el subarbol correcto antes de split definitivo
        if (k <= this.order / 2) {
            // si k cae en el bloque izquierdo, insertamos en current
            putNode(current, cl, nDes, k);
        } else {
            // si k cae en el bloque derecho, insertamos en nDes (ajustando indice)
            putNode(nDes, cl, nDes, k - posMdna);
        }

        // la mediana es la ultima clave de current antes de eliminarla
        E median = current.keys.get(current.count - 1);
        // ajustamos el puntero hijo 0 de nDes al hijo que quedo en current.childs[current.count]
        nDes.childs.set(0, current.childs.get(current.count));
        // reducimos count de current para quitar la mediana
        current.count--;

        // devolvemos la clave mediana para subirla al padre
        return median;
    }

    // divide un nodo hoja current tras intentar insertar cl en la posicion k y devuelve la primera clave del nodo derecho
    private E splitLeaf(BNode<E> current, E cl, int k) {
        // calculamos el punto de corte para la mediana segun k y order
        int posMdna = (k <= this.order / 2) 
                    ? this.order / 2 
                    : this.order / 2 + 1;
        // creamos el nuevo nodo hoja a la derecha
        nDes = new BNode<>(this.order, true);

        // movemos las claves desde posMdna hasta order-2 de current a nDes
        for (int i = posMdna; i < this.order - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
        }

        // ajustamos el numero de claves en cada hoja
        nDes.count = (this.order - 1) - posMdna;
        current.count = posMdna;

        // insertamos cl en current o en nDes segun corresponda
        if (k <= this.order / 2) {
            insertInLeaf(current, cl, k);           // clave va en la hoja izquierda
        } else {
            insertInLeaf(nDes, cl, k - posMdna);    // clave va en la hoja derecha (ajustando k)
        }

        // establecemos enlaces dobles entre hojas
        nDes.next = current.next;                  // nDes.next = siguiente hoja de current
        if (nDes.next != null) {
            nDes.next.prev = nDes;                // ajustamos prev del siguiente
        }
        current.next = nDes;                       // current.next apunta a nDes
        nDes.prev = current;                       // nDes.prev apunta a current

        // la mediana que sube al padre es la primera clave de la nueva hoja derecha
        E mediana = nDes.keys.get(0);
        up = true;                                 // indicamos que hubo split

        return mediana;                            // retornamos la mediana para el padre
    }

    // busca la clave data en el arbol y devuelve true si existe
    public boolean search(E data) {
        // iniciamos busqueda recursiva desde la raiz
        return searchRecursive(root, data);
    }

    private boolean searchRecursive(BNode<E> current, E data) {
        // si el nodo actual es nulo, no esta la clave
        if (current == null) {
            return false;
        }
        // arreglo de una posicion para recibir la posicion o rama donde continuar
        int[] pos = new int[1];
        // buscamos en este nodo; found sera true si llave esta en hoja
        boolean found = current.searchNode(data, pos);
        // si se encontro en el nodo actual, devolvemos true
        if (found) {
            return true;
        }
        // si no se encontro y existe un hijo en pos[0], descendemos recursivamente
        if (pos[0] < current.childs.size() && current.childs.get(pos[0]) != null) {
            return searchRecursive(current.childs.get(pos[0]), data);
        }
        // no se encontro y no hay por donde seguir
        return false;
    }

    // elimina recursivamente la clave key en el subárbol cuyo nodo raíz es node
    // retorna true si la eliminación se realizó, false si no se encontró en esta rama
    public boolean delete(BNode<E> node, E key) throws ItemNotFound {
        // si el nodo es null, no hay dónde buscar → elemento no encontrado
        if (node == null) {
            throw new ItemNotFound("elemento no encontrado: " + key);
        }

        // arreglo de tamaño 1 para recibir la posición dentro del nodo
        int pos[] = new int[1];
        // buscamos key en este nodo; found=true solo si está en hoja
        boolean found = node.searchNode(key, pos);

        if (found) {
            // si la clave está en un nodo interno
            if (!node.isLeaf) {
                // obtenemos el predecesor inmediato (mayor clave del subárbol izquierdo)
                E pred = getPredecessor(node, pos[0]);
                // reemplazamos key con el predecesor
                node.keys.set(pos[0], pred);
                // eliminamos recursivamente el predecesor en la rama izquierda
                return delete(node.childs.get(pos[0]), pred);
            } else {
                // si es hoja, quitamos la clave directamente
                removeKey(node, pos[0]);
                return true;
            }
        } else {
            // si key no está en este nodo
            if (node.isLeaf) {
                // en hoja y no encontrado → no hay nada que eliminar
                return false;
            } else {
                // descendemos al hijo donde debería estar key
                boolean isDeleted = delete(node.childs.get(pos[0]), key);
                // tras eliminar, si el hijo quedó por debajo del mínimo, reequilibramos
                if (node.childs.get(pos[0]).count < (order - 1) / 2) {
                    fix(node, pos[0]);
                }
                return isDeleted;
            }
        }
    }

    // elimina la clave en la posicion index de un nodo hoja, desplazando el resto hacia la izquierda
    private void removeKey(BNode<E> node, int index) {
        // desplazamos cada clave desde index+1 hasta count-1 una posicion a la izquierda
        for (int i = index; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
        }
        // limpiamos la ultima posicion, que ahora esta duplicada
        node.keys.set(node.count - 1, null);
        // reducimos el contador de claves validas
        node.count--;
    }

    // obtiene el predecesor inmediato (la mayor clave) del subarbol izquierdo en posicion index
    private E getPredecessor(BNode<E> node, int index) {
        // empezamos en el hijo a la izquierda de la clave en index
        BNode<E> current = node.childs.get(index);
        // bajamos hasta la hoja mas a la derecha de ese subarbol
        while (!current.isLeaf) {
            current = current.childs.get(current.count);
        }
        // devolvemos la ultima clave de esa hoja
        return current.keys.get(current.count - 1);
    }

    // fusiona el hijo derecho en el hijo izquierdo en el padre, eliminando el separador y ajustando punteros
    private void merge(BNode<E> parent, int index) {
        // obtenemos la hoja o subárbol izquierdo y derecho a fusionar
        BNode<E> left  = parent.childs.get(index);
        BNode<E> right = parent.childs.get(index + 1);

        // movemos todas las claves de right al final de left
        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + i, right.keys.get(i));
        }

        // si son hojas, ajustamos los enlaces next/prev para mantener la lista encadenada
        if (left.isLeaf && right.isLeaf) {
            left.next = right.next;                  // el siguiente de left salta a quien seguía a right
            if (right.next != null) {
                right.next.prev = left;              // ajustamos prev del siguiente para que apunte a left
            }
        }

        // el nuevo count de left es la suma de ambos
        left.count += right.count;

        // eliminamos el separador y el puntero a right en el nodo padre
        for (int i = index; i < parent.count - 1; i++) {
            // desplazamos claves del padre una posición a la izquierda
            parent.keys.set(i, parent.keys.get(i + 1));
            // desplazamos hijos del padre una posición atrás
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }

        // limpiamos la última clave e hijo del padre (ahora duplicados tras el desplazamiento)
        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        // reducimos el contador de claves del padre
        parent.count--;
    }

    // pide prestada una clave del hermano izquierdo cuando el nodo actual esta por debajo del minimo
    private void borrowFromLeft(BNode<E> parent, int index) {
        // obtenemos el hermano izquierdo y el nodo actual
        BNode<E> left    = parent.childs.get(index - 1);
        BNode<E> current = parent.childs.get(index);

        // desplazamos todas las claves de current una posicion a la derecha
        for (int i = current.count; i >= 0; i--) {
            current.keys.set(i + 1, current.keys.get(i));
        }

        // movemos la clave del padre que separa ambos nodos a la primera posicion de current
        current.keys.set(0, parent.keys.get(index - 1));
        // actualizamos en el padre la clave prestada del final de left
        parent.keys.set(index - 1, left.keys.get(left.count - 1));
        // limpiamos la ultima posicion de left
        left.keys.set(left.count - 1, null);

        // ajustamos contadores: current gana una clave y left pierde una
        current.count++;
        left.count--;
    }

    // pide prestada una clave del hermano derecho cuando current tiene pocas claves
    private void borrowFromRight(BNode<E> parent, int index) {
        BNode<E> right = parent.childs.get(index + 1); // nodo hermano a la derecha
        BNode<E> current = parent.childs.get(index);   // nodo actual que necesita una clave

        // movemos la clave del padre a la ultima posicion de current
        current.keys.set(current.count, parent.keys.get(index));
        // reemplazamos en el padre la clave con la primera de right
        parent.keys.set(index, right.keys.get(0));

        // desplazamos las claves de right hacia la izquierda para cubrir el hueco
        for (int i = 1; i < right.count; i++) {
            right.keys.set(i - 1, right.keys.get(i));
        }
        // limpiamos la ultima posicion, ahora duplicada
        right.keys.set(right.count - 1, null);

        current.count++; // actualizamos el contador de claves en current
        right.count--;   // actualizamos el contador de claves en right
    }


    // reequilibra el nodo parent cuando su hijo en index se queda con pocas claves
    private void fix(BNode<E> parent, int index) {
        // si existe hermano izquierdo con mas claves del minimo, pedimos prestado de la izquierda
        if (index > 0 && parent.childs.get(index - 1).count > (order - 1) / 2) {
            borrowFromLeft(parent, index);
        // si existe hermano derecho con mas claves del minimo, pedimos prestado de la derecha
        } else if (index < parent.count && parent.childs.get(index + 1).count > (order - 1) / 2) {
            borrowFromRight(parent, index);
        } else {
            // si ningun hermano puede prestar, fusionamos con uno de ellos
            if (index > 0) {
                merge(parent, index - 1); // fusion con el hermano izquierdo
            } else {
                merge(parent, index);     // fusion con el hermano derecho
            }
        }
    }

    // recorre todas las hojas del árbol B+ en orden ascendente de claves
    public void mostrarInorden() {
        BNode<E> current = root;
        // bajar hasta la hoja más a la izquierda
        while (current != null && !current.isLeaf) {
            current = current.childs.get(0); // seguimos siempre el primer hijo
        }

        // desde la primera hoja, recorremos la lista enlazada de hojas
        while (current != null) {
            // imprimimos todas las claves válidas de esta hoja
            for (int i = 0; i < current.count; i++) {
                System.out.print(current.keys.get(i) + " ");
            }
            current = current.next; // avanzamos al siguiente nodo hoja
        }
        System.out.println(); // salto de línea al terminar el recorrido
    }

}
