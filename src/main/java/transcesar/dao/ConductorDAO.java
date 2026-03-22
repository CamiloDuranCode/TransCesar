package transcesar.dao;

import transcesar.model.Conductor;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ConductorDAO {

    private static final String ARCHIVO = "datos/conductores.txt";

    public void guardar(Conductor conductor) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = conductor.getCedula() + ";" +
                    conductor.getNombre() + ";" +
                    conductor.getFechaNacimiento() + ";" +
                    conductor.getNumLicencia() + ";" +
                    conductor.getCategoria();
            bw.write(linea);
            bw.newLine();
        }
    }

    public List<Conductor> cargar() throws IOException {
        List<Conductor> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    LocalDate fechaNac = LocalDate.parse(datos[2].trim());
                    Conductor c = new Conductor(
                            datos[0].trim(),
                            datos[1].trim(),
                            fechaNac,
                            datos[3].trim(),
                            datos[4].trim()
                    );
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public Conductor buscarPorCedula(String cedula) throws IOException {
        return cargar().stream()
                .filter(c -> c.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }
}