package aplicacion;

import almacen.Producto;
import almacen.UbicacionAlmacen;
import exceptions.ItemDuplicated;
import graphArray.GrafoDirigidoArrayList;
import java.util.Scanner;

/**1
 * Esta es una aplicación para organizar un almacén.
 * Aquí puedes crear lugares donde se guardan productos en ubicaciones 
 * del almacen (como estanterías, pasillos o zonas de carga)
 * conectar esos lugares con caminos (rutas con peso),
 * y guardar productos dentro de cada lugar usando un árbol especial.
 *
 * Un mapa de almacenes donde puedes moverte entre ellos
 * y revisar qué cosas tiene guardadas cada uno.
 */

public class Principal {
    public static void main(String[] args) {
        // Se crea un escáner para leer datos del usuario
        Scanner sc = new Scanner(System.in);

        // Se crea un grafo donde se almacenarán las zonas del almacén como nodos
        GrafoDirigidoArrayList<UbicacionAlmacen> grafo = new GrafoDirigidoArrayList<>();

        // Menú principal que se repite hasta que el usuario elija salir
        while (true) {
            // Se muestran las opciones del menú
            System.out.println("\n--- MENÚ ALMACÉN ---");
            System.out.println("1. Agregar ubicación");
            System.out.println("2. Agregar ruta entre ubicaciones");
            System.out.println("3. Insertar producto en ubicación");
            System.out.println("4. Mostrar productos de una ubicación");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(sc.nextLine()); // Lee la opción del usuario

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break; // Sale del menú
            }

            switch (opcion) {
                case 1:
                    // Caso para agregar una nueva zona al almacén
                    System.out.print("Nombre de la ubicación: ");
                    String nombre = sc.nextLine();
                    System.out.print("Orden del árbol B+: ");
                    int orden = Integer.parseInt(sc.nextLine());

                    // Se crea la nueva ubicación con su árbol B+ interno
                    UbicacionAlmacen nueva = new UbicacionAlmacen(nombre, orden);
                    grafo.insertVertex(nueva); // Se agrega al grafo
                    System.out.println("Ubicación agregada.");
                    break;

                case 2:
                    // Caso para agregar una ruta (camino) entre dos ubicaciones
                    System.out.print("Ubicación origen: ");
                    String origen = sc.nextLine();
                    System.out.print("Ubicación destino: ");
                    String destino = sc.nextLine();
                    System.out.print("Peso de la ruta: "); // Puede ser distancia o tiempo
                    int peso = Integer.parseInt(sc.nextLine());

                    // Busca las ubicaciones en el grafo
                    UbicacionAlmacen u1 = buscarUbicacion(grafo, origen);
                    UbicacionAlmacen u2 = buscarUbicacion(grafo, destino);

                    // Si ambas ubicaciones existen, se crea la conexión
                    if (u1 != null && u2 != null) {
                        grafo.insertEdge(u1, u2, peso);
                        System.out.println("Ruta agregada.");
                    } else {
                        System.out.println("Ubicación no encontrada.");
                    }
                    break;

                case 3:
                    // Caso para insertar un producto en una ubicación
                    System.out.print("Nombre de la ubicación: ");
                    String ub = sc.nextLine();
                    UbicacionAlmacen u = buscarUbicacion(grafo, ub); // Se busca la ubicación

                    if (u != null) {
                        // Se piden los datos del producto
                        System.out.print("Código: ");
                        String codigo = sc.nextLine();
                        System.out.print("Nombre: ");
                        String nomProd = sc.nextLine();
                        System.out.print("Lote: ");
                        String lote = sc.nextLine();

                        // Se crea el producto y se intenta insertar en el árbol B+ de la ubicación
                        Producto p = new Producto(codigo, nomProd, lote);
                        try {
                            u.getArbolProductos().insert(p);
                            System.out.println("Producto insertado.");
                        } catch (ItemDuplicated e) {
                            System.out.println("Error: producto duplicado.");
                        }
                    } else {
                        System.out.println("Ubicación no encontrada.");
                    }
                    break;

                case 4:
                    // Caso para mostrar todos los productos guardados en una ubicación
                    System.out.print("Nombre de la ubicación: ");
                    String nombreUb = sc.nextLine();
                    UbicacionAlmacen ubic = buscarUbicacion(grafo, nombreUb);

                    if (ubic != null) {
                        System.out.println("Productos en " + nombreUb + ":");
                        ubic.getArbolProductos().mostrarInorden(); // Muestra los productos ordenados
                    } else {
                        System.out.println("Ubicación no encontrada.");
                    }
                    break;

                default:
                    // Si la opción no existe
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Método auxiliar para buscar una ubicación por su nombre en el grafo
    private static UbicacionAlmacen buscarUbicacion(GrafoDirigidoArrayList<UbicacionAlmacen> grafo, String nombre) {
        for (UbicacionAlmacen u : grafo.getListVertices()) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null; // Si no se encuentra, devuelve null
    }
}
