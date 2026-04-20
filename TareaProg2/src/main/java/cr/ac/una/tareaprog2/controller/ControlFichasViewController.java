/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ficha;
import util.JsonUtil;
import util.FlowController;
/**
 * FXML Controller class
 *
 * @author JD Madrigal Arias
 */
public class ControlFichasViewController extends Controller implements Initializable {
     @FXML private TableView<Ficha> tablaFichas;
      @FXML private TableColumn<Ficha, Integer> colNumero;
       @FXML private TableColumn<Ficha, String> colCedula;
        @FXML private TableColumn<Ficha, String> colTramite;
         @FXML private TableColumn<Ficha, Boolean> colPreferencial;
          @FXML private Label lblFicha;
          @FXML private Label lblCedula;
          @FXML private Label lblCliente;
          @FXML private Label lblTramite;
          @FXML private Label lblPreferencial;
          
           private final String ARCHIVO_FICHAS= System.getProperty("user.home")+"/datosProyecto/fichas.json";
           //se puede crear una clase intermediaria para no estar repetiendo la misma linea de codigo de arriba, solo se llamaria la clase
          private ObservableList<Ficha> lista= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
           @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
                colTramite.setCellValueFactory(new PropertyValueFactory<>("tramite"));
                    colPreferencial.setCellValueFactory(new PropertyValueFactory<>("preferencial"));
                    cargarDatos();
    }  
          
          private void cargarDatos(){
              lista.setAll(JsonUtil.cargarLista(ARCHIVO_FICHAS, Ficha.class));
              tablaFichas.setItems(lista);
          }
    @Override
    public void initialize() {
        // TODO
    }    

}
