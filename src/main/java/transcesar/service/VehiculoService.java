package transcesar.service;

import transcesar.dao.VehiculoDAO;
import transcesar.model.Conductor;
import transcesar.model.Vehiculo;
import transcesar.model.Imprimible;

import java.util.List;


public class VehiculoService {

    private final VehiculoDAO vehiculoDAO;
    private final List<Vehiculo> vehiculos;

    public VehiculoService(VehiculoDAO vehiculoDAO, List<Vehiculo> vehiculos) {
        this.vehiculoDAO = vehiculoDAO;
        this.vehiculos   = vehiculos;
    }

    public void registrarVehiculo(Vehiculo vehiculo) {
        if (placaExiste(vehiculo.getPlaca())) {
            System.out.println("Error: ya existe un vehículo con la placa "
                    + vehiculo.getPlaca());
            return;
        }
        vehiculos.add(vehiculo);
        vehiculoDAO.guardarVehiculo(vehiculo);
        System.out.println("Vehículo registrado: " + vehiculo.getPlaca());
    }

    public void asignarConductor(Vehiculo vehiculo, Conductor conductor) {
        if (conductor.getNumLicencia() == null
                || conductor.getNumLicencia().trim().isEmpty()) {
            System.out.println("Error: el conductor no tiene licencia registrada.");
            return;
        }
        System.out.println("Conductor " + conductor.getNombre()
                + " asignado al vehículo " + vehiculo.getPlaca());
    }

    public void listarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        for (Vehiculo v : vehiculos) {
            ((Imprimible) v).imprimirDetalle();
        }
    }
    private boolean placaExiste(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }
        return false;
    }

}

