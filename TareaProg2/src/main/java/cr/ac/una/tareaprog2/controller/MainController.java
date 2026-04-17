/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.FlowController;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class MainController extends Controller implements Initializable {

    @FXML
    private Button btnAdministrador;
    @FXML
    private Button btnFuncionario;
    @FXML
    private Button btnKiosk;
    @FXML
    private Button btnPantalla;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnRegistroClientes;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("MainController inicializado");
      
    }    
    
    @Override
    public void initialize() {
        
    }

  @FXML
private void onActionBtnAdministrador(ActionEvent event) {
    System.out.println("DEBUG: Click en Administrador");
    FlowController.getInstance().goMain("AdminView");
}
    @FXML
    private void onActionBtnFuncionario(ActionEvent event) {
        FlowController.getInstance().goMain("FuncionarioView");
    }

    @FXML
    private void OnActionBtnKiosk(ActionEvent event) {
        FlowController.getInstance().goMain("KioskoView");
    }

    @FXML
    private void onActionBtnPantalla(ActionEvent event) {
        FlowController.getInstance().goMain("PantallaView");
    }
    
        @FXML
    private void onActionBtnRegistroClientes(ActionEvent event) {
        FlowController.getInstance().goMain("RegistroClientesView");
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }


}
