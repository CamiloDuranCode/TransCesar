package transcesar.model;

public class Conductor extends Persona implements Imprimible{

    protected String numLicencia;
    protected String categoria;

    public Conductor(String cedula, String nombre, String numLicencia, String categoria) {
        super(cedula, nombre);
        this.numLicencia = numLicencia;
        this.categoria = categoria;
    }

    @Override
    public String imprimirDetalle() {
        return "Conductor: " + nombre + " - " + cedula + " - " + numLicencia + " - " + categoria;
    }
}
