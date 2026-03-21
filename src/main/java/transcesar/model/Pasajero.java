package transcesar.model;

import java.time.LocalDate;

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
}
