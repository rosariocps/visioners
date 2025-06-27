package stacklink;

import exceptions.ExceptionIsEmpty;
import linkedlist.Nodo;

public class StackLink<E> implements Stack<E> {
    private Nodo<E> tope;

    public StackLink() {
        this.tope = null;
    }

    @Override
    public void push(E x) {
        Nodo<E> nuevoNodo = new Nodo<>(x);
        nuevoNodo.setNext(tope);
        tope = nuevoNodo;
    }

    @Override
    public E pop() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("Pila vacía");
        }
        E valor = tope.getData();
        tope = tope.getNext();
        return valor;
    }

    @Override
    public E top() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("Pila vacía");
        }
        return tope.getData();
    }

    @Override
    public boolean isEmpty() {
        return tope == null;
    }

    @Override
    public void destroyStack() {
        tope = null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Pila vacía";
        }
        StringBuilder sb = new StringBuilder("Pila: [");
        Nodo<E> actual = tope;
        while (actual != null) {
            sb.append(actual.getData());
            if (actual.getNext() != null) {
                sb.append(", ");
            }
            actual = actual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
