
package model;

import java.time.LocalDate;

public class Cliente {
    private String cedula;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String rutaFoto;

    public Cliente() {}

    public Cliente(String cedula, String nombre, String apellidos) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}