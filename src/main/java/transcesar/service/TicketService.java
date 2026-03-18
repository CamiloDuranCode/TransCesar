package transcesar.service;

import transcesar.dao.TicketDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;

import java.io.IOException;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();

    private int cuposDisponibles = 10;

    public void venderTicket(Pasajero pasajero, Conductor conductor, double tarifaBase,
                             String origen, String destino) throws IOException {

        if(cuposDisponibles <= 0){
            System.out.println("No hay cupos disponibles");
            return;
        }


    }

}
