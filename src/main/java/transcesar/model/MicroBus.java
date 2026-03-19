package transcesar.model;

public class MicroBus extends Vehiculo implements Imprimible {

    private final double TARIFA = 10000;

    public MicroBus(String placa, Ruta ruta, boolean estado) {
        super(placa, ruta, 25, estado);
    }

    @Override
    public double tarifabase() { return TARIFA; }

    @Override
    public String getTipoVehiculo() { return "MicroBus"; }

    @Override
    public void imprimirDetalle() {
        System.out.println("Tipo: MicroBus");
        System.out.println("Placa: " + placa);
        System.out.println("Ruta: " + (ruta != null ? ruta.getCodigo() : "Sin ruta"));
        System.out.println("Capacidad máxima: " + capacidadMaxima);
        System.out.println("Pasajeros actuales: " + contadorPasajeros);
        System.out.println("Tarifa base: $" + TARIFA);
    }
}
