package transcesar.view;

import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.service.PersonaService;
import java.time.LocalDate;
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
        try {
            System.out.print("Cédula: ");
            String cedula = scanner.next();
            System.out.print("Nombre: ");
            String nombre = scanner.next();
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.next());
            System.out.print("Número de licencia: ");
            String numLicencia = scanner.next();
            System.out.print("Categoría (B1/B2/C1/C2): ");
            String categoria = scanner.next();
            Conductor conductor = new Conductor(cedula, nombre, fechaNacimiento, numLicencia, categoria);
            personaService.registrarConductor(conductor);
            System.out.println("Conductor registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar conductor: " + e.getMessage());
        }
    }

    private static void registrarPasajero() {
        try {
            System.out.print("Cédula: ");
            String cedula = scanner.next();
            System.out.print("Nombre: ");
            String nombre = scanner.next();
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.next());
            Pasajero pasajero = new Pasajero(cedula, nombre, fechaNacimiento);
            System.out.println("Tipo detectado: " + pasajero.getTipoPasajero());
            personaService.registrarPasajero(pasajero);
            System.out.println("Pasajero registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar pasajero: " + e.getMessage());
        }
    }
}