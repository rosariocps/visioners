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
        if (root == null) {
            root = new BNode<>(this.order, true);
            root.keys.set(0, cl);
            root.count = 1;
            return;
        }

        up = false;
        E mediana = push(this.root, cl);

        if (up) {
            BNode<E> pnew = new BNode<>(this.order, false);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) throws ItemDuplicated {
        int pos[] = new int[1];
        E mediana;

        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        }

        if (current.isLeaf) {
            boolean found = current.searchNode(cl, pos);
            if (found) {
                up = false;
                throw new ItemDuplicated("El elemento ya existe en el árbol: " + cl);
            }

            if (!current.nodeFull(this.order - 1)) {
                insertInLeaf(current, cl, pos[0]);
                up = false;
                return null;
            } else {
                return splitLeaf(current, cl, pos[0]);
            }
        } else {
            current.searchNode(cl, pos);
            mediana = push(current.childs.get(pos[0]), cl);

            if (up) {
                if (current.nodeFull(this.order - 1)) {
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }
            return mediana;
        }
    }

    private void insertInLeaf(BNode<E> leaf, E cl, int pos) {
        for (int i = leaf.count; i > pos; i--) {
            leaf.keys.set(i, leaf.keys.get(i - 1));
        }
        leaf.keys.set(pos, cl);
        leaf.count++;
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        int i;
        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        int i, posMdna;
        posMdna = (k <= this.order / 2) ? this.order / 2 : this.order / 2 + 1;
        nDes = new BNode<>(this.order,false);

        for (i = posMdna; i < this.order - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        nDes.count = (this.order - 1) - posMdna;
        current.count = posMdna;

        if (k <= this.order / 2) {
            putNode(current, cl, nDes, k);
        } else {
            putNode(nDes, cl, nDes, k - posMdna);
        }

        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;

        return median;
    }

    private E splitLeaf(BNode<E> current, E cl, int k) {
        int i, posMdna;
        posMdna = (k <= this.order / 2) ? this.order / 2 : this.order / 2 + 1;
        nDes = new BNode<>(this.order,true);

        for (i = posMdna; i < this.order - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
        }

        nDes.count = (this.order - 1) - posMdna;
        current.count = posMdna;

        if (k <= this.order / 2) {
            insertInLeaf(current, cl, k);
        } else {
            insertInLeaf(nDes, cl, k - posMdna);
        }

        // Enlace doble entre hojas
        nDes.next = current.next;
        if (nDes.next != null) {
            nDes.next.prev = nDes;
        }
        current.next = nDes;
        nDes.prev = current;

        E mediana = nDes.keys.get(0);
        up = true;

        return mediana;
    }

    public boolean search(E data) {
        return searchRecursive(root, data);
    }

    private boolean searchRecursive(BNode<E> current, E data) {
        if (current == null) {
            return false;
        }

        int[] pos = new int[1];
        boolean found = current.searchNode(data, pos);

        if (found) {
            return true;
        }

        if (pos[0] < current.childs.size() && current.childs.get(pos[0]) != null) {
            return searchRecursive(current.childs.get(pos[0]), data);
        }
        return false;
    }

    public void remove(E cl) throws ItemNotFound, ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío. No se puede eliminar.");
        }
        delete(root, cl);
        if (root != null && root.count == 0) {
            root = root.childs.get(0);
        }
    }


    public boolean delete(BNode<E> node, E key) throws ItemNotFound {
        if (node == null) {
            throw new ItemNotFound("Elemento no encontrado: " + key);
        }

        int pos[] = new int[1];
        boolean found = node.searchNode(key, pos);

        if (found) {
            if (!node.isLeaf) {
                E pred = getPredecessor(node, pos[0]);
                node.keys.set(pos[0], pred);
                return delete(node.childs.get(pos[0]), pred);
            } else {
                removeKey(node, pos[0]);
                return true;
            }
        } else {
            if (node.isLeaf) {
                return false;
            } else {
                boolean isDeleted = delete(node.childs.get(pos[0]), key);
                if (node.childs.get(pos[0]).count < (order - 1) / 2) {
                    fix(node, pos[0]);
                }
                return isDeleted;
            }
        }
    }

    private void removeKey(BNode<E> node, int index) {
        for(int i = index; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
        }
        node.keys.set(node.count - 1, null);
        node.count--;
    }

    private E getPredecessor(BNode<E> node, int index) {
        BNode<E> current = node.childs.get(index);
        while (!current.isLeaf) {
            current = current.childs.get(current.count);
        }
        return current.keys.get(current.count - 1);
    }

    private void merge(BNode<E> parent, int index) {
        BNode<E> left = parent.childs.get(index);
        BNode<E> right = parent.childs.get(index + 1);

        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + i, right.keys.get(i));
        }

        if (left.isLeaf && right.isLeaf) {
            left.next = right.next;
            if (right.next != null) right.next.prev = left;
        }

        left.count += right.count;

        for (int i = index; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }

        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
    }

    private void borrowFromLeft(BNode<E> parent, int index ) {
        BNode<E> left = parent.childs.get(index - 1);
        BNode<E> current = parent.childs.get(index);

        for (int i = current.count; i >= 0; i--) {
            current.keys.set(i + 1, current.keys.get(i));
        }

        current.keys.set(0, parent.keys.get(index - 1));
        parent.keys.set(index - 1, left.keys.get(left.count - 1));
        left.keys.set(left.count - 1, null);

        current.count++;
        left.count--;
    }

    private void borrowFromRight(BNode<E> parent, int index) {
        BNode<E> right = parent.childs.get(index + 1);
        BNode<E> current = parent.childs.get(index);

        current.keys.set(current.count, parent.keys.get(index));
        parent.keys.set(index, right.keys.get(0));

        for (int i = 1; i < right.count; i++) {
            right.keys.set(i - 1, right.keys.get(i));
        }
        right.keys.set(right.count - 1, null);

        current.count++;
        right.count--;
    }

    private void fix(BNode<E> parent, int index) {
        if (index > 0 && parent.childs.get(index - 1).count > (order - 1) / 2) {
            borrowFromLeft(parent, index);
        } else if (index < parent.count && parent.childs.get(index + 1).count > (order - 1) / 2) {
            borrowFromRight(parent, index);
        } else {
            if (index > 0) {
                merge(parent, index - 1);
            } else {
                merge(parent, index);
            }
        }
    }

    // Recorre todas las hojas en orden
    public void printAllOrdered() {
        BNode<E> current = root;
        while (current != null && !current.isLeaf) {
            current = current.childs.get(0);
        }

        while (current != null) {
            for (int i = 0; i < current.count; i++) {
                System.out.print(current.keys.get(i) + " ");
            }
            current = current.next;
        }
        System.out.println();
    }
}
