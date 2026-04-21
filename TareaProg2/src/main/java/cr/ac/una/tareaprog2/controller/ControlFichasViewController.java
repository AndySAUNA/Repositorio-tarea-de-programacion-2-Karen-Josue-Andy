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
import java.util.List;
import java.util.ArrayList;
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
            colCedula.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
                colTramite.setCellValueFactory(new PropertyValueFactory<>("idtramite"));
                    colPreferencial.setCellValueFactory(new PropertyValueFactory<>("preferencial"));
                    cargarDatos();
                 
    }  
          
          private void cargarDatos(){
              List<Ficha> datos= JsonUtil.cargarLista(ARCHIVO_FICHAS, Ficha.class);
              if(datos!=null){
                  lista.setAll(datos);
              }tablaFichas.setItems(lista);
          }
          
      @FXML private void siguiente(){
          if(lista.isEmpty())return;
          Ficha f= lista.remove(0);
          tablaFichas.refresh();
          atenderFicha(f);
      }
        @FXML private void preferencial(){
            for (Ficha f :new ArrayList<>(lista)){
                if(f.isPreferencial()){
                    lista.remove(f);
                    tablaFichas.refresh();
                    atenderFicha(f);
                    return;
                }
            }
        }
          @FXML private void repetir(){
        Ficha f = tablaFichas.getSelectionModel().getSelectedItem();
        if (f != null) {
        atenderFicha(f);
        }
    }
          
        @FXML private void llamarSeleccionada(){
         repetir();   
        }
                
      private void atenderFicha(Ficha f){
        mostrar(f);
        
    f.marcarComoLlamado(1);
     PantallaController pantalla = (PantallaController) FlowController.getInstance().getController("PantallaView");
    if (pantalla != null) {
        pantalla.registrarLlamado(f.getNumero(), "Caja 1");
    }
    guardar();      
      }
      private void mostrar(Ficha f){
          lblFicha.setText("Ficha: "+ f.getNumero());
           lblCedula.setText("Cedula: "+ f.getCedulaCliente());
           lblTramite.setText("Trámite: " + f.getIdTramite());
        lblPreferencial.setText("Preferencial: " + f.isPreferencial());
      }
      private void guardar(){
      JsonUtil.guardarLista(ARCHIVO_FICHAS, new ArrayList<>(lista));
      }
    @Override
    public void initialize() {
        // TODO
    }    

}