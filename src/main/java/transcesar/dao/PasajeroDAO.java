package transcesar.dao;

import transcesar.model.Pasajero;
import java.io.*;
import java.util.*;

public class PasajeroDAO {

    private static final String ARCHIVO = "pasajeros.txt";

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
