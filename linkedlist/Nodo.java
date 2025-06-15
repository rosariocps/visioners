package linkedlist;

public class Nodo<E> {
    E data; // Dato almacenado en el nodo
    Nodo<E> next; // Referencia al siguiente nodo en la lista

    // Constructor
    public Nodo(E data) {
        this.data = data; // Asignamos el dato al nodo
        this.next = null; // Inicialmente no apunta a ningún nodo (es el último)
    }

    public E getData(){
        return data;
    }

    public Nodo<E> getNext(){
        return next;
    }
}
