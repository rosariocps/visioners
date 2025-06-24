package modelos; 

// clase que representa un producto en el almacen
public class Producto {
    public String codigo; // codigo unico que identifica el producto
    public String nombre; // nombre descriptivo del producto

    public Producto(String codigo, String nombre) { // constructor con parametros
        this.codigo = codigo;   // asigna el codigo al atributo
        this.nombre = nombre;   // asigna el nombre al atributo
    }

    public void mostrarDatos() { // imprime en consola los datos del producto
        System.out.println("producto: " + nombre + " | codigo: " + codigo); // muestra nombre y codigo
    }
}
