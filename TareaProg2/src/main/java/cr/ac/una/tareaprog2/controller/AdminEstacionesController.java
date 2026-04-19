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
    private Button btnPriorizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private MFXTableView<Sucursal> tableViewSucursal;
    private static final String ArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    @FXML
    private MFXTableView<Estacion> tableViewEstacion;
     private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTablillaSucursales();
        setupTablillaEstaciones();
        seleccionarPrimeraSucursal();
        cargarSucursalesEnTablilla();
        cargarEstacionesDeSucursal(obtenerSucursalSeleccionada());
        
        
        // TODO
    }    
    @Override
    public void initialize() {
        cargarSucursalesEnTablilla(); //siempre tiene que actualizar las sucursales en tablilla
        cargarEstacionesDeSucursal(obtenerSucursalSeleccionada());//siempre tiene que actualizar Estaciones en tablilla
    }
    @FXML
private void onMouseClickedTablillaSucursales(MouseEvent event) {
    Sucursal seleccionada = obtenerSucursalSeleccionada();
    cargarEstacionesDeSucursal(seleccionada);
}
    private void setupTablillaSucursales(){
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
    }
    //pone las columnas de la tablilla de sucursales
    private void setupTablillaEstaciones(){
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
    
    private void cargarSucursalesEnTablilla(){
        seleccionarPrimeraSucursal();
        List<Sucursal> lista = JsonUtil.cargarLista(ArchivoSucursales, Sucursal.class);
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

    private void configurarSeleccionSucursal() {
        tableViewSucursal.setOnMouseClicked(event -> {
            Sucursal seleccionada = obtenerSucursalSeleccionada();
            cargarEstacionesDeSucursal(seleccionada);
        });
    }
    
    // funcion pra obtener el objeto que ha sido seleccionado de la tablilla tableViewSucursal
    private Sucursal obtenerSucursalSeleccionada(){
        var seleccionadas = tableViewSucursal.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }

    private void seleccionarPrimeraSucursal(){
        if (!listaSucursales.isEmpty()){// si la lista de sucursales no esta vacia
            tableViewSucursal.getSelectionModel().clearSelection();//se limpia seleccion de la tablilla al iniciar
            tableViewSucursal.getSelectionModel().selectIndex(0);//se selecciona en la tablilla la primera sucursal de la lista por defecto
            cargarEstacionesDeSucursal(listaSucursales.get(0));//se carga las estaciones de la primera sucursal por defecto
        }
        else{
            cargarEstacionesDeSucursal(null); // en caso de que la lista de sucursales este vacia, no se cargan estaciones
        }
    }
    
    private void cargarEstacionesDeSucursal(Sucursal sucursal){
        listaEstaciones.clear();
        
        if(sucursal !=null && sucursal.getEstaciones() != null){
            listaEstaciones.addAll(sucursal.getEstaciones());
        }
        tableViewEstacion.setItems(listaEstaciones);
    }

    @FXML
    private void onActionTxfBuscar(ActionEvent event) {
    }
    //boton para agregar estacion en sucursal
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        Sucursal sucursalSeleccionada = obtenerSucursalSeleccionada();
        if (sucursalSeleccionada == null){
            new Mensaje().showModal(Alert.AlertType.ERROR,"Error Agregar", getStage(), "Tienes que seleccionar una sucursal");
        return;
        }
        String nombre = "Estacion" + getIdSiguienteEstacion();
        Estacion nueva = new Estacion(getIdSiguienteEstacion(),nombre,false); // crea estacion no preferencial por defecto
        if (sucursalSeleccionada.getEstaciones() == null){// por aquello de que el conjunto de estaciones este vacio, hacer uno nuevo
            sucursalSeleccionada.setEstaciones(new ArrayList<>());
        }
        //agregar la estacion y recargar la tablilla
        sucursalSeleccionada.getEstaciones().add(nueva);
        cargarEstacionesDeSucursal(sucursalSeleccionada);
        //para guardar todo en el Json, como la estacion es parte de la sucursal, una vez modificada, hay que guardar todo el arreglo
        guardarSucursales();
        new Mensaje().showModal(Alert.AlertType.INFORMATION,"Estacion agregada", getStage(), "Estacion ha sido agregada exitosamente");
        cargarEstacionesDeSucursal(obtenerSucursalSeleccionada());
    }
    
    private void guardarSucursales(){
        JsonUtil.guardarLista(ArchivoSucursales, new ArrayList<>(listaSucursales));
    }
            
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
    @FXML
    private void onActionPriorizar(ActionEvent event) {
    }

    @FXML
    private void onActionEliminar(ActionEvent event) {
    }

    
    
}
