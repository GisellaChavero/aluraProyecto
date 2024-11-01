package proyectoMonedas.com;

import proyectoMonedas.com.Respuesta;

import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Respuesta respuesta = new Respuesta();
        String opcion;
        int i = 2;

        do {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Realizar una conversión de moneda");
            System.out.println("2. Ver explicación del programa");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción (1-3): ");
            opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    respuesta.realizarConversion(scanner);
                    break;
                case "2":
                    respuesta.mostrarExplicacion();
                    break;
                case "3":
                    System.out.println("Gracias por usar el convertidor de monedas. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija entre 1 y 3.");
            }
        } while (!opcion.equals("3"));

        scanner.close();
    }
}
