package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();

    private int cuposDisponibles = 10;

    private double totalRecaudado = 0.0;
    private Map<String, Integer> pasajerosPorTipo = new HashMap<>();

    private static final List<MonthDay> FESTIVOS = Arrays.asList(
            MonthDay.of(1,  1),   // Año Nuevo
            MonthDay.of(1,  6),   // Reyes Magos
            MonthDay.of(3, 24),   // San José
            MonthDay.of(5,  1),   // Día del Trabajo
            MonthDay.of(6, 29),   // San Pedro y San Pablo
            MonthDay.of(7,  4),   // San Juan
            MonthDay.of(7, 20),   // Independencia
            MonthDay.of(8,  7),   // Batalla de Boyacá
            MonthDay.of(8, 18),   // Asunción de la Virgen
            MonthDay.of(10, 13),  // Día de la Raza
            MonthDay.of(11,  3),  // Todos los Santos
            MonthDay.of(11, 10),  // Independencia de Cartagena
            MonthDay.of(12,  8),  // Inmaculada Concepción
            MonthDay.of(12, 25)   // Navidad
    );

    public TicketService() {
        pasajerosPorTipo.put("ADULTO_MAYOR", 0);
        pasajerosPorTipo.put("ESTUDIANTE",   0);
        pasajerosPorTipo.put("REGULAR",      0);
    }

    public void venderTicket(Pasajero pasajero, Conductor conductor, double tarifaBase,
                             String origen, String destino) throws IOException {

        if (cuposDisponibles <= 0) {
            System.out.println("No hay cupos disponibles");
            return;
        }

        Vehiculo vehiculo = conductor.getVehiculo();

        if (vehiculo == null) {
            System.out.println("El conductor no tiene un vehículo asignado");
            return;
        }

        Ticket ticket = new Ticket(
                pasajero,
                vehiculo,
                LocalDate.now(),
                origen,
                destino
        );

        ticketDAO.guardar(ticket);
        actualizarEstadisticas(ticket);
        cuposDisponibles--;

        System.out.println("Ticket vendido correctamente. Valor final: $" + ticket.getValorFinal());
    }


    public boolean esFestivo(LocalDate fecha) {
        MonthDay dia = MonthDay.from(fecha);
        return FESTIVOS.contains(dia);
    }

    private void actualizarEstadisticas(Ticket ticket) {
        totalRecaudado += ticket.getValorFinal();

        String tipo = ticket.getPasajero().getClass().getSimpleName();
        switch (tipo) {
            case "PasajeroAdultoMayor":
                pasajerosPorTipo.put("ADULTO_MAYOR",
                        pasajerosPorTipo.get("ADULTO_MAYOR") + 1);
                break;
            case "PasajeroEstudiante":
                pasajerosPorTipo.put("ESTUDIANTE",
                        pasajerosPorTipo.get("ESTUDIANTE") + 1);
                break;
            default:
                pasajerosPorTipo.put("REGULAR",
                        pasajerosPorTipo.get("REGULAR") + 1);
        }
    }

    public void mostrarEstadisticas() {
        System.out.println("\n========== ESTADÍSTICAS ==========");
        System.out.println("Total recaudado: $" + totalRecaudado);
        System.out.println("\nPasajeros por tipo:");
        System.out.println("  Adultos Mayores : " + pasajerosPorTipo.get("ADULTO_MAYOR"));
        System.out.println("  Estudiantes     : " + pasajerosPorTipo.get("ESTUDIANTE"));
        System.out.println("  Regulares       : " + pasajerosPorTipo.get("REGULAR"));
        System.out.println("===================================\n");
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }
}
