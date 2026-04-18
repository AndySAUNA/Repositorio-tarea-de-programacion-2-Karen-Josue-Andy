/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import util.FlowController;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminSucursalesController  extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfBuscar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private MFXTableView<?> tableViewSucursal;

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
    private void onActionTxfBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("AgregarSucursalView");
    }

    @FXML
    private void onActionEliminar(ActionEvent event) {
    }
    
}
