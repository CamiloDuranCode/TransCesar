package transcesar.view;

import transcesar.model.Ruta;
import transcesar.service.RutaService;
import java.util.List;
import java.util.Scanner;

public class MenuRutas {

    static Scanner scanner = new Scanner(System.in);
    static RutaService rutaService = new RutaService();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE RUTAS =====");
            System.out.println("1. Registrar ruta");
            System.out.println("2. Listar rutas");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarRuta();
                case 2 -> listarRutas();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarRuta() {
        try {
            System.out.print("Código de ruta: ");
            String codigo = scanner.next();
            System.out.print("Ciudad origen: ");
            String origen = scanner.next();
            System.out.print("Ciudad destino: ");
            String destino = scanner.next();
            System.out.print("Distancia (km): ");
            double distancia = scanner.nextDouble();
            System.out.print("Tiempo estimado (minutos): ");
            int tiempo = scanner.nextInt();
            rutaService.registrarRuta(codigo, origen, destino, distancia, tiempo);
        } catch (Exception e) {
            System.out.println("Error al registrar ruta: " + e.getMessage());
        }
    }

    private static void listarRutas() {
        List<Ruta> rutas = rutaService.listarRutas();
        if (rutas.isEmpty()) {
            System.out.println("No hay rutas registradas.");
            return;
        }
        rutas.forEach(Ruta::imprimirDetalle);
    }
}