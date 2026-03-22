package transcesar.view;

import transcesar.service.TicketService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuReportes {

    static Scanner scanner = new Scanner(System.in);
    static TicketService ticketService = new TicketService();

    public static void mostrar() throws IOException {
        int opcion;
        do {
            System.out.println("\n===== MÓDULO DE REPORTES =====");
            System.out.println("1. Tickets por fecha");
            System.out.println("2. Tickets por tipo de vehículo");
            System.out.println("3. Tickets por tipo de pasajero");
            System.out.println("4. Resumen del día actual");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> reportePorFecha();
                case 2 -> reportePorTipoVehiculo();
                case 3 -> reportePorTipoPasajero();
                case 4 -> ticketService.resumenDiaActual();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void reportePorFecha() {
        try {
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            String fecha = scanner.next();
            ticketService.listarTicketsPorFecha(LocalDate.parse(fecha));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reportePorTipoVehiculo() {
        try {
            System.out.print("Tipo de vehículo (Bus/Buseta/MicroBus): ");
            String tipo = scanner.next();
            ticketService.listarTicketsPorTipoVehiculo(tipo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reportePorTipoPasajero() {
        try {
            System.out.print("Tipo de pasajero (Regular/Estudiante/AdultoMayor): ");
            String tipo = scanner.next();
            ticketService.listarTicketsPorTipoPasajero(tipo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
