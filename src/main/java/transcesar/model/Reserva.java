package transcesar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva implements Imprimible{

    private String idReserva;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaViaje;
    private String origen;
    private String destino;
    private EstadoReserva estado;
    private LocalDateTime fechaCreacion;

    public Reserva(String idReserva, Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje, String origen, String destino, LocalDateTime fechaCreacion) {
    }

    public enum EstadoReserva {
        ACTIVA,
        CONVERTIDA,
        CANCELADA
    }

    public Reserva(String idReserva, LocalDateTime fechaCreacion,
                   String destino, String origen,
                   LocalDate fechaViaje, Vehiculo vehiculo, Pasajero pasajero) {
        this.idReserva = idReserva;
        this.fechaCreacion = fechaCreacion;
        this.estado = EstadoReserva.ACTIVA;
        this.destino = destino;
        this.origen = origen;
        this.fechaViaje = fechaViaje;
        this.vehiculo = vehiculo;
        this.pasajero = pasajero;
    }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public EstadoReserva getEstado() { return estado; }

    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public String getDestino() {return destino;}

    public void setDestino(String destino) {this.destino = destino;}

    public String getOrigen() { return origen; }

    public void setOrigen(String origen) { this.origen = origen; }

    public LocalDate getFechaViaje() { return fechaViaje; }

    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }

    public Vehiculo getVehiculo() { return vehiculo; }

    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Pasajero getPasajero() {return pasajero; }

    public void setPasajero(Pasajero pasajero) { this.pasajero = pasajero; }

    public String getIdReserva() { return idReserva; }

    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }


    public boolean estaVencida() {
        LocalDateTime ahora = LocalDateTime.now();
        return estado == EstadoReserva.ACTIVA &&
                fechaCreacion.plusHours(24).isBefore(ahora);
    }


    public boolean convertir() {
        if (estado == EstadoReserva.ACTIVA) {
            this.estado = EstadoReserva.CONVERTIDA;
            return true;
        }
        return false;
    }

    public boolean cancelar() {
        if (estado == EstadoReserva.ACTIVA) {
            this.estado = EstadoReserva.CANCELADA;
            return true;
        }
        return false;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("===== RESERVA =====");
        System.out.println("ID Reserva: " + idReserva);
        System.out.println("Pasajero: " + pasajero.getNombre());
        System.out.println("Vehículo: " + vehiculo.getPlaca());
        System.out.println("Fecha viaje: " + fechaViaje);
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Estado: " + estado);
        System.out.println("Fecha creación: " + fechaCreacion);
        System.out.println("===================");
    }
}
