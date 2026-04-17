/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private int id;
    private String nombre;
    private String direccion;
    private List<Estacion> estaciones;

    public Sucursal() {
        this.estaciones = new ArrayList<>();
    }
    public Sucursal(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.estaciones = new ArrayList<>();
    }
public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Estacion> getEstaciones() { return estaciones; }
    public void setEstaciones(List<Estacion> estaciones) { this.estaciones = estaciones; }

    // Métodos útiles
    public void agregarEstacion(Estacion estacion) {
        this.estaciones.add(estacion);
    }

    public void quitarEstacion(int idEstacion) {
        estaciones.removeIf(e -> e.getId() == idEstacion);
    }
     public Estacion buscarEstacion(int idEstacion) {
        return estaciones.stream()
                .filter(e -> e.getId() == idEstacion)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
