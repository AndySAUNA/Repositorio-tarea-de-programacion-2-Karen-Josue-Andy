
package model;

/**
 *
 * @author Paulina
 */
public class Empresa {
     private String nombre;
    private String rutaLogo;
    private String pinAdministrador;
    
    public Empresa() {
        this.nombre = "Sistema de Fichas";
        this.pinAdministrador = "1234";
    }
    public Empresa(String nombre, String rutaLogo, String pinAdministrador) {
        this.nombre = nombre;
        this.rutaLogo = rutaLogo;
        this.pinAdministrador = pinAdministrador;
    }
     public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRutaLogo() { return rutaLogo; }
    public void setRutaLogo(String rutaLogo) { this.rutaLogo = rutaLogo; }

    public String getPinAdministrador() { return pinAdministrador; }
    public void setPinAdministrador(String pinAdministrador) { this.pinAdministrador = pinAdministrador; }
}
