/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class EditarClienteController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXTextField txfApellidos;
    @FXML
    private MFXButton btnTomaNuevaFoto;
    @FXML
    private MFXButton btnTomaNuevaFoto1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
    
    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionAplicarCambios(ActionEvent event) {
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void txfFechaNacimiento(ActionEvent event) {
    }

    @FXML
    private void onActionTomarFotoNueva(ActionEvent event) {
    }

    
    
}
