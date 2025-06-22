package modelos;

// este es un arbol b simple representado con un arreglo
public class ArbolBProducto {
    private Producto[] productos;
    private int tamaño;      // cuántos productos hay
    private int capacidad;   // capacidad máxima del arreglo

    public ArbolBProducto(int capacidad) {
        this.capacidad = capacidad;
        productos = new Producto[capacidad];
        tamaño = 0;
    }

    // agrega un producto si hay espacio
    public void insertarProducto(Producto p) {
        if (tamaño < capacidad) {
            productos[tamaño] = p;
            tamaño++;
        } else {
            System.out.println("Arbol lleno. No se puede insertar más productos.");
        }
    }

    // busca un producto por su codigo
    public Producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < tamaño; i++) {
            if (productos[i].codigo.equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    // muestra todos los productos guardados
    public void mostrarProductos() {
        for (int i = 0; i < tamaño; i++) {
            productos[i].mostrarDatos();
        }
    }
}
