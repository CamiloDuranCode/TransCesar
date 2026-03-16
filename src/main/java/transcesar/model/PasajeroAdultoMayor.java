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
    public void imprimirDetalle() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Cedula: " + cedula);
    }


}
