package transcesar.view;

import transcesar.service.VehiculoService;
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
        System.out.print("Tipo (Bus/Buseta/MicroBus): ");
        String tipo = scanner.next();
        System.out.print("Placa: ");
        String placa = scanner.next();
        System.out.print("Ruta: ");
        String ruta = scanner.next();
        vehiculoService.registrarVehiculo(tipo, placa, ruta);
    }
}