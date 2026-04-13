package model;

public class Llamado {
    private String ficha;      
    private String estacion;   
    private String hora;      

    
    public Llamado() {}

    public Llamado(String ficha, String estacion, String hora) {
        this.ficha = ficha;
        this.estacion = estacion;
        this.hora = hora;
    }
    public String getFicha() { return ficha; }
    public void setFicha(String ficha) { this.ficha = ficha; }

    public String getEstacion() { return estacion; }
    public void setEstacion(String estacion) { this.estacion = estacion; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    @Override
    public String toString() {
        return ficha + " - " + estacion + " - " + hora;
    }
}