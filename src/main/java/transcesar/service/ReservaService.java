package transcesar.service;

import transcesar.dao.ReservaDAO;
import transcesar.dao.TicketDAO;
import transcesar.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReservaService {

    private ReservaDAO reservaDAO;
    private TicketDAO ticketDAO;
    private TicketService ticketService;


    private static final int MAX_TICKETS_POR_DIA = 3;

    public ReservaService() {
        this.reservaDAO = new ReservaDAO();
        this.ticketDAO = new TicketDAO();
        this.ticketService = new TicketService();
    }


    public Reserva crearReserva(Pasajero pasajero, Vehiculo vehiculo,
                                LocalDate fechaViaje, String origen, String destino)
            throws IOException {

        if (!validarLimiteTicketsPorDia(pasajero, fechaViaje)) {
            throw new IllegalStateException(
                    "El pasajero " + pasajero.getNombre() +
                            " ya tiene " + MAX_TICKETS_POR_DIA +
                            " tickets para la fecha " + fechaViaje
            );
        }

        if (!validarDisponibilidadVehiculo(vehiculo, fechaViaje)) {
            throw new IllegalStateException(
                    "El vehículo con placa " + vehiculo.getPlaca() +
                            " no está disponible para la fecha " + fechaViaje
            );
        }

        String idReserva = generarIdReserva();
        Reserva reserva = new Reserva(
                idReserva,
                pasajero,
                vehiculo,
                fechaViaje,
                origen,
                destino,
                LocalDateTime.now()
        );


        reservaDAO.guardar(reserva);

        System.out.println("Reserva creada exitosamente: " + idReserva);
        return reserva;
    }


    public Ticket confirmarReserva(String idReserva, Conductor conductor)
            throws IOException {

        Reserva reserva = reservaDAO.buscarPorId(idReserva);

        if (reserva == null) {
            throw new IllegalArgumentException("No existe reserva con ID: " + idReserva);
        }

        if (reserva.getEstado() != Reserva.EstadoReserva.ACTIVA) {
            throw new IllegalStateException(
                    "La reserva " + idReserva + " no está confirmada. Estado actual: " +
                            reserva.getEstado()
            );
        }

        if (reserva.getFechaViaje().isBefore(LocalDate.now())) {
            throw new IllegalStateException(
                    "No se puede confirmar una reserva con fecha de viaje pasada: " +
                            reserva.getFechaViaje()
            );
        }

        if (!validarLimiteTicketsPorDia(reserva.getPasajero(), reserva.getFechaViaje())) {
            reserva.cancelar();
            reservaDAO.guardar(reserva);

            throw new IllegalStateException(
                    "El pasajero ya alcanzó el límite de " + MAX_TICKETS_POR_DIA +
                            " tickets para esta fecha. Reserva cancelada."
            );
        }
        if (conductor.getVehiculo() == null) {
            throw new IllegalArgumentException(
                    "El conductor no tiene un vehículo asignado"
            );
        }

        if (!conductor.getVehiculo().getPlaca().equals(reserva.getVehiculo().getPlaca())) {
            throw new IllegalArgumentException(
                    "El vehículo del conductor no coincide con el de la reserva"
            );
        }

        Ticket ticket = new Ticket(
                reserva.getPasajero(),
                reserva.getVehiculo(),
                LocalDate.now(), // fecha de compra
                reserva.getOrigen(),
                reserva.getDestino()
        );

        ticketDAO.guardar(ticket);

        ticketService.actualizarEstadisticas(ticket);

        reserva.convertir();
        reservaDAO.guardar(reserva);

        System.out.println("Reserva " + idReserva + " convertida a ticket exitosamente");
        return ticket;
    }


    public boolean cancelarReserva(String idReserva) throws IOException {
        Reserva reserva = reservaDAO.buscarPorId(idReserva);

        if (reserva == null) {
            return false;
        }

        if (reserva.getEstado() == Reserva.EstadoReserva.ACTIVA) {
            reserva.cancelar();
            reservaDAO.guardar(reserva);
            System.out.println("Reserva " + idReserva + " cancelada");
            return true;
        }

        return false;
    }

    public int verificarReservasVencidas() throws IOException {
        List<Reserva> todas = reservaDAO.listarTodos();
        int canceladas = 0;

        for (Reserva reserva : todas) {
            if (reserva.getEstado() == Reserva.EstadoReserva.ACTIVA &&
                    reserva.estaVencida()) {
                reserva.cancelar();
                reservaDAO.guardar(reserva);
                canceladas++;
                System.out.println("Reserva vencida cancelada: " + reserva.getIdReserva());
            }
        }

        System.out.println("Total de reservas vencidas canceladas: " + canceladas);
        return canceladas;
    }

    private boolean validarLimiteTicketsPorDia(Pasajero pasajero, LocalDate fecha)
            throws IOException {

        List<Reserva> reservasDelDia = reservaDAO.buscarPorPasajero(pasajero.getCedula())
                .stream()
                .filter(r -> r.getFechaViaje().equals(fecha))
                .filter(r -> r.getEstado() == Reserva.EstadoReserva.ACTIVA ||
                        r.getEstado() == Reserva.EstadoReserva.CONVERTIDA)
                .toList();

        return reservasDelDia.size() < MAX_TICKETS_POR_DIA;
    }

    private boolean validarDisponibilidadVehiculo(Vehiculo vehiculo, LocalDate fecha)
            throws IOException {

        long reservasActivas = reservaDAO.buscarPorFecha(fecha)
                .stream()
                .filter(r -> r.getVehiculo().getPlaca().equals(vehiculo.getPlaca()))
                .filter(r -> r.getEstado() == Reserva.EstadoReserva.ACTIVA)
                .count();

        return reservasActivas < vehiculo.getCapacidadMaxima();
    }

    private String generarIdReserva() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    public List<Reserva> buscarReservasPorPasajero(String cedulaPasajero)
            throws IOException {
        return reservaDAO.buscarPorPasajero(cedulaPasajero);
    }


    public List<Reserva> buscarReservasPorFecha(LocalDate fecha)
            throws IOException {
        return reservaDAO.buscarPorFecha(fecha);
    }

    public List<Reserva> buscarReservasPorEstado(Reserva.EstadoReserva estado)
            throws IOException {
        return reservaDAO.buscarPorEstado(estado);
    }

    public List<Reserva> listarTodasLasReservas() throws IOException {
        return reservaDAO.listarTodos();
    }
}
