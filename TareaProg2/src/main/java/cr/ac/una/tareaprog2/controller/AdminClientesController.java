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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.Sucursal;
import util.FlowController;
import util.Formato;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
/*
    
    Nota: editar cliente no ha sido implementado aun, el view existe, pero el controlador no esta seteado
        agregar cliente y eliminar cliente funcionan bien
    
    */
public class AdminClientesController extends Controller implements Initializable {

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
    
    private static final String UrlArchivoClientes = "data/Clientes.json";
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      setupTablillaClientes();
      cargarClientes();
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
      cargarClientes();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //crea las columnas de la tablilla de clientes                  (funciona perfectamente)
    private void setupTablillaClientes(){
        //
        MFXTableColumn<Cliente> colCedula = 
                new MFXTableColumn<>("Cedula",true, Comparator.comparing(Cliente::getCedula));
        MFXTableColumn<Cliente> colNombre = 
                new MFXTableColumn<>("Nombre",true, Comparator.comparing(Cliente::getNombre));
        MFXTableColumn<Cliente> colApellidos = 
                new MFXTableColumn<>("Apellidos",true, Comparator.comparing(Cliente::getFechaNacimiento));
        MFXTableColumn<Cliente> colFechaNacimiento = 
                new MFXTableColumn<>("Fecha Nacimiento",true, Comparator.comparing(Cliente::getFechaNacimiento));
        //
        colCedula.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getCedula));
        colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getNombre));
        colApellidos.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getApellidos));
        colFechaNacimiento.setRowCellFactory(col -> new MFXTableRowCell<>(Cliente::getFechaNacimiento));
        //
        tablillaClientes.getTableColumns().setAll(colCedula, colNombre, colApellidos,colFechaNacimiento);
        tablillaClientes.setItems(listaClientes);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //actualiza el archovo con la lista de clientes en el ram despues de un cambio (funciona perfectamente)
    private void guardarClientes(){
        JsonUtil.guardarLista(UrlArchivoClientes,new ArrayList<>(listaClientes));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //carga la lista de clientes a la tablilla                      (funciona perfectamente)
    private void cargarClientes() {
        List<Cliente> lista = JsonUtil.cargarLista(UrlArchivoClientes, Cliente.class);
        if (lista == null){
            lista = new ArrayList<>();
        }
        listaClientes.clear();
        listaClientes.addAll(lista);
        tablillaClientes.setItems(listaClientes);
        Platform.runLater(() -> {
            tablillaClientes.setItems(null);
            tablillaClientes.setItems(listaClientes);
        });
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //metodo retorna el objeto que ha sido seleccionado en la lista (funciona perfectamente)
    private Cliente obtenerSeleccion(){
        var seleccionadas = tablillaClientes.getSelectionModel().getSelectedValues();
        if (seleccionadas == null || seleccionadas.isEmpty()){
            return null;
        }
        return seleccionadas.get(0);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML//                                                         (funciona perfectamente)
    private void onActionAgregarCliente(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("AgregarClienteView", this.getStage(), false);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML//                                                         (funciona perfectamente)
    private void onActionEliminarCliente(ActionEvent event) {
        Cliente seleccionado = obtenerSeleccion();
        if (seleccionado == null){
            new Mensaje().showModal(Alert.AlertType.WARNING, "Error Eliminar"
                , getStage(), "no hay seleccionado");
            return;
        }
        
        if (new Mensaje().showConfirmation("Atencion!", getStage(), 
                "Está seguro de querer eliminar el cliente: " + 
                        seleccionado.getNombre() + seleccionado.getApellidos() + "?") == true){
            try {
                Files.deleteIfExists(Paths.get(seleccionado.getRutaFoto()));
            } catch (IOException e) {
                System.out.println("error eliminando foto seleccionada.");
            }
            listaClientes.remove(seleccionado);
            guardarClientes();
            cargarClientes();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Éxito"
                , getStage(), "Cliente Eliminado exitosamente");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionEditarCliente(ActionEvent event) {//necesita implementar
        FlowController.getInstance().goViewInWindowModal("EditarClienteView", this.getStage(), false);
    }
    
}
