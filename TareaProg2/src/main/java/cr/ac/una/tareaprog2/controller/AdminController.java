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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.FlowController;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminController extends Controller implements Initializable {

    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXButton btnConfiguraciones;
    @FXML
    private MFXButton btnTramites;
    @FXML
    private MFXButton btnClientes;
    @FXML
    private MFXButton btnMetricas;
    @FXML
    private MFXButton btnSucursales;
    @FXML
    private BorderPane root;

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
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnConfiguraciones(ActionEvent event) {
        FlowController.getInstance().goView("AdminConfiguracionesView");
    }

    @FXML
    private void onActionBtnTramites(ActionEvent event) {
        FlowController.getInstance().goView("AdminTramitesView");
    }

    @FXML
    private void onActionBtnClientes(ActionEvent event) {
        FlowController.getInstance().goView("AdminClientesView");
    }

    @FXML
    private void onActionBtnMetricas(ActionEvent event) {
        FlowController.getInstance().goView("AdminMetricasView");
    }

    @FXML
    private void onActionBtnSucursales(ActionEvent event) {
        FlowController.getInstance().goView("AdminSucursalesView");
    }
    
}
