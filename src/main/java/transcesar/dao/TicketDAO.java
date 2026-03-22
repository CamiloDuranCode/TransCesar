package transcesar.dao;

import transcesar.model.Pasajero;
import transcesar.model.Ticket;
import transcesar.model.Vehiculo;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TicketDAO {

    private static final String ARCHIVO = "datos/tickets.txt";
    private final PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public void guardar(Ticket ticket) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = ticket.getPasajero().getCedula() + ";" +
                    ticket.getVehiculo().getPlaca() + ";" +
                    ticket.getFechaCompra() + ";" +
                    ticket.getOrigen() + ";" +
                    ticket.getDestino() + ";" +
                    ticket.getValorFinal();
            bw.write(linea);
            bw.newLine();
        }
    }

    public List<Ticket> cargarTickets() throws IOException {
        List<Ticket> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Ticket t = deserializar(linea);
                if (t != null) lista.add(t);
            }
        }
        return lista;
    }

    private Ticket deserializar(String linea) {
        try {
            String[] datos = linea.split(";");
            if (datos.length >= 6) {
                Pasajero pasajero = pasajeroDAO.buscarPorCedula(datos[0].trim());
                Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(datos[1].trim());
                LocalDate fechaCompra = LocalDate.parse(datos[2].trim());
                String origen = datos[3].trim();
                String destino = datos[4].trim();
                if (pasajero != null && vehiculo != null) {
                    return new Ticket(pasajero, vehiculo, fechaCompra, origen, destino);
                }
            }
        } catch (Exception e) {
            System.out.println("Error deserializando ticket: " + linea);
        }
        return null;
    }
}