package transcesar.model;

public class PasajeroEstudiante extends Pasajero implements Imprimible{

    public PasajeroEstudiante(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.15;
    }

    @Override
    public String imprimirDetalle() {
        return "Pasajero Estudiante: " + nombre + " - " + cedula;
    }
}
