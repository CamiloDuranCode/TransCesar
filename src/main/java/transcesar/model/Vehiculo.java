package transcesar.model;

public abstract class Vehiculo {
    protected String placa;
    protected String ruta;
    protected int capacidadMaxima;
    protected int contadorPasajeros;


    public Vehiculo(String placa, String ruta, int capacidadMaxima) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.contadorPasajeros = 0;
    }


    public abstract double tarifabase();

    public abstract String getTipoVehiculo();



    public String getPlaca() {
        return placa;
    }

    public String getRuta() {
        return ruta;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getContadorPasajeros() {
        return contadorPasajeros;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
