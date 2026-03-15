package transcesar.model;

public class Bus extends Vehiculo implements Imprimible{

    private final double TARIFA = 15000;

    public Bus(String placa, String ruta) {
        super(placa, ruta, 45);
    }

    @Override
    public double tarifabase() {
        return TARIFA;
    }

    @Override
    public String getTipoVehiculo() {
        return "Bus";
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("Tipo: Bus");
        System.out.println("Placa: " + placa);
        System.out.println("Ruta: " + ruta);
        System.out.println("Capacidad máxima: " + capacidadMaxima);
        System.out.println("Pasajeros actuales: " + contadorPasajeros);
        System.out.println("Tarifa base: $" + TARIFA);
    }
}
