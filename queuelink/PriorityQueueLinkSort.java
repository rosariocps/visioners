package queuelink;

import exceptions.ExceptionIsEmpty;
import linkedlist.Nodo;

public class PriorityQueueLinkSort<E, N extends Comparable<N>> implements PriorityQueue<E, N> {

    class EntryNode {
        E data;
        N priority;

        EntryNode(E data, N priority) {
            this.data = data;
            this.priority = priority;
        }

        public E getData() {
            return data;
        }

        public N getPriority() {
            return priority;
        }
    }

    private Nodo<EntryNode> first;
    private Nodo<EntryNode> last;

    public PriorityQueueLinkSort() {
        this.first = null;
        this.last = null;
    }

    @Override
    public void enqueue(E x, N pr) {
        EntryNode entradaNueva = new EntryNode(x, pr);
        Nodo<EntryNode> nodoNuevo = new Nodo<>(entradaNueva);

        if (first == null) {
            first = last = nodoNuevo;
            return;
        }

        if (pr.compareTo(first.getData().getPriority()) < 0) {
            nodoNuevo.setNext(first);
            first = nodoNuevo;
            return;
        }

        Nodo<EntryNode> nodoCurrent = first;
        while (nodoCurrent.getNext() != null &&
               pr.compareTo(nodoCurrent.getNext().getData().getPriority()) >= 0) {
            nodoCurrent = nodoCurrent.getNext();
        }

        nodoNuevo.setNext(nodoCurrent.getNext());
        nodoCurrent.setNext(nodoNuevo);

        if (nodoNuevo.getNext() == null) {
            last = nodoNuevo;
        }
    }

    @Override
    public E dequeue() throws ExceptionIsEmpty {
        if (isEmpty())
            throw new ExceptionIsEmpty("Cannot remove from an empty queue");

        E valor = first.getData().getData();
        first = first.getNext();
        if (first == null)
            last = null;
        return valor;
    }

    @Override
    public E back() throws ExceptionIsEmpty {
        if (isEmpty())
            throw new ExceptionIsEmpty("La cola está vacía");
        return last.getData().getData();
    }

    @Override
    public E front() throws ExceptionIsEmpty {
        if (isEmpty())
            throw new ExceptionIsEmpty("La cola está vacía");
        return first.getData().getData();
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String buscarElemento(E elemento) {
        Nodo<EntryNode> actual = first;
        int posicion = 0;

        while (actual != null) {
            if (actual.getData().getData().equals(elemento)) {
                return "\"" + elemento + "\" está en la posición " + posicion +
                        " y tiene prioridad " + actual.getData().getPriority() + ".";
            }
            actual = actual.getNext();
            posicion++;
        }

        return "\"" + elemento + "\" no se encontró en la cola de prioridad.";
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Cola vacía";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %s\n", "Dato", "Prioridad"));
        sb.append("----------------------\n");

        Nodo<EntryNode> actual = first;
        while (actual != null) {
            sb.append(String.format("%-10s | %s\n",
                    actual.getData().getData().toString(),
                    actual.getData().getPriority().toString()));
            actual = actual.getNext();
        }
        return sb.toString();
    }
}
