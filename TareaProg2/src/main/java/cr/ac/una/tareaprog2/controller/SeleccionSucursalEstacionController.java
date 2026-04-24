/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.Estacion;
import model.Sucursal;
import util.AppContext;
import util.FlowController;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class SeleccionSucursalEstacionController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXComboBox<Sucursal> cbSucursal;
    @FXML
    private MFXComboBox<Estacion> cbEstacion;
    
    //para manejar archivo y lista de Sucursales
    private static final String UrlArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de Estaciones
    private String UrlArchivoEstaciones = "data/Datos.json";
    private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();
    
    private List<Node> requeridos = new ArrayList();// para los requeridos del formulario

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Seleccionar Estacion");//establece el nombre de la ventana
        //estas dos lineas llenan el MFXComboBox con objetos tipo Sucursal
        indicarRequeridos();
        listaSucursales.setAll(cargarListaSucursales());
        cbSucursal.setItems(listaSucursales);
        cargarEstaciones();
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
       
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //establece los campos requeridos del formulario    (funciona perfectamente)
    private void indicarRequeridos(){
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(cbSucursal,cbEstacion));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    // gracias por el codigo profe (｡•̀ᴗ-)✧              (funciona perfectamente)
    private String validarRequeridos(){
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //busca en el archivo json, extrae la lista de entidades y los retorna          (funciona perfectamente)
    private List<Sucursal> cargarListaSucursales(){
        List<Sucursal> lista = JsonUtil.cargarLista(UrlArchivoSucursales, Sucursal.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //carga la lista de estaciones al MFXComboBox de estaciones cuando hay una sucursal seleccionada (hay que probar)
    private void cargarEstaciones() {
        if (cbSucursal.getValue() == null){ //si valor seleccionado sucursal no es nulo
            return;
        }
        List<Estacion> lista = cbSucursal.getValue().getEstaciones();//agarra la lista de estaciones pertenecientes al sucursal seleccionada
        
        if (lista == null){ // por si la lista es nula
            lista = new ArrayList<>();
        }
        listaEstaciones.clear();
        listaEstaciones.addAll(lista);
        cbEstacion.setItems(listaEstaciones);
        Platform.runLater(() -> {
            cbEstacion.setItems(null);
            cbEstacion.setItems(listaEstaciones);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia formulario,  (funciona perfectamente)
    private void cargarValoresPorDefecto(){
        cbSucursal.clear();
        cbEstacion.clear();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionBtnSeleccionar(ActionEvent event) {
        try{
            //revisa que los espacios requeridos esten llenos
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Seleccionar Estacion"
                , getStage(), invalidos);
                return;
            }
            AppContext.getInstance().set("sucursalSeleccionada", cbSucursal.getValue().getId());
            AppContext.getInstance().set("EstacionSeleccionada", cbEstacion.getValue().getId());
            FlowController.getInstance().goMain("FuncionarioView");
            this.getStage().close();
        }catch(Exception ex){
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error abriendo estacion", getStage(),
                    "No se pudo abrir la Estacion");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        cargarValoresPorDefecto();
        this.getStage().close();
    }

    @FXML
    private void onActionCbSucursal(ActionEvent event) {
        cargarEstaciones();
    }
}
