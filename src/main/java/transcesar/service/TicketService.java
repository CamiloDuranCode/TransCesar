package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;

import java.io.IOException;
import java.time.LocalDate;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();

    private int cuposDisponibles = 10;

    public void venderTicket(Pasajero pasajero, Conductor conductor, double tarifaBase,
                             String origen, String destino) throws IOException {

        if(cuposDisponibles <= 0){
            System.out.println("No hay cupos disponibles");
            return;
        }

        double descuento = pasajero.calcularDescuento();

        double valorFinal = tarifaBase * (1 - descuento);

        Vehiculo vehiculo = conductor.getVehiculo();


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

        cuposDisponibles--;

        System.out.println("Ticket vendido correctamente. Valor final: " + ticket.getValorFinal());

    }
}
