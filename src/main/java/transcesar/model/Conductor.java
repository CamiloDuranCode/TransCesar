package transcesar.model;

public class Conductor extends Persona implements Imprimible{

    private String numLicencia;
    private String categoria;

    public Conductor(String cedula, String nombre, String numLicencia, String categoria) {
        super(cedula, nombre);
        this.numLicencia = numLicencia;
        this.categoria = categoria;
    }

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
