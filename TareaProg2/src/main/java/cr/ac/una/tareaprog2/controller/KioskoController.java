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
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Ficha;
import java.io.File;
import util.JsonUtil;
/**
 * FXML Controller class
 *
 * @author andys
 */


public class KioskoController extends Controller implements Initializable {
    
    @FXML private MFXTextField txtCedula;
        @FXML private Label lblFicha;
        @FXML private MFXComboBox<String> cbTramite;
      private final String ARCHIVO_FICHAS= System.getProperty("user.home")+"/datosProyecto/fichas.json";
    @FXML
    private AnchorPane root;
         
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cbTramite.getItems().addAll("Deposito", "Retiro", "Consultas");
       File carpeta= new File(System.getProperty("user.home")+ "/datosProyecto/");
       if(!carpeta.exists()){
           carpeta.mkdirs();
       }
        // TODO
    }      
    @Override
    public void initialize(){//Vacio
    }
    
    private void generarFichas(){
        String cedula= txtCedula.getText();
        String tipo = cbTramite.getValue();
        if(cedula==null||cedula.isEmpty()||tipo==null){
            lblFicha.setText("complete los datos de acceso");
            return;
        }
        List<Ficha> fichas= JsonUtil.cargarLista(ARCHIVO_FICHAS, Ficha.class);
        if(fichas==null){
            fichas=new java.util.ArrayList<>();
        }
        int contador = fichas.size()+1;
        int idTramite=1;
        int idSucursal=1;
           Ficha nueva= new Ficha(contador,cedula,idTramite,idSucursal, false);
           fichas.add(nueva);
           JsonUtil.guardarLista(ARCHIVO_FICHAS, fichas);
        lblFicha.setText("Ficha: " + String.format("%03d" , nueva.getNumero() ));      
    }

    @FXML
    private void generarFicha(ActionEvent event) {
        generarFichas();
    } 
}
