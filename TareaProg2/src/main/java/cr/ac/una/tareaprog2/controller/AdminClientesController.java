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
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.EstablecimientoDto;
import util.Formato;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminClientesController  extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTableView<Cliente> listaClientes;
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    @FXML
    private MFXComboBox<?> comboxSucursal01;
    @FXML
    private MFXButton btnAgregarCliente1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      setupListaClientes();
      setupSucursales();
        // TODO
    }    

    @Override
    public void initialize() {
      
    }
    
    private void setupListaClientes(){
        //
        MFXTableColumn<Cliente> columnaCedula = new MFXTableColumn<>("Cedula",true, Comparator.comparing(Cliente::getCedula));
        MFXTableColumn<Cliente> columnaNombre = new MFXTableColumn<>("Nombre",true, Comparator.comparing(Cliente::getNombre));
        MFXTableColumn<Cliente> columnaApellidos = new MFXTableColumn<>("Nombre",true, Comparator.comparing(Cliente::getApellidos));
        //
        columnaCedula.setRowCellFactory(c -> new MFXTableRowCell<>(Cliente::getCedula));
        columnaNombre.setRowCellFactory(c -> new MFXTableRowCell<>(Cliente::getNombre));
        columnaApellidos.setRowCellFactory(c -> new MFXTableRowCell<>(Cliente::getNombre));
        //
        listaClientes.getTableColumns().setAll(columnaCedula, columnaNombre, columnaApellidos);
        listaClientes.setItems(clientes);
    }
    private void setupSucursales(){
        
    }
    
}
