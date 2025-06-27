package queuelink;

import exceptions.ExceptionIsEmpty;

public interface PriorityQueue<E, N> {
    void enqueue(E x, N pr); // Inserta un elemento según su prioridad
    E dequeue() throws ExceptionIsEmpty; // Elimina y retorna el elemento con mayor prioridad
    E front() throws ExceptionIsEmpty; // Retorna el dato del elemento con mayor prioridad
    E back() throws ExceptionIsEmpty; // Retorna el dato del elemento con menor prioridad
    boolean isEmpty(); // Retorna true si la cola está vacía, false en caso contrario
    //metodo para buscar un elemento especifico en la cola de prioridad
    String buscarElemento(E elemento); // Retorna la prioridad y la posicion del elemento
}
