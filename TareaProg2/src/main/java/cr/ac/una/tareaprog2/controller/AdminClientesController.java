/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.EstablecimientoDto;
import model.Sucursal;
import util.Formato;
import util.JsonUtil;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminClientesController  extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnEliminarCliente;
    @FXML
    private MFXButton btnAgregarCliente;
    @FXML
    private MFXButton btnEditarCliente;
    @FXML
    private MFXTableView<Cliente> tablillaClientes;
    private static final String ArchivoClientes = "data/Datos.json";//nota: hay que agregar la direccion
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      setupTablillaClientes();
        // TODO
    }    

    @Override
    public void initialize() {
      
    }
    
    private void setupTablillaClientes(){
        //
        MFXTableColumn<Cliente> colCedula = 
                new MFXTableColumn<>("Cedula",true, Comparator.comparing(Cliente::getCedula));
        MFXTableColumn<Cliente> colNombre = 
                new MFXTableColumn<>("Nombre",true, Comparator.comparing(Cliente::getNombre));
        MFXTableColumn<Cliente> colApellidos = 
                new MFXTableColumn<>("Apellidos",true, Comparator.comparing(Cliente::getApellidos));
        //
        colCedula.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getCedula));
        colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getNombre));
        colApellidos.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getNombre));
        //
        tablillaClientes.getTableColumns().setAll(colCedula, colNombre, colApellidos);
        tablillaClientes.setItems(listaClientes);
    }
    private void cargarClientesEnTablilla(){
        List<Sucursal> lista = JsonUtil.cargarLista(ArchivoClientes, Sucursal.class);
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaClientes.clear();
        listaClientes.addAll(listaClientes);
        //tableViewSucursal.setItems(listaSucursales);
        Platform.runLater(() -> {
            tablillaClientes.setItems(null);
            tablillaClientes.setItems(listaClientes);
        });
    }
    private void setupSucursales(){
        
    }

    @FXML
    private void onActionAgregarCliente(ActionEvent event) {
    }

    @FXML
    private void onActionEliminarCliente(ActionEvent event) {
    }

    @FXML
    private void onActionEditarCliente(ActionEvent event) {
    }
    
}
