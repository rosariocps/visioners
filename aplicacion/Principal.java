package aplicacion;

import almacen.Producto;
import almacen.UbicacionAlmacen;
import exceptions.ItemDuplicated;
import graphArray.GrafoDirigidoArrayList;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GrafoDirigidoArrayList<UbicacionAlmacen> grafo = new GrafoDirigidoArrayList<>();

        while (true) {
            System.out.println("\n--- MENÚ ALMACÉN ---");
            System.out.println("1. Agregar ubicación");
            System.out.println("2. Agregar ruta entre ubicaciones");
            System.out.println("3. Insertar producto en ubicación");
            System.out.println("4. Mostrar productos de una ubicación");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la ubicación: ");
                    String nombre = sc.nextLine();
                    System.out.print("Orden del árbol B+: ");
                    int orden = Integer.parseInt(sc.nextLine());

                    UbicacionAlmacen nueva = new UbicacionAlmacen(nombre, orden);
                    grafo.insertVertex(nueva);
                    System.out.println("Ubicación agregada.");
                    break;

                case 2:
                    System.out.print("Ubicación origen: ");
                    String origen = sc.nextLine();
                    System.out.print("Ubicación destino: ");
                    String destino = sc.nextLine();
                    System.out.print("Peso de la ruta: ");
                    int peso = Integer.parseInt(sc.nextLine());

                    UbicacionAlmacen u1 = buscarUbicacion(grafo, origen);
                    UbicacionAlmacen u2 = buscarUbicacion(grafo, destino);

                    if (u1 != null && u2 != null) {
                        grafo.insertEdge(u1, u2, peso);
                        System.out.println("Ruta agregada.");
                    } else {
                        System.out.println("Ubicación no encontrada.");
                    }
                    break;

                case 3:
                    System.out.print("Nombre de la ubicación: ");
                    String ub = sc.nextLine();
                    UbicacionAlmacen u = buscarUbicacion(grafo, ub);

                    if (u != null) {
                        System.out.print("Código: ");
                        String codigo = sc.nextLine();
                        System.out.print("Nombre: ");
                        String nomProd = sc.nextLine();
                        System.out.print("Lote: ");
                        String lote = sc.nextLine();

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
                    System.out.print("Nombre de la ubicación: ");
                    String nombreUb = sc.nextLine();
                    UbicacionAlmacen ubic = buscarUbicacion(grafo, nombreUb);

                    if (ubic != null) {
                        System.out.println("Productos en " + nombreUb + ":");
                        ubic.getArbolProductos().mostrarInorden();
                    } else {
                        System.out.println("Ubicación no encontrada.");
                    }
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static UbicacionAlmacen buscarUbicacion(GrafoDirigidoArrayList<UbicacionAlmacen> grafo, String nombre) {
        for (UbicacionAlmacen u : grafo.getListVertices()) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }
}
