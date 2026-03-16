package transcesar.model;

public class PasajeroRegular extends Pasajero implements Imprimible{

    public PasajeroRegular(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.0;
    }

    @Override
    public String imprimirDetalle() {
        return "Pasajero Regular: " + nombre + " - " + cedula;
    }

}
