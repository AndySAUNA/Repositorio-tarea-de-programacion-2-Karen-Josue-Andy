/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Estacion;
import model.Sucursal;
import util.JsonUtil;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //estas dos lineas llenan el MFXComboBox con objetos tipo Sucursal
        listaSucursales.setAll(cargarListaSucursales());
        cbSucursal.setItems(listaSucursales);
        cargarEstaciones();
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
       
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
        if (cbSucursal.getValue() == null){
            return;
        }
        List<Estacion> lista = cbSucursal.getValue().getEstaciones();
        
        if (lista == null){
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

    @FXML
    private void onActionBtnSeleccionar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
    }
}
