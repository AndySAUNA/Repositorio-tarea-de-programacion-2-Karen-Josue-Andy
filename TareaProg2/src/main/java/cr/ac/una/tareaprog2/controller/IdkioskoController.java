/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.FlowController;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class IdkioskoController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfNombreUsuario;
    @FXML
    private MFXPasswordField pwfContraseña;

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
    private void onActionBtnIngresar(ActionEvent event) {
        FlowController.getInstance().goMain("NewKioskoView");
    }

    @FXML
    private void onActionBtnIngresarComoInvitado(ActionEvent event) {
        //FlowController.getInstance().goMain("CrearFichaView");
        //FlowController.getInstance().goViewInWindowModal("CrearFichaView", this.getStage(), false);
    }

    @FXML
    private void onActionBtnCrearCuenta(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("AgregarClienteView", this.getStage(), false);
    }

  
}
