package transcesar.model;

public class Conductor extends Persona implements Imprimible{

    private String numLicencia;
    private String categoria;
    private Vehiculo vehiculo;

    public Conductor(String cedula, String nombre, String numLicencia, String categoria) {
        super(cedula, nombre);
        this.numLicencia = numLicencia;
        this.categoria = categoria;
    }

    public Conductor(String cedula, String nombre, String numLicencia, Vehiculo vehiculo, String categoria) {
        super(cedula, nombre);
        this.numLicencia = numLicencia;
        this.vehiculo = vehiculo;
        this.categoria = categoria;
    }

    public Vehiculo getVehiculo(){return vehiculo;}

    public String getNumLicencia() {
        return numLicencia;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Cedula: " + cedula);
        System.out.println("Numero de licencia: " + numLicencia);
        System.out.println("Categoria: " + categoria);
    }
}
