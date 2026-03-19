package transcesar.model;

public class Ruta implements Imprimible {

    private String codigo;
    private String ciudadOrigen;
    private String ciudadDestino;
    private double distanciaKm;
    private int tiempoEstimadoMinutos;

    public Ruta(String codigo, String ciudadOrigen, String ciudadDestino,
                double distanciaKm, int tiempoEstimadoMinutos) {
        this.codigo = codigo;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("===== RUTA =====");
        System.out.println("Código   : " + codigo);
        System.out.println("Origen   : " + ciudadOrigen);
        System.out.println("Destino  : " + ciudadDestino);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Tiempo   : " + tiempoEstimadoMinutos + " min");
        System.out.println("================");
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getCiudadOrigen() { return ciudadOrigen; }
    public String getCiudadDestino() { return ciudadDestino; }
    public double getDistanciaKm() { return distanciaKm; }
    public int getTiempoEstimadoMinutos() { return tiempoEstimadoMinutos; }
}
