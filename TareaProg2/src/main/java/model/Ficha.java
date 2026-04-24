
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ficha {
      private int numero;
    private String cedulaCliente; 
    private int idTramite;
    private int idSucursal;
    private Integer idEstacion; 
    private LocalDateTime fechaHoraGeneracion;
    private LocalDateTime fechaHoraLlamado; 
    private String estado; 
    private boolean preferencial;

    public Ficha() {}
    public Ficha(int numero, String cedulaCliente, int idTramite, int idSucursal, boolean preferencial) {
        this.numero = numero;
        this.cedulaCliente = cedulaCliente;
        this.idTramite = idTramite;
        this.idSucursal = idSucursal;
        this.preferencial = preferencial;
        this.estado = "ESPERA";
        this.fechaHoraGeneracion = LocalDateTime.now();
    }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(String cedulaCliente) { this.cedulaCliente = cedulaCliente; }

    public int getIdTramite() { return idTramite; }
    public void setIdTramite(int idTramite) { this.idTramite = idTramite; }

    public int getIdSucursal() { return idSucursal; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }

    public Integer getIdEstacion() { return idEstacion; }
    public void setIdEstacion(Integer idEstacion) { this.idEstacion = idEstacion; }

    public LocalDateTime getFechaHoraGeneracion() { return fechaHoraGeneracion; }
    public void setFechaHoraGeneracion(LocalDateTime fechaHoraGeneracion) { this.fechaHoraGeneracion = fechaHoraGeneracion; }
    
    public LocalDateTime getFechaHoraLlamado() { return fechaHoraLlamado; }
    public void setFechaHoraLlamado(LocalDateTime fechaHoraLlamado) { this.fechaHoraLlamado = fechaHoraLlamado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isPreferencial() { return preferencial; }
    public void setPreferencial(boolean preferencial) { this.preferencial = preferencial; }

   
    public void marcarComoLlamado(int idEstacion) {
        this.idEstacion = idEstacion;
        this.fechaHoraLlamado = LocalDateTime.now();
        this.estado = "LLAMADO";
    }
public void marcarComoAtendido() {
        this.estado = "ATENDIDO";
    }

    public String getFechaHoraGeneracionFormateada() {
        if (fechaHoraGeneracion == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHoraGeneracion.format(formatter);
    }

    public String getFechaHoraLlamadoFormateada() {
        if (fechaHoraLlamado == null) return "No llamado";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHoraLlamado.format(formatter);
    }

    @Override
    public String toString() {
        return "Ficha #" + numero + " - " + estado + (preferencial ? " (Preferencial)" : "");
    }
}
