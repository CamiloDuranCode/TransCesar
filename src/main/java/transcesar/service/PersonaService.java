package transcesar.service;

import transcesar.dao.ConductorDAO;
import transcesar.dao.PasajeroDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;

import java.io.IOException;

public class PersonaService {

    private ConductorDAO conductorDAO = new ConductorDAO();
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public void registrarConductor(Conductor conductor) throws IOException {
        conductorDAO.guardar(conductor);
    }

    public void registrarPasajero(Pasajero pasajero) throws IOException {
        pasajeroDAO.guardar(pasajero);
    }
}
