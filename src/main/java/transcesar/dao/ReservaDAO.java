package transcesar.dao;

import transcesar.model.Pasajero;
import transcesar.model.Reserva;
import transcesar.model.Vehiculo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaDAO {

    private static final String ARCHIVO_RESERVAS = "reservas.txt";

    private PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public void guardar(Reserva reserva) throws IOException {
        List<Reserva> reservas = listarTodos();
        reservas.removeIf(r -> r.getIdReserva().equals(reserva.getIdReserva()));
        reservas.add(reserva);
        guardarTodos(reservas);
    }

    public Reserva buscarPorId(String idReserva) throws IOException {
        return listarTodos().stream()
                .filter(r -> r.getIdReserva().equals(idReserva))
                .findFirst()
                .orElse(null);
    }

    public List<Reserva> listarTodos() throws IOException {
        List<Reserva> reservas = new ArrayList<>();
        File archivo = new File(ARCHIVO_RESERVAS);

        if (!archivo.exists()) {
            return reservas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Reserva reserva = deserializar(linea);
                if (reserva != null) {
                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }

    public List<Reserva> buscarPorPasajero(String cedulaPasajero) throws IOException {
        return listarTodos().stream()
                .filter(r -> r.getPasajero().getCedula().equals(cedulaPasajero))
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarPorFecha(LocalDate fecha) throws IOException {
        return listarTodos().stream()
                .filter(r -> r.getFechaViaje().equals(fecha))
                .collect(Collectors.toList());
    }

    public List<Reserva> buscarPorEstado(Reserva.EstadoReserva estado) throws IOException {
        return listarTodos().stream()
                .filter(r -> r.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public boolean eliminar(String idReserva) throws IOException {
        List<Reserva> reservas = listarTodos();
        boolean eliminado = reservas.removeIf(r -> r.getIdReserva().equals(idReserva));

        if (eliminado) {
            guardarTodos(reservas);
        }
        return eliminado;
    }

    private void guardarTodos(List<Reserva> reservas) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_RESERVAS))) {
            for (Reserva reserva : reservas) {
                writer.write(serializar(reserva));  // Cambiado de bw a writer
                writer.newLine();
            }
        }
    }

    private String serializar(Reserva reserva) {
        return String.join("|",
                reserva.getIdReserva(),
                reserva.getPasajero().getCedula(),
                reserva.getVehiculo().getPlaca(),
                reserva.getFechaViaje().toString(),
                reserva.getOrigen(),
                reserva.getDestino(),
                reserva.getEstado().toString(),
                reserva.getFechaCreacion().toString()
        );
    }

    private Reserva deserializar(String linea) {
        try {
            String[] partes = linea.split("\\|");

            if (partes.length >= 8) {  // Cambiado de 7 a 8
                String idReserva = partes[0];
                String cedulaPasajero = partes[1];
                String placaVehiculo = partes[2];
                LocalDate fechaViaje = LocalDate.parse(partes[3]);
                String origen = partes[4];
                String destino = partes[5];
                Reserva.EstadoReserva estado = Reserva.EstadoReserva.valueOf(partes[6]);
                LocalDate fechaCreacion = LocalDate.parse(partes[7]);

                Pasajero pasajero = pasajeroDAO.buscarPorCedula(cedulaPasajero);
                Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(placaVehiculo);

                if (pasajero == null || vehiculo == null) {
                    System.err.println("No se encontró pasajero o vehículo para la reserva: " + idReserva);
                    return null;
                }

                Reserva reserva = new Reserva(
                        idReserva,
                        pasajero,
                        vehiculo,
                        fechaViaje,
                        origen,
                        destino,
                        fechaCreacion
                );

                reserva.setEstado(estado);
                return reserva;
            }
        } catch (Exception e) {
            System.err.println("Error deserializando reserva: " + linea);
            e.printStackTrace();
        }
        return null;
    }




}
