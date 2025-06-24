package utils; 

// clase que implementa una cola de enteros con arreglo circular
public class Cola {
    private int[] datos; // arreglo donde se guardan los valores
    private int frente;  // indice del primer elemento en la cola
    private int fin;     // indice donde se agregara el siguiente elemento
    private int tamano;  // numero de elementos actualmente en la cola

    public Cola(int capacidad) { // constructor que recibe la capacidad maxima
        datos = new int[capacidad]; // crea el arreglo con la capacidad dada
        frente = 0;                 // inicializa el indice frente en cero
        fin = 0;                    // inicializa el indice fin en cero
        tamano = 0;                 // inicializa el tamano en cero
    }

    public void encolar(int valor) { // metodo para agregar un elemento
        if (tamano < datos.length) { // verifica que la cola no este llena
            datos[fin] = valor;      // coloca el valor en la posicion fin
            fin = (fin + 1) % datos.length; // avanza fin circularmente
            tamano++;                // aumenta el contador de elementos
        } else {
            System.out.println("cola llena, no se puede encolar " + valor); // mensaje si esta llena
        }
    }

    public int desencolar() { // metodo para remover y retornar el primer elemento
        if (tamano > 0) {       // verifica que la cola no este vacia
            int valor = datos[frente]; // obtiene el valor en la posicion frente
            frente = (frente + 1) % datos.length; // avanza frente circularmente
            tamano--;          // decrementa el contador de elementos
            return valor;      // retorna el valor desencolado
        } else {
            System.out.println("cola vacia, no hay nada que desencolar"); // mensaje si esta vacia
            return -1;         // retorna -1 como indicador de error
        }
    }

    public boolean estaVacia() { // metodo que indica si la cola esta vacia
        return tamano == 0;       // true si tamano es cero
    }

    public boolean estaLlena() { // metodo que indica si la cola esta llena
        return tamano == datos.length; // true si tamano igual a capacidad
    }

    public int tamano() { // metodo que retorna el numero de elementos actuales
        return tamano;    // devuelve el contador tamano
    }
}
