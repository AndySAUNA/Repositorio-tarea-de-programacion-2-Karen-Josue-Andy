/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Estacion;
import model.Sucursal;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminEstacionesController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfBuscar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private MFXTableView<Sucursal> tableViewSucursal;
    private static final String ArchivoDatos = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    @FXML
    private MFXTableView<Estacion> tableViewEstacion;
    private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();
    @FXML
    private Button btnPreferencial;
    
    /**
     * Initializes the controller class.
     */
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTablillaSucursales();//pone columnas de sucursales
        setupTablillaEstaciones();//pone columnas de estaciones
        cargarSucursalesEnTablilla();
        seleccionarPrimeraSucursal();
        cargarEstacionesDeSucursalEnTablilla();
        //cargarEstacionesDeSucursal(listaSucursales.get(0));
        
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        cargarSucursalesEnTablilla(); //siempre tiene que actualizar las sucursales en tablilla
        cargarEstacionesDeSucursalEnTablilla();
        cargarSucursalesEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void setupTablillaSucursales(){//funciona
        if(!tableViewSucursal.getTableColumns().isEmpty()){
            return;
        }
        MFXTableColumn<Sucursal> colId = 
                new MFXTableColumn<>("Id", true, Comparator.comparingInt(Sucursal::getId));
        MFXTableColumn<Sucursal> colNombre = 
                new MFXTableColumn<>("Nombre", true, Comparator.comparing(Sucursal::getNombre));
        
    colId.setRowCellFactory(col -> new MFXTableRowCell<>(s -> String.valueOf(s.getId())));
    colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Sucursal::getNombre));
        
        tableViewSucursal.getTableColumns().setAll(colId,colNombre);
        tableViewSucursal.setItems(listaSucursales);
        cargarSucursalesEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //pone las columnas de la tablilla de sucursales
    private void setupTablillaEstaciones(){// funciona
        if(!tableViewEstacion.getTableColumns().isEmpty()){
            return;
        }
        MFXTableColumn<Estacion> colNumero = 
                new MFXTableColumn<>("Numero", true, Comparator.comparingInt(Estacion::getId));
        MFXTableColumn<Estacion> colNombre = 
                new MFXTableColumn<>("Nombre", true, Comparator.comparing(Estacion::getNombre));
        MFXTableColumn<Estacion> colPreferencial = 
                new MFXTableColumn<>("Preferencial", true, Comparator.comparing(Estacion::isPreferencial));
        
        colNumero.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> String.valueOf(Estacion.getId())));
        colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion::getNombre));
        colPreferencial.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> Estacion.isPreferencial()));
        
        tableViewEstacion.getTableColumns().setAll(colNumero,colNombre,colPreferencial);
        tableViewEstacion.setItems(listaEstaciones);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void cargarSucursalesEnTablilla(){// funciona perfectamente
        List<Sucursal> lista = JsonUtil.cargarLista(ArchivoDatos, Sucursal.class);
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaSucursales.clear();
        listaSucursales.addAll(lista);
        
        Platform.runLater(() -> {
            tableViewSucursal.setItems(null);
            tableViewSucursal.setItems(listaSucursales);
            
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void cargarEstacionesDeSucursalEnTablilla(){
        if(obtenerSucursalSeleccionada() == null){
            System.out.println("sucusal no tiene estaciones");
            return;
        }
        List<Estacion> lista = obtenerSucursalSeleccionada().getEstaciones();
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaEstaciones.clear();
        listaEstaciones.addAll(lista);
        
        Platform.runLater(() -> {
            tableViewEstacion.setItems(null);
            tableViewEstacion.setItems(listaEstaciones);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void cargarEstacionesDeSucursal(Sucursal sucursal){//nofunciona
        listaEstaciones.clear();
        
        if(sucursal !=null && sucursal.getEstaciones() != null){
            listaEstaciones.addAll(sucursal.getEstaciones());
        }
        tableViewEstacion.setItems(listaEstaciones);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void configurarSeleccionSucursal() {//no se para que es
        tableViewSucursal.setOnMouseClicked(event -> {
            Sucursal seleccionada = obtenerSucursalSeleccionada();
            cargarEstacionesDeSucursal(seleccionada);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    // funcion pra obtener el objeto que ha sido seleccionado de la tablilla tableViewSucursal
    private Sucursal obtenerSucursalSeleccionada(){//funciona
        var seleccionadas = tableViewSucursal.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //retorna el objeto estacion que esta seleccionado
    private Estacion obtenerEstacionSeleccionada(){
        var seleccionadas = tableViewEstacion.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void seleccionarPrimeraSucursal(){//funciona 
        if (!listaSucursales.isEmpty()){// si la lista de sucursales no esta vacia
            tableViewSucursal.getSelectionModel().clearSelection();//se limpia seleccion de la tablilla al iniciar
            tableViewSucursal.getSelectionModel().selectIndex(0);//se selecciona en la tablilla la primera sucursal de la lista por defecto
        }else{
            listaEstaciones.clear();
            tableViewEstacion.setItems(listaEstaciones);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionTxfBuscar(ActionEvent event) {
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void guardarDatos(){//funciona
        JsonUtil.guardarLista(ArchivoDatos, new ArrayList<>(listaSucursales));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //funcion para pobtener el id que sigue para establecer nombre de estacion y id
    private int getIdSiguienteEstacion(){ //consigue el id mas alto de la lista
        
        int maxId = 0;
        for (Estacion s : listaEstaciones) {
            if (s.getId() > maxId) {
                maxId = s.getId();
            }
        }
        return maxId + 1;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionEliminar(ActionEvent event) {
        if(obtenerSucursalSeleccionada()==null){
            new Mensaje().showModal(Alert.AlertType.ERROR,"Error", getStage(), "Sucursal no seleccionada");
        }
        if(obtenerEstacionSeleccionada()==null){
            new Mensaje().showModal(Alert.AlertType.ERROR,"Error", getStage(), "Sucursal no tiene esstaciones para eliminar");
        }
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private int getIultimaEstacion(){ //consigue el id mas alto de la lista
        
        int maxId = 0;
        for (Estacion s : listaEstaciones) {
            if (s.getId() > maxId) {
                maxId = s.getId();
            }
        }
        return maxId;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void onActionBtnRefrescar(ActionEvent event) {
        cargarEstacionesDeSucursalEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void ClickMF1(MouseEvent event) {
        System.out.println("ClickCickMF activado");
        cargarEstacionesDeSucursalEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void mover(MouseEvent event) {
        cargarEstacionesDeSucursalEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionPreferencial(ActionEvent event) {
        if(obtenerSucursalSeleccionada()==null||obtenerEstacionSeleccionada()==null){
            new Mensaje().showModal(Alert.AlertType.ERROR,"Error", getStage(), "estacion no seleccionada");
        }
        Sucursal sucursal = obtenerSucursalSeleccionada();
        Estacion estacion = obtenerEstacionSeleccionada();
        if(estacion.isPreferencial()){
            estacion.setPreferencial(false);
        }else{
        estacion.setPreferencial(true);
        }
        cargarEstacionesDeSucursalEnTablilla();
        guardarDatos();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
    }
}
