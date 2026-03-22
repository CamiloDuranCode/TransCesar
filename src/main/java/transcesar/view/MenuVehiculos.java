package transcesar.view;

import transcesar.model.Conductor;
import transcesar.model.Ruta;
import transcesar.model.Vehiculo;
import transcesar.service.PersonaService;
import transcesar.service.VehiculoService;
import java.util.List;
import java.util.Scanner;

public class MenuVehiculos {

    static Scanner scanner = new Scanner(System.in);
    static VehiculoService vehiculoService = new VehiculoService();
    static PersonaService personaService = new PersonaService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE VEHÍCULOS =====");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Listar vehículos");
            System.out.println("3. Asignar conductor a vehículo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarVehiculo();
                case 2 -> vehiculoService.listarVehiculos();
                case 3 -> asignarConductor();
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

    private static void asignarConductor() {
        try {
            List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
            if (vehiculos.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
                return;
            }

            System.out.println("\nVehículos disponibles:");
            for (int i = 0; i < vehiculos.size(); i++) {
                System.out.println((i + 1) + ". " + vehiculos.get(i).getPlaca()
                        + " - " + vehiculos.get(i).getTipoVehiculo());
            }
            System.out.print("Seleccione un vehículo: ");
            int selVehiculo = scanner.nextInt() - 1;

            if (selVehiculo < 0 || selVehiculo >= vehiculos.size()) {
                System.out.println("Selección no válida.");
                return;
            }

            List<Conductor> conductores = personaService.getConductores();
            if (conductores.isEmpty()) {
                System.out.println("No hay conductores registrados.");
                return;
            }

            System.out.println("\nConductores disponibles:");
            for (int i = 0; i < conductores.size(); i++) {
                System.out.println((i + 1) + ". " + conductores.get(i).getNombre()
                        + " - Licencia: " + conductores.get(i).getNumLicencia());
            }
            System.out.print("Seleccione un conductor: ");
            int selConductor = scanner.nextInt() - 1;

            if (selConductor < 0 || selConductor >= conductores.size()) {
                System.out.println("Selección no válida.");
                return;
            }

            vehiculoService.asignarConductor(
                    vehiculos.get(selVehiculo),
                    conductores.get(selConductor)
            );

        } catch (Exception e) {
            System.out.println("Error al asignar conductor: " + e.getMessage());
        }
    }
}
