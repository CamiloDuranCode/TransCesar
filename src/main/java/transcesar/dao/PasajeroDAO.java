package transcesar.dao;

import transcesar.model.Pasajero;
import java.io.*;
import java.util.*;

public class PasajeroDAO {

    private static final String ARCHIVO = "pasajeros.txt";

    public Pasajero buscarPorCedula(String cedula) throws IOException {
        return listarTodos().stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public List<Pasajero> listarTodos() throws IOException {
        List<Pasajero> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                Pasajero pasajero = deserializarPasajero(linea);
                if (pasajero != null) {
                    lista.add(pasajero);
                }
            }
        }
        return lista;
    }

    private Pasajero deserializarPasajero(String linea) {
        return null;
    }

    public void guardar(Pasajero pasajero) throws IOException {


        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true));

        String tipo = pasajero.getClass().getSimpleName();

        String linea = pasajero.getNombre() + ";" +
                pasajero.getCedula() + ";" +
                tipo;

        bw.write(linea);
        bw.newLine();
        bw.close();
    }


}
