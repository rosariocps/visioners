package almacen;

public class Producto implements Comparable<Producto> {
    private String codigo;
    private String nombre;
    private String lote;

    public Producto(String codigo, String nombre, String lote) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.lote = lote;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLote() {
        return lote;
    }

    @Override
    public int compareTo(Producto otro) {
        // Comparación por código para ordenamiento en el árbol B+
        return this.codigo.compareTo(otro.codigo); 
        //negativo si es menor
        //cero si es igual
        //positivo si es mayor
    }

    @Override
    public String toString() {
        return nombre + " (Código: " + codigo + ", Lote: " + lote + ")";
    }
}