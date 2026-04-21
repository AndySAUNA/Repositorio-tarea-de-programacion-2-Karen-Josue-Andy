/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Modality;
import util.FlowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Modality;
/**
 * FXML Controller class
 *
 * @author andys
 */
public class FuncionarioController extends Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private void abrirControlFichas(){
    FlowController.getInstance().goView("ControlFichasView");
    }
      @FXML private void abrirClientes(){
       FlowController.getInstance().goView("RegistroClientesView");
      }
        @FXML private void salir(){
             FlowController.getInstance().salir();
        }
    
    @Override
    public void initialize() {
        
    }
    
}
