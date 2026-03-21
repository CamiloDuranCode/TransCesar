package transcesar.view;

import transcesar.model.Ticket;
import transcesar.service.TicketService;
import transcesar.service.VehiculoService;
import java.util.List;
import java.util.Scanner;

public class MenuEstadisticas {

    static Scanner scanner = new Scanner(System.in);
    static TicketService ticketService = new TicketService();
    static VehiculoService vehiculoService = new VehiculoService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== CONSULTAS Y ESTADÍSTICAS =====");
            System.out.println("1. Ver estadísticas generales");
            System.out.println("2. Vehículo con más tickets vendidos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> ticketService.mostrarEstadisticas();
                case 2 -> {
                    List<Ticket> tickets = ticketService.getTickets();
                    vehiculoService.vehiculoConMasTickets(tickets);
                }
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}