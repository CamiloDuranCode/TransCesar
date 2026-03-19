package transcesar.model;

public abstract class Vehiculo {
    protected String placa;
    protected Ruta ruta;
    protected int capacidadMaxima;
    protected int contadorPasajeros;
    protected boolean estado;

    public Vehiculo(String placa, Ruta ruta, int capacidadMaxima, boolean estado) {
        this.placa             = placa;
        this.ruta              = ruta;
        this.capacidadMaxima   = capacidadMaxima;
        this.contadorPasajeros = 0;
        this.estado            = estado;
    }

    public abstract double tarifabase();
    public abstract String getTipoVehiculo();

    public String getPlaca()             { return placa;             }
    public Ruta getRuta()                { return ruta;              }
    public int getCapacidadMaxima()      { return capacidadMaxima;   }
    public int getContadorPasajeros()    { return contadorPasajeros; }
    public boolean getEstado()           { return estado;            }

    public void setRuta(Ruta ruta)       { this.ruta   = ruta;   }
    public void setEstado(boolean estado){ this.estado = estado;  }
}



