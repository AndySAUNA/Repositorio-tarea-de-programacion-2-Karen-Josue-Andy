/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Cliente;
import model.Estacion;
import model.Ficha;
import model.Sucursal;
import util.FlowController;
import util.JsonUtil;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminTramitesController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXComboBox<Sucursal> cboxSucursal;
    @FXML
    private MFXDatePicker txfFechaTramite;
    @FXML
    private MFXButton btnAgregarTramite;
    @FXML
    private MFXTableView<Ficha> tableViewFicha;
    @FXML
    private MFXTableView<Estacion> tableViewEstacion;
    
   //para manejar archivo y lista de Fichas
    private String UrlArchivoFichas = "data/Tramites.json";
    private final ObservableList<Ficha> listaFichas = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de Sucursales
    private static final String UrlArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de clientes
    private static final String UrlArchivoClientes = "data/Clientes.json";
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(); //no se si se va a usar
    
    //para manejar archivo y lista de Estaciones
    private String UrlArchivoEstaciones = "data/Datos.json";
    private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFormulario();
        
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        cargarFichas();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //setea todos los componentes del formulario                                    (funciona perfectamente)
    private void setFormulario(){
        listaSucursales.setAll(cargarListaSucursales());
        cboxSucursal.setItems(listaSucursales);
        setupTablillaEstaciones();
        setupTablillaFichas();
        cargarFichas();
        cargarEstaciones();
        
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
    //crea las columnas de la tablilla de estaciones                                (funciona perfectamente)
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
    //crea las columnas de la tablilla de fichas                                    (funciona perfectamente)
    private void setupTablillaFichas(){// funciona
            if(!tableViewFicha.getTableColumns().isEmpty()){
                return;
            }
            MFXTableColumn<Ficha> colNumero = 
                    new MFXTableColumn<>("Numero", true, Comparator.comparingInt(Ficha::getNumero));
            MFXTableColumn<Ficha> colCedula = 
                    new MFXTableColumn<>("Cedula Cliente", true, Comparator.comparing(Ficha::getCedulaCliente));
            MFXTableColumn<Ficha> colPreferencial = 
                    new MFXTableColumn<>("Preferencial", true, Comparator.comparing(Ficha::isPreferencial));

            colNumero.setRowCellFactory(col -> new MFXTableRowCell<>(Ficha::getNumero));
            colCedula.setRowCellFactory(col -> new MFXTableRowCell<>(Ficha::getCedulaCliente));
            colPreferencial.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> Estacion.isPreferencial()));

            tableViewFicha.getTableColumns().setAll(colNumero,colCedula,colPreferencial);
            tableViewFicha.setItems(listaFichas);
        }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //carga la lista de clientes a la tablilla                                      (funciona perfectamente)
    private void cargarFichas() {
        List<Ficha> lista = JsonUtil.cargarLista(UrlArchivoFichas, Ficha.class);
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaFichas.clear();
        listaFichas.addAll(lista);
        tableViewFicha.setItems(listaFichas);
        Platform.runLater(() -> {
            tableViewFicha.setItems(null);
            tableViewFicha.setItems(listaFichas);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //carga la lista de estaciones a la tablilla de estaciones cuando hay una sucursal seleccionada (funciona perfectamente)
    private void cargarEstaciones() {
        if (cboxSucursal.getValue() == null){
            return;
        }
        List<Estacion> lista = cboxSucursal.getValue().getEstaciones();
        
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaEstaciones.clear();
        listaEstaciones.addAll(lista);
        tableViewEstacion.setItems(listaEstaciones);
        Platform.runLater(() -> {
            tableViewEstacion.setItems(null);
            tableViewEstacion.setItems(listaEstaciones);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //metodo retorna el objeto que ha sido seleccionado en la lista                 (funciona perfectamente)
    private Ficha obtenerFichaSeleccionada(){
        var seleccionadas = tableViewFicha.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionCboxSucursal(ActionEvent event) {
        cargarEstaciones();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionTxfTramite(ActionEvent event) {
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionBtnAgregarTramite(ActionEvent event) {
         FlowController.getInstance().goViewInWindowModal("AgregarTramiteView", this.getStage(), false);
    }


    
}
