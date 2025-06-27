package stacklink;

import exceptions.ExceptionIsEmpty;

public class Test {
    public static void main(String[] args) {
        try {
            // creamos una variable llamada pilaEnteros que usa la interfaz stack con datos tipo entero
            // pero en realidad usamos la clase stacklink como su forma de funcionar (implementacion)
            Stack<Integer> pilaEnteros = new StackLink<>();
            // agregamos numeros a la pila
            pilaEnteros.push(10);
            pilaEnteros.push(20);
            pilaEnteros.push(30);
            //mostramos la pila - como es una estructura LIFO
            //el ultimo en entrar 30 es el primero que se ve en la cima
            System.out.println(pilaEnteros.toString()); // [30, 20, 10]

            //mostramos el tope de la pila
            System.out.println("Elemento en el tope: " + pilaEnteros.top()); // 30
            
            //eliminar el nodo de la cima (tope) y lo mostramos
            System.out.println("Eliminando la cima de la pila: " + pilaEnteros.pop()); // 30
            //mostramos la pila despues de haber eliminado
            System.out.println(pilaEnteros.toString()); // [20, 10]

            //vaciamos la pila
            pilaEnteros.destroyStack();
            //mostramos la pila, q ahora esta vacia, por lo que el mensaje sera "pila vacia"
            System.out.println(pilaEnteros.toString()); 

            //hacemos un prueba de una pila de strings
            Stack<String> pilaStrings = new StackLink<>();
            //agregamos las cadenas a la pila
            pilaStrings.push("Hola");
            pilaStrings.push("Mundo");
            //mostramos la pila en texto
            System.out.println(pilaStrings.toString());
        //si ocurre una excepcion porque la pila esta vacia (al hacer pop o top sin elementos) entra aqui
        } catch (ExceptionIsEmpty e) {
            //mostramos el mensaje de la excepcion que explica que paso
            //osea "Pila vacia"
            System.out.println("Excepcion: " + e.getMessage());
        }
    }
}
