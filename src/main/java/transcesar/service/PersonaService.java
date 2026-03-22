package transcesar.service;

import transcesar.dao.ConductorDAO;
import transcesar.dao.PasajeroDAO;
import transcesar.model.Conductor;
import transcesar.model.Pasajero;
import transcesar.service.VehiculoService;

import java.io.IOException;
import java.util.List;

public class PersonaService {

    private ConductorDAO conductorDAO = new ConductorDAO();
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private VehiculoService vehiculoService = new VehiculoService();

    public void registrarConductor(Conductor conductor) throws IOException {
        if (conductor.getNumLicencia() == null
                || conductor.getNumLicencia().trim().isEmpty()) {
            System.out.println("Error: el conductor debe tener licencia registrada.");
            return;
        }
        conductorDAO.guardar(conductor);
        System.out.println("Conductor registrado exitosamente.");
    }

    public List<Conductor> getConductores() throws IOException {
        return conductorDAO.cargar(vehiculoService.getVehiculos());
    }

    public List<Pasajero> getPasajeros() throws IOException {
        return pasajeroDAO.cargar();
    }

    public void registrarPasajero(Pasajero pasajero) throws IOException {
        pasajeroDAO.guardar(pasajero);
    }
}
