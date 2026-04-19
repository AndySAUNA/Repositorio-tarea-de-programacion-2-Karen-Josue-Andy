
package model;

/**
 *
 * @author Paulina
 */
public class Tramite {
    private int id;
    private String nombre;
    private int duracionEstimada; 
    private boolean activo;

    public Tramite() {}
    
    public Tramite(int id, String nombre, int duracionEstimada, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.duracionEstimada = duracionEstimada;
        this.activo = activo;
    }
     public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDuracionEstimada() { return duracionEstimada; }
    public void setDuracionEstimada(int duracionEstimada) { this.duracionEstimada = duracionEstimada; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + (activo ? "" : " (Inactivo)");
    }
}
