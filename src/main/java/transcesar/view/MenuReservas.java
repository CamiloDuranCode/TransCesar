package transcesar.view;

import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.Reserva;
import transcesar.model.Vehiculo;
import transcesar.service.PersonaService;
import transcesar.service.ReservaService;
import transcesar.service.VehiculoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuReservas {

    static Scanner scanner = new Scanner(System.in);
    static ReservaService reservaService = new ReservaService();
    static PersonaService personaService = new PersonaService();
    static VehiculoService vehiculoService = new VehiculoService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== MÓDULO DE RESERVAS =====");
            System.out.println("1. Crear reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Listar reservas activas");
            System.out.println("4. Historial de reservas de un pasajero");
            System.out.println("5. Convertir reserva en ticket");
            System.out.println("6. Verificar reservas vencidas");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> crearReserva();
                case 2 -> cancelarReserva();
                case 3 -> listarReservasActivas();
                case 4 -> historialPasajero();
                case 5 -> convertirReserva();
                case 6 -> verificarVencidas();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void crearReserva() {
        try {
            List<Pasajero> pasajeros = personaService.getPasajeros();
            if (pasajeros.isEmpty()) {
                System.out.println("No hay pasajeros registrados.");
                return;
            }
            System.out.println("\nPasajeros disponibles:");
            for (int i = 0; i < pasajeros.size(); i++) {
                System.out.println((i + 1) + ". " + pasajeros.get(i).getNombre());
            }
            System.out.print("Seleccione un pasajero: ");
            Pasajero pasajero = pasajeros.get(scanner.nextInt() - 1);

            List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
            if (vehiculos.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
                return;
            }
            System.out.println("\nVehículos disponibles:");
            for (int i = 0; i < vehiculos.size(); i++) {
                System.out.println((i + 1) + ". " + vehiculos.get(i).getPlaca()
                        + " - " + vehiculos.get(i).getRuta());
            }
            System.out.print("Seleccione un vehículo: ");
            Vehiculo vehiculo = vehiculos.get(scanner.nextInt() - 1);

            System.out.print("Fecha de viaje (YYYY-MM-DD): ");
            LocalDate fechaViaje = LocalDate.parse(scanner.next());
            System.out.print("Origen: ");
            String origen = scanner.next();
            System.out.print("Destino: ");
            String destino = scanner.next();

            reservaService.crearReserva(pasajero, vehiculo, fechaViaje, origen, destino);

        } catch (Exception e) {
            System.out.println("Error al crear reserva: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        try {
            System.out.print("Ingrese el código de la reserva: ");
            String idReserva = scanner.next();
            boolean resultado = reservaService.cancelarReserva(idReserva);
            if (!resultado) {
                System.out.println("No se encontró una reserva activa con ese código.");
            }
        } catch (Exception e) {
            System.out.println("Error al cancelar reserva: " + e.getMessage());
        }
    }

    private static void listarReservasActivas() {
        try {
            List<Reserva> activas = reservaService.buscarReservasPorEstado(
                    Reserva.EstadoReserva.ACTIVA);
            if (activas.isEmpty()) {
                System.out.println("No hay reservas activas.");
                return;
            }
            activas.forEach(Reserva::imprimirDetalle);
        } catch (Exception e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }
    }

    private static void historialPasajero() {
        try {
            System.out.print("Ingrese la cédula del pasajero: ");
            String cedula = scanner.next();
            List<Reserva> historial = reservaService.buscarReservasPorPasajero(cedula);
            if (historial.isEmpty()) {
                System.out.println("No hay reservas para ese pasajero.");
                return;
            }
            historial.forEach(Reserva::imprimirDetalle);
        } catch (Exception e) {
            System.out.println("Error al buscar historial: " + e.getMessage());
        }
    }

    private static void convertirReserva() {
        try {
            System.out.print("Ingrese el código de la reserva: ");
            String idReserva = scanner.next();

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

            reservaService.confirmarReserva(idReserva, conductor);

        } catch (Exception e) {
            System.out.println("Error al convertir reserva: " + e.getMessage());
        }
    }

    private static void verificarVencidas() {
        try {
            int canceladas = reservaService.verificarReservasVencidas();
            System.out.println("Reservas vencidas canceladas: " + canceladas);
        } catch (Exception e) {
            System.out.println("Error al verificar reservas: " + e.getMessage());
        }
    }
}