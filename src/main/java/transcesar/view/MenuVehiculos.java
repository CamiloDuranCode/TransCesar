package transcesar.view;

import transcesar.model.Ruta;
import transcesar.service.VehiculoService;
import java.util.List;
import java.util.Scanner;

public class MenuVehiculos {

    static Scanner scanner = new Scanner(System.in);
    static VehiculoService vehiculoService = new VehiculoService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE VEHÍCULOS =====");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Listar vehículos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarVehiculo();
                case 2 -> vehiculoService.listarVehiculos();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarVehiculo() {
        try {
            System.out.print("Tipo (Bus/Buseta/MicroBus): ");
            String tipo = scanner.next();
            System.out.print("Placa: ");
            String placa = scanner.next();

            // Listar rutas disponibles
            List<Ruta> rutas = vehiculoService.listarRutas();
            if (rutas.isEmpty()) {
                System.out.println("No hay rutas registradas. Registre una ruta primero.");
                return;
            }

            System.out.println("\nRutas disponibles:");
            for (int i = 0; i < rutas.size(); i++) {
                System.out.println((i + 1) + ". " + rutas.get(i).getCodigo()
                        + " - " + rutas.get(i).getCiudadOrigen()
                        + " -> " + rutas.get(i).getCiudadDestino());
            }

            System.out.print("Seleccione una ruta: ");
            int seleccion = scanner.nextInt() - 1;

            if (seleccion < 0 || seleccion >= rutas.size()) {
                System.out.println("Selección no válida.");
                return;
            }

            Ruta ruta = rutas.get(seleccion);
            vehiculoService.registrarVehiculo(tipo, placa, ruta);

        } catch (Exception e) {
            System.out.println("Error al registrar vehículo: " + e.getMessage());
        }
    }
}