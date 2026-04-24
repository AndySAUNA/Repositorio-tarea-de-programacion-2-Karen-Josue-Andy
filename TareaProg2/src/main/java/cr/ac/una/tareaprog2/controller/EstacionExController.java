/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Cliente;
import model.Estacion;
import model.Ficha;
import model.Sucursal;
import util.AppContext;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class EstacionExController extends Controller implements Initializable {
    //agarra datos externos
    int idSucursal = (int) AppContext.getInstance().get("sucursalSeleccionada");
    int idEstacion = (int) AppContext.getInstance().get("estacionSeleccionada");
    
    //para manejar archivo y lista de clientes
    private static final String UrlArchivoClientes = "data/Clientes.json";
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(); //no se si se va a usar
    
    //para manejar archivo y lista de Sucursales
    private static final String UrlArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de Estaciones
    private String UrlArchivoEstaciones = "data/Datos.json";
    private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();
    
    @FXML
    private MFXTableView<Ficha> tablillaFichas;
    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private void onActionBtnLlamarFicha(ActionEvent event) {
    }

    @FXML
    private void onActionBtnLlamarSiguienteFicha(ActionEvent event) {
    }

    @FXML
    private void onActionBtnVerCliente(ActionEvent event) {
    }
    
}
