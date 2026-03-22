package transcesar.view;

import java.util.Scanner;

public class Menu {
    static Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== TRANSCESAR S.A.S. =====");
            System.out.println("1. Gestión de Vehículos");
            System.out.println("2. Gestión de Personas");
            System.out.println("3. Venta de Tickets");
            System.out.println("4. Consultas y Estadísticas");
            System.out.println("5. Reportes");
            System.out.println("6. Reservas");
            System.out.println("7. Gestión de Rutas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            try {
                switch (opcion) {
                    case 1 -> MenuVehiculos.mostrar();
                    case 2 -> MenuPersonas.mostrar();
                    case 3 -> MenuTickets.mostrar();
                    case 4 -> MenuEstadisticas.mostrar();
                    case 5 -> MenuReportes.mostrar();
                    case 6 -> MenuReservas.mostrar();
                    case 7 -> MenuRutas.mostrar();
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
