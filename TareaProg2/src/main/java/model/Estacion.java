
package model;

/**
 *
 * @author Paulina
 */

import java.util.ArrayList;
import java.util.List;

public class Estacion {
    private int id;
    private String nombre;
    private boolean preferencial;
    private List<Integer> tramitesAsignados; 
   
    public Estacion() {
        this.tramitesAsignados = new ArrayList<>();
    }

    
    public Estacion(int id, String nombre, boolean preferencial) {
        this.id = id;
        this.nombre = nombre;
        this.preferencial = preferencial;
        this.tramitesAsignados = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isPreferencial() { return preferencial; }
    public void setPreferencial(boolean preferencial) { this.preferencial = preferencial; }

    public List<Integer> getTramitesAsignados() { return tramitesAsignados; }
    public void setTramitesAsignados(List<Integer> tramitesAsignados) { this.tramitesAsignados = tramitesAsignados; }

    
    public void agregarTramite(int idTramite) {
        if (!tramitesAsignados.contains(idTramite)) {
            tramitesAsignados.add(idTramite);
        }
    }
    public void quitarTramite(int idTramite) {
        tramitesAsignados.remove(Integer.valueOf(idTramite));
    }

    public boolean atiendeTramite(int idTramite) {
        return tramitesAsignados.contains(idTramite);
    }

    @Override
    public String toString() {
        return nombre + (preferencial ? " (Preferencial)" : "");
    }
    
}
