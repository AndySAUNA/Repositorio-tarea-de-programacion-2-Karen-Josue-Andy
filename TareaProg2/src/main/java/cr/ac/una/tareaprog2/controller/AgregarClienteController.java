/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import model.Cliente;
import util.Formato;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AgregarClienteController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXTextField txfApellidos;
    @FXML
    private ImageView ivFotoPerfil;
    @FXML
    private MFXButton btnTomaNuevaFoto;
    @FXML
    private MFXButton btnTomaNuevaFoto1;
    private Cliente cliente;
    @FXML
    private MFXDatePicker dpFechaNacimiento;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField txFContraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Crear cuenta");
        txfCedula.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txfApellidos.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(60));
        //cargarValoresPorDefecto();
        bindCliente();
        // TODO
    }    
    @Override
    public void initialize() {
        
    }
    private void bindCliente(){
        
    }

    
    private void cargarValoresPorDefecto(){
        cliente.setNombre("");
        cliente.setApellidos("");
        cliente.setCedula("");
        cliente.setRutaFoto("");
        cliente.setFechaNacimiento(LocalDate.now());
    }
    @FXML
    private void onActionCancelar(ActionEvent event) {
        new Mensaje().showModal(Alert.AlertType.WARNING,
                        "Cancelar", getStage(), "Esta seguro de querer cancelar el tramite?");
        //cargarValoresPorDefecto();
        this.getStage().close();
    }

    @FXML
    private void onActionTomarFotoNueva(ActionEvent event) {
        
    }

    @FXML
    private void onActionCrearCuenta(ActionEvent event) {
    }

}
