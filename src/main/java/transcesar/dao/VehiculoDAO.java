package transcesar.dao;

import transcesar.model.Bus;
import transcesar.model.Buseta;
import transcesar.model.MicroBus;
import transcesar.model.Ruta;
import transcesar.model.Vehiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private static final String ARCHIVO_BUSETA   = "buseta.txt";
    private static final String ARCHIVO_BUS      = "bus.txt";
    private static final String ARCHIVO_MICROBUS = "microbus.txt";
    private static final String SEP              = ";";

    private RutaDao rutaDao = new RutaDao();

    public Vehiculo buscarPorPlaca(String placa) throws IOException {
        List<Ruta> rutas = rutaDao.cargarRutas();
        List<Vehiculo> todos = cargarVehiculos(rutas);

        return todos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);
    }

    public List<Vehiculo> listarTodos() throws IOException {
        List<Ruta> rutas = rutaDao.cargarRutas();
        return cargarVehiculos(rutas);
    }

    public void guardarVehiculo(Vehiculo vehiculo) {
        String archivo = obtenerArchivo(vehiculo);

        if (archivo == null) {
            System.out.println("[VehiculoDAO] Tipo no reconocido: "
                    + vehiculo.getClass().getSimpleName());
            return;
        }

        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            System.out.println("[VehiculoDAO] Error: Placa no puede estar vacía");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {

            String codigoRuta = vehiculo.getRuta() != null
                    ? vehiculo.getRuta().getCodigo() : "SIN_RUTA";

            // Guardamos solo 3 campos porque el constructor espera 3
            String linea = vehiculo.getPlaca()
                    + SEP + codigoRuta
                    + SEP + vehiculo.getEstado();
            // SIN capacidad - el constructor no la recibe

            bw.write(linea);
            bw.newLine();
            System.out.println("[VehiculoDAO] Guardado en " + archivo + " → " + linea);

        } catch (IOException e) {
            System.out.println("[VehiculoDAO] Error al escribir en "
                    + archivo + ": " + e.getMessage());
        }
    }

    public List<Vehiculo> cargarVehiculos(List<Ruta> rutas) {
        List<Vehiculo> lista = new ArrayList<>();

        lista.addAll(leerArchivo(ARCHIVO_BUSETA,   "buseta",   rutas));
        lista.addAll(leerArchivo(ARCHIVO_BUS,      "bus",      rutas));
        lista.addAll(leerArchivo(ARCHIVO_MICROBUS, "microbus", rutas));

        System.out.println("[VehiculoDAO] Vehículos cargados: " + lista.size());
        return lista;
    }

    private List<Vehiculo> leerArchivo(String nombreArchivo,
                                       String tipo, List<Ruta> rutas) {
        List<Vehiculo> lista   = new ArrayList<>();
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

                if (p.length < 3) {
                    System.out.println("[VehiculoDAO] Línea " + nLinea
                            + " malformada en " + nombreArchivo
                            + ": \"" + linea + "\" — omitida.");
                    continue;
                }

                String  placa      = p[0].trim();
                String  codigoRuta = p[1].trim();
                boolean estado     = Boolean.parseBoolean(p[2].trim());

                Ruta ruta = buscarRuta(codigoRuta, rutas);

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

    private Ruta buscarRuta(String codigo, List<Ruta> rutas) {
        if (codigo == null || codigo.isEmpty() || codigo.equals("SIN_RUTA")) {
            return null;
        }
        for (Ruta r : rutas) {
            if (r != null && r.getCodigo() != null &&
                    r.getCodigo().equalsIgnoreCase(codigo)) {
                return r;
            }
        }
        System.out.println("[VehiculoDAO] Ruta no encontrada: " + codigo);
        return null;
    }

    private Vehiculo instanciar(String tipo, String placa,
                                Ruta ruta, boolean estado) {
        try {
            switch (tipo.toLowerCase()) {
                case "buseta":
                    return new Buseta(placa, ruta, estado);  // 3 argumentos
                case "bus":
                    return new Bus(placa, ruta, estado);     // 3 argumentos
                case "microbus":
                    return new MicroBus(placa, ruta, estado); // 3 argumentos
                default:
                    System.out.println("[VehiculoDAO] Tipo desconocido: " + tipo);
                    return null;
            }
        } catch (Exception e) {
            System.out.println("[VehiculoDAO] Error al instanciar " + tipo
                    + " con placa " + placa + ": " + e.getMessage());
            return null;
        }
    }

    private String obtenerArchivo(Vehiculo vehiculo) {
        if (vehiculo instanceof Buseta)   return ARCHIVO_BUSETA;
        if (vehiculo instanceof Bus)      return ARCHIVO_BUS;
        if (vehiculo instanceof MicroBus) return ARCHIVO_MICROBUS;
        return null;
    }

    public boolean eliminarVehiculo(String placa) throws IOException {
        List<Ruta> rutas = rutaDao.cargarRutas();
        List<Vehiculo> todos = cargarVehiculos(rutas);

        boolean eliminado = todos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));

        if (eliminado) {
            sobrescribirArchivos(todos);
        }

        return eliminado;
    }

    private void sobrescribirArchivos(List<Vehiculo> vehiculos) {
        limpiarArchivo(ARCHIVO_BUSETA);
        limpiarArchivo(ARCHIVO_BUS);
        limpiarArchivo(ARCHIVO_MICROBUS);

        for (Vehiculo v : vehiculos) {
            guardarVehiculo(v);
        }
    }

    private void limpiarArchivo(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

        } catch (IOException e) {
            System.out.println("[VehiculoDAO] Error al limpiar " + archivo);
        }
    }
}