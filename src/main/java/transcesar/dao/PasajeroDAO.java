package transcesar.dao;

import transcesar.model.Pasajero;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PasajeroDAO {

    private static final String ARCHIVO = "datos/pasajeros.txt";

    public void guardar(Pasajero pasajero) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            String linea = pasajero.getCedula() + ";" +
                    pasajero.getNombre() + ";" +
                    pasajero.getFechaNacimiento();
            bw.write(linea);
            bw.newLine();
        }
    }

    public List<Pasajero> listarTodos() throws IOException {
        List<Pasajero> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Pasajero p = deserializarPasajero(linea);
                if (p != null) lista.add(p);
            }
        }
        return lista;
    }

    private Pasajero deserializarPasajero(String linea) {
        try {
            String[] datos = linea.split(";");
            if (datos.length >= 3) {
                String cedula = datos[0].trim();
                String nombre = datos[1].trim();
                LocalDate fechaNacimiento = LocalDate.parse(datos[2].trim());
                return new Pasajero(cedula, nombre, fechaNacimiento);
            }
        } catch (Exception e) {
            System.out.println("Error deserializando pasajero: " + linea);
        }
        return null;
    }

    public Pasajero buscarPorCedula(String cedula) throws IOException {
        return listarTodos().stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public List<Pasajero> cargar() throws IOException {
        return listarTodos();
    }
}