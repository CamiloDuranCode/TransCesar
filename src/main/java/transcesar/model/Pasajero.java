package transcesar.model;

import java.time.LocalDate;
import transcesar.model.Imprimible;

public class Pasajero extends Persona  {

    public Pasajero(String cedula, String nombre, LocalDate fechaNacimiento) {
        super(cedula, nombre,fechaNacimiento);
        
    }

    public double calcularDescuento() {
        if (getEdad() >= 60) {
            return 0.30;
        } else if (getEdad() >= 18 && getEdad() < 25) {
            return 0.15;
        }
        return 0.0;
    }

    public String getTipoPasajero() {
        if (getEdad() >= 60) return "ADULTO_MAYOR";
        if (getEdad() >= 18 && getEdad() < 25) return "ESTUDIANTE";
        return "REGULAR";
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("===== PASAJERO =====");
        System.out.println("Cédula    : " + cedula);
        System.out.println("Nombre    : " + nombre);
        System.out.println("Edad      : " + getEdad());
        System.out.println("Tipo      : " + getTipoPasajero());
        System.out.println("Descuento : " + (calcularDescuento() * 100) + "%");
        System.out.println("====================");
    }
}
