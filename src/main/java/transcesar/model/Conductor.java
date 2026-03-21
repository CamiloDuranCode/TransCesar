package transcesar.model;

import java.time.LocalDate;

public class Conductor extends Persona {

    private String numLicencia;
    private String categoria;
    private Vehiculo vehiculo;

    public Conductor(String cedula, String nombre, LocalDate fecchaNacimiento, String numLicencia, String categoria) {
        super(cedula, nombre,fecchaNacimiento);
        this.numLicencia = numLicencia;
        this.categoria = categoria;
    }

    public Conductor(String cedula, String nombre, LocalDate fecchaNacimiento, String numLicencia, Vehiculo vehiculo, String categoria) {
        super(cedula, nombre,fecchaNacimiento );
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
        System.out.println("Edad: " + getEdad() + " años");
        System.out.println("Numero de licencia: " + numLicencia);
        System.out.println("Categoria: " + categoria);
    }
}
