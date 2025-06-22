package utils;

/**
 * Cola simple de enteros basada en arreglo circular.
 */
public class Cola {
    private int[] datos;   // almacenamiento de la cola
    private int frente;    // indice del primer elemento
    private int fin;       // indice donde encolar el siguiente
    private int tamano;    // numero de elementos actualmente en la cola

    /**
     * Constructor: crea una cola con capacidad fija.
     * @param capacidad maxima de la cola
     */
    public Cola(int capacidad) {
        datos  = new int[capacidad];
        frente = 0;
        fin     = 0;
        tamano  = 0;
    }

    /**
     * Agrega un valor al final de la cola.
     * @param valor entero a encolar
     */
    public void encolar(int valor) {
        if (tamano < datos.length) {
            datos[fin] = valor;
            fin = (fin + 1) % datos.length;
            tamano++;
        } else {
            System.out.println("Cola llena. No se puede encolar " + valor);
        }
    }

    /**
     * Quita y devuelve el primer valor de la cola.
     * @return valor desencolado, o -1 si la cola esta vacia
     */
    public int desencolar() {
        if (tamano > 0) {
            int valor = datos[frente];
            frente = (frente + 1) % datos.length;
            tamano--;
            return valor;
        } else {
            System.out.println("Cola vacia. No hay nada que desencolar.");
            return -1;
        }
    }

    /**
     * Indica si la cola esta vacia.
     * @return true si no tiene elementos
     */
    public boolean estaVacia() {
        return tamano == 0;
    }

    /**
     * Indica si la cola esta llena.
     * @return true si alcanzo su capacidad maxima
     */
    public boolean estaLlena() {
        return tamano == datos.length;
    }

    /**
     * Devuelve el numero de elementos en la cola.
     * @return cantidad actual de elementos
     */
    public int tamano() {
        return tamano;
    }
}
