package transcesar.model;

public class PasajeroAdultoMayor extends Pasajero implements Imprimible{

    public PasajeroAdultoMayor(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.30;
    }

    @Override
    public String imprimirDetalle() {
        return "Pasajero Adulto Mayor: " + nombre + " - " + cedula;
    }


}
