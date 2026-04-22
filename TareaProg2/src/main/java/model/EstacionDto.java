/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author andys
 */
public class EstacionDto {
    public StringProperty id; //tiene que ser publico
    private StringProperty sucursal;
    private StringProperty numeroEstacion;
    private BooleanProperty preferencial;
    //-----------------------------------------------------------------------------------------------------------------------------------
    public EstacionDto(){
        this.id = new SimpleStringProperty("");
        this.sucursal = new SimpleStringProperty("");
        this.numeroEstacion = new SimpleStringProperty("");
        this.preferencial = new SimpleBooleanProperty(false);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public Long getId() {
          if (this.id.get() != null && !this.id.get().isBlank()){
              return Long.valueOf(this.id.get());
          }else{
              return null;
          }
    }
    public String getSucursal() {
        return sucursal.get();
    }
    public Integer getnumeroEstacion() {
        if (this.id.get() != null && !this.numeroEstacion.get().isBlank()){
              return Integer.valueOf(this.numeroEstacion.get());
          }else{
              return null;
          }
    }
    public Boolean preferencial() {
        return preferencial.get();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public void setId(Long id) {
        this.id.set(id.toString());
    }
    public void setSucursal(String sucursal) {
        this.sucursal.set(sucursal);
    }
    public void setnumeroEstacion(Integer numeroEstacion) {
        this.numeroEstacion.set(numeroEstacion.toString());
    }
    public void setpreferencial(Boolean preferencial) {
        this.preferencial.set(preferencial);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public StringProperty getIdProperty() {
        return id;
    }
    public StringProperty getSucursalProperty() {
        return sucursal;
    }

    public StringProperty getNumeroEstacionProperty() {
        return numeroEstacion;
    }

    public BooleanProperty getPreferencialProperty() {
        return preferencial;
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
        return "EstablecimientoDto{" + "id=" + id + ", sucursal=" + sucursal + ", numero estacion=" + numeroEstacion + ", preferencial=" + preferencial + '}';
    }
}
