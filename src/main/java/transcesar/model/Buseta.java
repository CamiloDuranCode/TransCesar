package transcesar.model;

public class Buseta extends Vehiculo {

    private final double TARIFA = 8000;

    public Buseta(String placa, Ruta ruta, boolean estado) {

        super(placa, ruta, 19, estado);
    }

    @Override
    public double tarifabase() {
        return TARIFA;
    }

    @Override
    public String getTipoVehiculo() {
        return "Buseta";
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("Tipo: Buseta");
        System.out.println("Placa: " + placa);
        System.out.println("Ruta: " + (ruta != null ? ruta.getCodigo() : "Sin ruta"));
        System.out.println("Capacidad máxima: " + capacidadMaxima);
        System.out.println("Pasajeros actuales: " + contadorPasajeros);
        System.out.println("Tarifa base: $" + TARIFA);
    }
}