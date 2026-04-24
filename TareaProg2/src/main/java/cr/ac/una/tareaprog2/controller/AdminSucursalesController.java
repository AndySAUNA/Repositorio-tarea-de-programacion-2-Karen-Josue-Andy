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
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.Estacion;
import model.Sucursal;
import util.FlowController;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminSucursalesController extends Controller implements Initializable {

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
    
    private static final String ArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTablillaSucursales();
        cargarSucursalesEnTablilla();
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        //cargarSucursales();
        cargarSucursalesEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private Sucursal obtenerSeleccionada(){
        var seleccionadas = tableViewSucursal.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //pone las columnas de la tablilla de sucursales
    private void setupTablillaSucursales(){
        if(!tableViewSucursal.getTableColumns().isEmpty()){
            return;
        }
        MFXTableColumn<Sucursal> colId = 
                new MFXTableColumn<>("Id", true, Comparator.comparingInt(Sucursal::getId));
        MFXTableColumn<Sucursal> colNombre = 
                new MFXTableColumn<>("Nombre", true, Comparator.comparing(Sucursal::getNombre));
        MFXTableColumn<Sucursal> colDireccion = 
                new MFXTableColumn<>("Direccion", true, Comparator.comparing(Sucursal::getDireccion));
        
        colId.setRowCellFactory(col -> new MFXTableRowCell<>(s -> String.valueOf(s.getId())));
        colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Sucursal::getNombre));
        colDireccion.setRowCellFactory(col -> new MFXTableRowCell<>(Sucursal::getDireccion));
        
        tableViewSucursal.getTableColumns().setAll(colId,colNombre,colDireccion);
        tableViewSucursal.setItems(listaSucursales);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void cargarSucursalesEnTablilla(){
        List<Sucursal> lista = JsonUtil.cargarLista(ArchivoSucursales, Sucursal.class);
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaSucursales.clear();
        listaSucursales.addAll(lista);
        //tableViewSucursal.setItems(listaSucursales);
        Platform.runLater(() -> {
            tableViewSucursal.setItems(null);
            tableViewSucursal.setItems(listaSucursales);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void cargarSucursales() {
        List<Sucursal> sucursales = JsonUtil.cargarLista(ArchivoSucursales, Sucursal.class);
        listaSucursales.clear();
        listaSucursales.addAll(sucursales);
        tableViewSucursal.setItems(listaSucursales);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionTxfBuscar(ActionEvent event) {
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("AgregarSucursalView", this.getStage(), false);
        cargarSucursalesEnTablilla();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionEliminar(ActionEvent event) {
        Sucursal seleccionada = obtenerSeleccionada();
        if (seleccionada == null){
            new Mensaje().showModal(Alert.AlertType.WARNING, "Error Eliminar"
                , getStage(), "no hay seleccionado");
            return;
        }
        
        if (new Mensaje().showConfirmation("Atencion!", getStage(), 
                "Está seguro de querer eliminar la sucursal: " + seleccionada.getNombre()) == true){
            listaSucursales.remove(seleccionada);
            guardarSucursales();
            cargarSucursales();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Éxito"
                , getStage(), "Sucursal eliminada");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void guardarSucursales(){
        JsonUtil.guardarLista(ArchivoSucursales,new ArrayList<>(listaSucursales));
    }
    
}
