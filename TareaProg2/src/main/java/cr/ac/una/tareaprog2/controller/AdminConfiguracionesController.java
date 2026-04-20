/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import util.FlowController;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminConfiguracionesController extends Controller  implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnCambiarLogo;
    @FXML
    private MFXButton btnCambiarColores;

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
    private void onActionBtnCambiarLogo(ActionEvent event) {
        FlowController.getInstance().goMain("CambiarLogoView");
    }

    @FXML
    private void onActionCambiarColores(ActionEvent event) {
    }
    
}
