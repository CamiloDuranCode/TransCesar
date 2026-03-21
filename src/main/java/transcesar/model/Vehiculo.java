package transcesar.model;

public abstract class Vehiculo implements Imprimible {
    protected String placa;
    protected Ruta ruta;
    protected int capacidadMaxima;
    protected int contadorPasajeros;
    protected boolean estado;

    public Vehiculo(String placa, Ruta ruta, int capacidadMaxima, boolean estado) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.contadorPasajeros = 0;
        this.estado = estado;
    }

    public abstract double tarifabase();
    public abstract String getTipoVehiculo();

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta   = ruta;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getContadorPasajeros() {
        return contadorPasajeros;
    }

    public void setContadorPasajeros(int contadorPasajeros) {
        this.contadorPasajeros = contadorPasajeros;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public abstract void imprimirDetalle();

    public void incrementarPasajeros() {
        this.contadorPasajeros++;
    }

    public void decrementarPasajeros() {
        if (contadorPasajeros > 0) this.contadorPasajeros--;
    }

    public boolean tieneCupos() {
        return contadorPasajeros < capacidadMaxima;
    }
}



