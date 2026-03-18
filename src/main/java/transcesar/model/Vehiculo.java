package transcesar.model;

public abstract class Vehiculo {
    protected String placa;
    protected String ruta;
    protected int capacidadMaxima;
    protected int contadorPasajeros;
    protected boolean estado;


    public Vehiculo(String placa, String ruta, int capacidadMaxima,boolean estado) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.contadorPasajeros = 0;
        this.estado = true;
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

    public boolean getEstado() {
        return estado;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
