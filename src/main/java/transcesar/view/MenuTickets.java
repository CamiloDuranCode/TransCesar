package transcesar.view;

import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.Ticket;
import transcesar.service.PersonaService;
import transcesar.service.TicketService;
import transcesar.service.VehiculoService;
import java.util.List;
import java.util.Scanner;

public class MenuTickets {

    static Scanner scanner = new Scanner(System.in);
    static TicketService ticketService = new TicketService();
    static PersonaService personaService = new PersonaService();
    static VehiculoService vehiculoService = new VehiculoService();

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
                case 2 -> {
                    List<Ticket> tickets = ticketService.getTickets();
                    if (tickets.isEmpty()) {
                        System.out.println("No hay tickets registrados.");
                    } else {
                        tickets.forEach(Ticket::imprimirDetalle);
                    }
                }
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void venderTicket() {
        try {
            List<Conductor> conductores = personaService.getConductores();
            if (conductores.isEmpty()) {
                System.out.println("No hay conductores registrados.");
                return;
            }
            System.out.println("\nConductores disponibles:");
            for (int i = 0; i < conductores.size(); i++) {
                System.out.println((i + 1) + ". " + conductores.get(i).getNombre());
            }
            System.out.print("Seleccione un conductor: ");
            Conductor conductor = conductores.get(scanner.nextInt() - 1);

            List<Pasajero> pasajeros = personaService.getPasajeros();
            if (pasajeros.isEmpty()) {
                System.out.println("No hay pasajeros registrados.");
                return;
            }
            System.out.println("\nPasajeros disponibles:");
            for (int i = 0; i < pasajeros.size(); i++) {
                System.out.println((i + 1) + ". " + pasajeros.get(i).getNombre()
                        + " - " + pasajeros.get(i).getTipoPasajero());
            }
            System.out.print("Seleccione un pasajero: ");
            Pasajero pasajero = pasajeros.get(scanner.nextInt() - 1);

            System.out.print("Origen: ");
            String origen = scanner.next();
            System.out.print("Destino: ");
            String destino = scanner.next();

            ticketService.venderTicket(pasajero, conductor, origen, destino);

        } catch (Exception e) {
            System.out.println("Error al vender ticket: " + e.getMessage());
        }
    }
}