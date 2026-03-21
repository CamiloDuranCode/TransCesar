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

    private double totalRecaudado = 0.0;
    private Map<String, Integer> pasajerosPorTipo = new HashMap<>();

    public TicketService() {

        pasajerosPorTipo.put("ADULTO_MAYOR", 0);
        pasajerosPorTipo.put("ESTUDIANTE", 0);
        pasajerosPorTipo.put("REGULAR", 0);
    }

    public void venderTicket(Pasajero pasajero, Conductor conductor,
                             String origen, String destino) throws IOException {

        Vehiculo vehiculo = conductor.getVehiculo();

        if (vehiculo == null) {
            System.out.println("El conductor no tiene un vehículo asignado.");
            return;
        }

        if (vehiculo.getContadorPasajeros() >= vehiculo.getCapacidadMaxima()) {
            System.out.println("No hay cupos disponibles en el vehículo " + vehiculo.getPlaca());
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
        vehiculo.incrementarPasajeros();
        actualizarEstadisticas(ticket);

        System.out.println("Ticket vendido correctamente. Valor final: $" + ticket.getValorFinal());
    }

    public void actualizarEstadisticas(Ticket ticket) {
        totalRecaudado += ticket.getValorFinal();
        String tipo = ticket.getPasajero().getTipoPasajero();
        pasajerosPorTipo.put(tipo, pasajerosPorTipo.getOrDefault(tipo, 0) + 1);
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


    public void resumenDiaActual() {
        LocalDate hoy = LocalDate.now();
        List<Ticket> tickets = ticketDAO.cargarTickets();
        long totalHoy = tickets.stream()
                .filter(t -> t.getFechaCompra().equals(hoy))
                .count();
        double recaudadoHoy = tickets.stream()
                .filter(t -> t.getFechaCompra().equals(hoy))
                .mapToDouble(Ticket::getValorFinal)
                .sum();
        System.out.println("===== RESUMEN DEL DÍA =====");
        System.out.println("Fecha: " + hoy);
        System.out.println("Tickets vendidos: " + totalHoy);
        System.out.println("Total recaudado: $" + recaudadoHoy);
        System.out.println("===========================");
    }

    public void listarTicketsPorFecha(LocalDate fecha) {
        ticketDAO.cargarTickets().stream()
                .filter(t -> t.getFechaCompra().equals(fecha))
                .forEach(Ticket::imprimirDetalle);
    }

    public void listarTicketsPorTipoVehiculo(String tipo) {
        ticketDAO.cargarTickets().stream()
                .filter(t -> t.getVehiculo().getClass().getSimpleName().equalsIgnoreCase(tipo))
                .forEach(Ticket::imprimirDetalle);
    }

    public void listarTicketsPorTipoPasajero(String tipo) {
        ticketDAO.cargarTickets().stream()
                .filter(t -> t.getPasajero().getTipoPasajero().equalsIgnoreCase(tipo))
                .forEach(Ticket::imprimirDetalle);
    }
}
