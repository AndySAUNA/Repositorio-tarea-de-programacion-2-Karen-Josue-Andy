/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.MFXTextField;
//import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author andys
 */
public class KioskoViewController implements Initializable {
    
    @FXML private MFXTextField txtCedula;
        @FXML private Label lblFicha;
        @FXML private MFXComboBox<String> cbTramite;
        private int contador = 1;
         
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cbTramite.getItems().addAll("Deposito", "Retiro", "Consultas");
        // TODO
    }    
    @FXML private void generarFichas(){
        String cedula= txtCedula.getText();
        String tipo = cbTramite.getValue();
        if(cedula.isEmpty()||tipo==null){
            lblFicha.setText("Complete los datos de acceso");
            return;
        }
         String ficha= tipo.substring(0,1) + String.format("%03d"+ contador);
        lblFicha.setText("Ficha: " + ficha);
        contador++;
    }
}
