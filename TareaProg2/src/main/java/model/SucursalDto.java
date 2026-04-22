/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author andys
 */
public class SucursalDto {

    private StringProperty nombre;
    private StringProperty estaciones;
    private StringProperty id;

    public StringProperty getNombre() {
        return nombre;
    }

    public void setNombre(StringProperty nombre) {
        this.nombre = nombre;
    }

    public StringProperty getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(StringProperty Estaciones) {
        this.estaciones = Estaciones;
    }
    
        public StringProperty getid() {
        return id;
    }

    public void setid(StringProperty id) {
        this.estaciones = id;
    }
    

    public SucursalDto() {
        this.nombre = new SimpleStringProperty("");
        this.estaciones = new SimpleStringProperty("");
    }
    
 @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SucursalDto other = (SucursalDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EstablecimientoDto{" + "id=" + id + ", nombre=" + nombre + ", estaciones=" + estaciones + '}';
    }
}
