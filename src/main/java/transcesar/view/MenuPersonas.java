package transcesar.view;

import transcesar.service.PersonaService;
import java.util.Scanner;

public class MenuPersonas {

    static Scanner scanner = new Scanner(System.in);
    static PersonaService personaService = new PersonaService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE PERSONAS =====");
            System.out.println("1. Registrar conductor");
            System.out.println("2. Registrar pasajero");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarConductor();
                case 2 -> registrarPasajero();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarConductor() {
        System.out.print("Cédula: ");
        String cedula = scanner.next();
        System.out.print("Nombre: ");
        String nombre = scanner.next();
        System.out.print("Número de licencia: ");
        String numLicencia = scanner.next();
        System.out.print("Categoría (B1/B2/C1/C2): ");
        String categoria = scanner.next();
        personaService.registrarConductor(cedula, nombre, numLicencia, categoria);
    }

    private static void registrarPasajero() {
        System.out.print("Cédula: ");
        String cedula = scanner.next();
        System.out.print("Nombre: ");
        String nombre = scanner.next();
        System.out.print("Tipo (Regular/Estudiante/AdultoMayor): ");
        String tipo = scanner.next();
        personaService.registrarPasajero(cedula, nombre, tipo);
    }
}
