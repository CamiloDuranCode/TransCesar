package transcesar.dao;

import transcesar.model.Conductor;
import java.io.*;
import java.util.*;

public class ConductorDAO {

    private static final String ARCHIVO = "conductores.txt";

    public void guardar(Conductor conductor) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true));

        String linea = conductor.getNombre() + ";" +
                conductor.getCedula() + ";" +
                conductor.getNumLicencia() + ";" +
                conductor.getCategoria();

        bw.write(linea);
        bw.newLine();
        bw.close();
    }

    public List<Conductor> cargar() throws IOException {

        List<Conductor> lista = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        String linea;

        while((linea = br.readLine()) != null){

            String[] datos = linea.split(";");

            Conductor c = new Conductor(
                    datos[1],
                    datos[0],
                    datos[2],
                    datos[3]
            );
            lista.add(c);
        }
        br.close();
        return lista;
    }
}
