package modelos; 

// clase que simula un arbol b+ simple usando arreglo
public class ArbolBProducto {
    private Producto[] productos; // arreglo que almacena los productos
    private int tamano;           // numero actual de productos en el arreglo
    private int capacidad;        // maxima capacidad del arreglo

    public ArbolBProducto(int capacidad) { // constructor que recibe la capacidad maxima
        this.capacidad = capacidad;        // guarda la capacidad maxima
        productos = new Producto[capacidad]; // crea el arreglo de productos
        tamano = 0;                          // inicializa el contador en cero
    }

    public void insertarProducto(Producto p) { // metodo para agregar un producto
        if (tamano < capacidad) {             // si aun hay espacio en el arreglo
            productos[tamano] = p;            // coloca el producto en la posicion libre
            tamano++;                         // incrementa el contador de productos
        } else {
            System.out.println("arbol lleno, no se puede insertar mas"); // avisa si ya no cabe
        }
    }

    public boolean eliminarProducto(String codigo) { // metodo para borrar producto por codigo
        for (int i = 0; i < tamano; i++) {           // recorre el arreglo hasta el tamano
            if (productos[i].codigo.equals(codigo)) { // comprueba si el codigo coincide
                for (int j = i; j < tamano - 1; j++) { // desplaza los demas productos hacia atras
                    productos[j] = productos[j + 1];  // mueve elemento siguiente al actual
                }
                productos[tamano - 1] = null; // limpia la ultima posicion ahora vacia
                tamano--;                    // decrementa el contador de productos
                return true;                 // retorna exito en la eliminacion
            }
        }
        return false; // retorna false si no encontro ningun producto con ese codigo
    }

    public Producto buscarPorCodigo(String codigo) { // busca y retorna un producto por codigo
        for (int i = 0; i < tamano; i++) {           // recorre el arreglo hasta el tamano
            if (productos[i].codigo.equals(codigo)) { // si encuentra coincidencia
                return productos[i];                 // retorna el producto encontrado
            }
        }
        return null; // retorna null si no lo encontro
    }

    public void mostrarProductos() { // imprime todos los productos del arreglo
        for (int i = 0; i < tamano; i++) {  // recorre el arreglo hasta el tamano
            productos[i].mostrarDatos();    // invoca el metodo que imprime los datos del producto
        }
    }
}
