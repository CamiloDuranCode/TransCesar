package transcesar.dao;

import transcesar.model.Vehiculo;
import transcesar.model.Bus;
import transcesar.model.Buseta;
import transcesar.model.MicroBus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class VehiculoDAO {

    private static final String ARCHIVO_BUSETA   = "buseta.txt";
    private static final String ARCHIVO_BUS      = "bus.txt";
    private static final String ARCHIVO_MICROBUS = "microbus.txt";
    private static final String SEP              = ";";


    public void guardarVehiculo(Vehiculo vehiculo) {
        String archivo = obtenerArchivo(vehiculo);

        if (archivo == null) {
            System.out.println("[VehiculoDAO] Tipo no reconocido: "
                    + vehiculo.getClass().getSimpleName());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(archivo, true))) {

            String linea = vehiculo.getPlaca()
                    + SEP + vehiculo.getRuta()
                    + SEP + vehiculo.getEstado();

            bw.write(linea);
            bw.newLine();
            System.out.println("[VehiculoDAO] Guardado en "
                    + archivo + " → " + linea);

        } catch (IOException e) {
            System.out.println("[VehiculoDAO] Error al escribir en "
                    + archivo + ": " + e.getMessage());
        }
    }

    public List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();

        lista.addAll(leerArchivo(ARCHIVO_BUSETA,   "buseta"));
        lista.addAll(leerArchivo(ARCHIVO_BUS,      "bus"));
        lista.addAll(leerArchivo(ARCHIVO_MICROBUS, "microbus"));

        System.out.println("[VehiculoDAO] Vehículos cargados al inicio: "
                + lista.size());
        return lista;
    }

    private List<Vehiculo> leerArchivo(String nombreArchivo, String tipo) {
        List<Vehiculo> lista  = new ArrayList<>();
        File           archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("[VehiculoDAO] Archivo no encontrado, se omite: "
                    + nombreArchivo);
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

                if (p.length != 3) {
                    System.out.println("[VehiculoDAO] Línea " + nLinea
                            + " malformada en " + nombreArchivo
                            + ": \"" + linea + "\" — omitida.");
                    continue;
                }

                String  placa  = p[0].trim();
                String  ruta   = p[1].trim();
                boolean estado = Boolean.parseBoolean(p[2].trim());

                Vehiculo v = instanciar(tipo, placa, ruta, estado);
                if (v != null) lista.add(v);
            }

            System.out.println("[VehiculoDAO] " + lista.size()
                    + " vehículo(s) leído(s) desde " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("[VehiculoDAO] Error al leer "
                    + nombreArchivo + ": " + e.getMessage());
        }

        return lista;
    }

    private Vehiculo instanciar(String tipo, String placa,
                                String ruta, boolean estado) {
        switch (tipo.toLowerCase()) {
            case "buseta":   return new Buseta(placa, ruta, estado);
            case "bus":      return new Bus(placa, ruta, estado);
            case "microbus": return new MicroBus(placa, ruta, estado);
            default:
                System.out.println("[VehiculoDAO] Tipo desconocido: " + tipo);
                return null;
        }
    }

    private String obtenerArchivo(Vehiculo vehiculo) {
        if (vehiculo instanceof Buseta)   return ARCHIVO_BUSETA;
        if (vehiculo instanceof Bus)      return ARCHIVO_BUS;
        if (vehiculo instanceof MicroBus) return ARCHIVO_MICROBUS;
        return null;
    }
}