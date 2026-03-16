package transcesar.model;

import java.time.LocalDate;

public class Ticket implements Imprimible, Calculable {

    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaCompra;
    private String origen;
    private String destino;
    private double valorFinal;

    public Ticket(Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaCompra,
                  String origen, String destino) {
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCompra = fechaCompra;
        this.origen = origen;
        this.destino = destino;
        this.valorFinal = calcularValor();
    }

    @Override
    public double calcularValor() {
        double tarifa = vehiculo.tarifabase();
        double descuento = pasajero.calcularDescuento();
        return tarifa * (1 - descuento);
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("===== TICKET DE VIAJE =====");
        System.out.println("Pasajero : " + pasajero.getNombre());
        System.out.println("Vehículo : " + vehiculo.getPlaca());
        System.out.println("Origen   : " + origen);
        System.out.println("Destino  : " + destino);
        System.out.println("Fecha    : " + fechaCompra);
        System.out.println("Valor    : $" + valorFinal);
        System.out.println("===========================");
    }

    // Getters
    public Pasajero getPasajero() { return pasajero; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public double getValorFinal() { return valorFinal; }
}