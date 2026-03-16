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
    public void imprimirDetalle() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Cedula: " + cedula);
    }
}
