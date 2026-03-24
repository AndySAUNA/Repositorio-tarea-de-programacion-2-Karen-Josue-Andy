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

/**
 * FXML Controller class
 *
 * @author andys
 */
public class MainController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnAdministrador(ActionEvent event) {
    }

    @FXML
    private void onActionBtnFuncionario(ActionEvent event) {
    }

    @FXML
    private void OnActionBtnKiosk(ActionEvent event) {
    }

    @FXML
    private void onActionBtnPantalla(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }
    
}
