package transcesar.view;

import transcesar.service.TicketService;
import java.util.Scanner;

public class MenuTickets {

    static Scanner scanner = new Scanner(System.in);
    static TicketService ticketService = new TicketService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== VENTA DE TICKETS =====");
            System.out.println("1. Vender ticket");
            System.out.println("2. Listar tickets");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> venderTicket();
                case 2 -> ticketService.listarTickets();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void venderTicket() {
        System.out.print("Cédula del pasajero: ");
        String cedulaPasajero = scanner.next();
        System.out.print("Placa del vehículo: ");
        String placaVehiculo = scanner.next();
        System.out.print("Origen: ");
        String origen = scanner.next();
        System.out.print("Destino: ");
        String destino = scanner.next();
        ticketService.venderTicket(cedulaPasajero, placaVehiculo, origen, destino);
    }
}