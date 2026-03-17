package transcesar.dao;

import transcesar.model.Ticket;

import java.io.*;
import java.util.*;

public class TicketDAO {

    private static final String ARCHIVO = "tickets.txt";

    public void guardar(Ticket ticket) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true));

        String linea = ticket.getPasajero() + ";" +
                ticket.getVehiculo() + ";" +
                ticket.getFechaCompra() + ";" +
                ticket.getOrigen() + ";" +
                ticket.getDestino() + ";" +
                ticket.getValorFinal();

        bw.write(linea);
        bw.newLine();
        bw.close();
    }



}
