package modelos;

// esta clase representa un producto en el almacen
public class Producto {
    public String codigo; // codigo unico del producto
    public String nombre; // nombre del producto

    public Producto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // muestra los datos del producto
    public void mostrarDatos() {
        System.out.println("Producto: " + nombre + " | Código: " + codigo);
    }
}
