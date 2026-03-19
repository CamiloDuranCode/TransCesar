package transcesar.dao;

import transcesar.model.Ruta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RutaDao {

    private static final String ARCHIVO = "rutas.txt";
    private static final String SEP     = ";";

    public void guardarRuta(Ruta ruta) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(ARCHIVO, true))) {

            String linea = ruta.getCodigo()
                    + SEP + ruta.getCiudadOrigen()
                    + SEP + ruta.getCiudadDestino()
                    + SEP + ruta.getDistanciaKm()
                    + SEP + ruta.getTiempoEstimadoMinutos();

            bw.write(linea);
            bw.newLine();
            System.out.println("[RutaDAO] Guardada: " + linea);

        } catch (IOException e) {
            System.out.println("[RutaDAO] Error al guardar: " + e.getMessage());
        }
    }

    public List<Ruta> cargarRutas() {
        List<Ruta> lista   = new ArrayList<>();
        File       archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("[RutaDAO] Archivo no encontrado, se omite: " + ARCHIVO);
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int nLinea = 0;

            while ((linea = br.readLine()) != null) {
                nLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] p = linea.split(SEP);

                if (p.length != 5) {
                    System.out.println("[RutaDAO] Línea " + nLinea
                            + " malformada: \"" + linea + "\" — omitida.");
                    continue;
                }

                try {
                    String codigo      = p[0].trim();
                    String origen      = p[1].trim();
                    String destino     = p[2].trim();
                    double distancia   = Double.parseDouble(p[3].trim());
                    int    tiempo      = Integer.parseInt(p[4].trim());

                    lista.add(new Ruta(codigo, origen, destino, distancia, tiempo));

                } catch (NumberFormatException e) {
                    System.out.println("[RutaDAO] Datos inválidos en línea "
                            + nLinea + " — omitida.");
                }
            }

            System.out.println("[RutaDAO] " + lista.size()
                    + " ruta(s) cargada(s) desde " + ARCHIVO);

        } catch (IOException e) {
            System.out.println("[RutaDAO] Error al leer: " + e.getMessage());
        }

        return lista;
    }
}