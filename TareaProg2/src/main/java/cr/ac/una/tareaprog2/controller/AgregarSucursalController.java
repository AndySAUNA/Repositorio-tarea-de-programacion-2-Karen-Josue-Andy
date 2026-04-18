/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
public class AgregarSucursalController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregar;
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private MFXTextField txfNombreSucursal;
    @FXML
    private MFXComboBox<?> comboBoxCantidadEstaciones;
    @FXML
    private MFXTextField TxfDireccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Agregar Sucursal");
        // TODO
    }    
        @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void onActionTxfNombreSucursal(ActionEvent event) {
    }

    @FXML
    private void onActionComboBoxCantidadEstaciones(ActionEvent event) {
    }

    @FXML
    private void onActionTxfDireccion(ActionEvent event) {
    }


    
}
