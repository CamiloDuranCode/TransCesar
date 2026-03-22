package transcesar.dao;

import transcesar.model.Conductor;
import transcesar.model.Vehiculo;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ConductorDAO {

    private static final String ARCHIVO = "datos/conductores.txt";

    public void guardar(Conductor conductor) throws IOException {
        List<Conductor> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (archivo.exists()) {
            lista = cargar(new ArrayList<>());
            lista.removeIf(c -> c.getCedula().equals(conductor.getCedula()));
        }
        lista.add(conductor);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Conductor c : lista) {
                String placaVehiculo = c.getVehiculo() != null
                        ? c.getVehiculo().getPlaca() : "";
                String linea = c.getCedula() + ";" +
                        c.getNombre() + ";" +
                        c.getFechaNacimiento() + ";" +
                        c.getNumLicencia() + ";" +
                        c.getCategoria() + ";" +
                        placaVehiculo;
                bw.write(linea);
                bw.newLine();
            }
        }
    }

    public List<Conductor> cargar(List<Vehiculo> vehiculos) throws IOException {
        List<Conductor> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    LocalDate fechaNac = LocalDate.parse(datos[2].trim());
                    Vehiculo vehiculo = null;
                    if (datos.length >= 6 && !datos[5].trim().isEmpty()) {
                        String placa = datos[5].trim();
                        vehiculo = vehiculos.stream()
                                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                                .findFirst().orElse(null);
                    }
                    Conductor c = new Conductor(
                            datos[0].trim(),
                            datos[1].trim(),
                            fechaNac,
                            datos[3].trim(),
                            vehiculo,
                            datos[4].trim()
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public Conductor buscarPorCedula(String cedula, List<Vehiculo> vehiculos) throws IOException {
        return cargar(vehiculos).stream()
                .filter(c -> c.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }
}