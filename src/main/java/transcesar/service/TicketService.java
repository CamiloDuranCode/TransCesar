package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();

    private int cuposDisponibles = 10;

    private double totalRecaudado = 0.0;
    private Map<String, Integer> pasajerosPorTipo = new HashMap<>();

    public TicketService() {

        pasajerosPorTipo.put("ADULTO_MAYOR", 0);
        pasajerosPorTipo.put("ESTUDIANTE", 0);
        pasajerosPorTipo.put("REGULAR", 0);
    }

    public void venderTicket(Pasajero pasajero, Conductor conductor, double tarifaBase,
                             String origen, String destino) throws IOException {

        Vehiculo vehiculo = null;

        if (vehiculo.getContadorPasajeros() >= vehiculo.getCapacidadMaxima()) {
            System.out.println("No hay cupos disponibles en el vehículo " + vehiculo.getPlaca());
            return;
        }

        vehiculo = conductor.getVehiculo();


        if (vehiculo == null) {
            System.out.println("El conductor no tiene un vehículo asignado");
            return;
        }


        Ticket ticket = new Ticket(
                pasajero,
                vehiculo,
                LocalDate.now(), // Fecha actual
                origen,
                destino
        );

        ticketDAO.guardar(ticket);

        actualizarEstadisticas(ticket);

        cuposDisponibles--;

        System.out.println("Ticket vendido correctamente. Valor final: " + ticket.getValorFinal());

    }

    private void actualizarEstadisticas(Ticket ticket) {

        totalRecaudado += ticket.getValorFinal();
        
        String tipo = ticket.getPasajero().getClass().getSimpleName();
        switch(tipo) {
            case "PasajeroAdultoMayor":
                pasajerosPorTipo.put("ADULTO_MAYOR", pasajerosPorTipo.get("ADULTO_MAYOR") + 1);
                break;
            case "PasajeroEstudiante":
                pasajerosPorTipo.put("ESTUDIANTE", pasajerosPorTipo.get("ESTUDIANTE") + 1);
                break;
            default:
                pasajerosPorTipo.put("REGULAR", pasajerosPorTipo.get("REGULAR") + 1);
        }
    }

    public List<Ticket> getTickets() {
        return ticketDAO.cargarTickets();
    }

    public void mostrarEstadisticas() {
        System.out.println("\n========== ESTADÍSTICAS ==========");
        System.out.println("Total recaudado: $" + totalRecaudado);
        System.out.println("\nPasajeros por tipo:");
        System.out.println("  Adultos Mayores: " + pasajerosPorTipo.get("ADULTO_MAYOR"));
        System.out.println("  Estudiantes: " + pasajerosPorTipo.get("ESTUDIANTE"));
        System.out.println("  Regulares: " + pasajerosPorTipo.get("REGULAR"));
        System.out.println("===================================\n");
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }


}
