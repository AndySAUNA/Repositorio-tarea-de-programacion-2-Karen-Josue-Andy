/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import model.Sucursal;
import util.Formato;
import util.JsonUtil;
import util.Mensaje;
import java.io.File;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AgregarSucursalController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregar;
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private MFXTextField txfNombreSucursal;
    @FXML
    private MFXTextField txfDescripcion;
    @FXML
    private MFXTextField txfCantidadEstaciones;
    
    private String rutaFotoSeleccionada;
    private Sucursal sucursal;
    private final String ArchivoSucursales = "data/Datos.json";
    private MFXTextField txfDireccion;
    private List<Node> requeridos = new ArrayList();
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Agregar Sucursal");
        txfNombreSucursal.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfCantidadEstaciones.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(60));
        
        sucursal = new Sucursal();
        bindSucursal();
        indicarRequeridos();
        asegurarDirectorioDatos();
        cargarValoresPorDefecto();
        // TODO
    }    
        @Override
    public void initialize() {
        
    }
    private void cargarValoresPorDefecto(){
        txfNombreSucursal.clear();
        txfDescripcion.clear();
        txfCantidadEstaciones.clear(); 
    }
    
    private List<Sucursal> cargarSucursales(){
        List<Sucursal> sucursales = JsonUtil.cargarLista(ArchivoSucursales, Sucursal.class);
        if (sucursales == null) {
            sucursales = new ArrayList<>();
        }
        return sucursales;
    }
    private int siguienteId(List<Sucursal> sucursales){ //consigue el id mas alto de la lista
        int maxId = 0;
    for (Sucursal s : sucursales) {
        if (s.getId() > maxId) {
            maxId = s.getId();
        }
    }
    return maxId + 1;
    }
    
    private void bindSucursal(){
    }
    private void indicarRequeridos(){
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txfNombreSucursal,txfCantidadEstaciones,txfDescripcion));
    }
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
    private void asegurarDirectorioDatos(){
        File archivo = new File(ArchivoSucursales);
        File parent = archivo.getParentFile();
        if(parent != null && !parent.exists()){
            parent.mkdirs();
        }
    }
    
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        try{
            //revisa que los espacios requeridos esten llenos
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Sucursal"
                , getStage(), invalidos);
                return;
            }
            //revisa que no haya otra instancia de ese nombre
            List<Sucursal> sucursales = cargarSucursales();
            String nombre = txfNombreSucursal.getText().trim();
            //para revisar que no se duplique el nombre
            for (Sucursal suc : sucursales) {
                 if (suc.getNombre() != null) {
                    if (suc.getNombre().equalsIgnoreCase(nombre)) {
                        new Mensaje().showModal(
                        Alert.AlertType.WARNING,
                            "Guardar Sucursal", getStage(), "Ya existe una sucursal con ese nombre.");
                        return;
                    }
                }
            }
            //ya todas las pruebas pasadas se guarda, avisa que todo en orden y cierra
            //llenar objeto con datos del view
            sucursal.setId(siguienteId(sucursales)); // el Id es automatico
            sucursal.setNombre(txfNombreSucursal.getText().trim());
            sucursal.setDireccion(txfDescripcion.getText().trim());
            //guardar
            sucursales.add(sucursal);
            asegurarDirectorioDatos();
            JsonUtil.guardarLista(ArchivoSucursales, sucursales);
            //limpiar datos
                new Mensaje().showModal(Alert.AlertType.INFORMATION,
                        "Guardar Sucursal", getStage(), "Se ha creado la sucursal exitosamente!");
                cargarValoresPorDefecto();
                this.getStage().close();
            
        }catch(Exception ex){
            Logger.getLogger(AgregarSucursalController.class.getName()).
                    log(Level.SEVERE,"Error guardando la sucursal.",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Sucursal", getStage(),
                    "Ocurrio un error guardando la sucursal");
                }
    }

    @FXML
    private void onActionTxfNombreSucursal(ActionEvent event) {
    }



    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        new Mensaje().showModal(Alert.AlertType.WARNING,
                        "Cancelar", getStage(), "Esta seguro de querer cancelar el tramite?");
        cargarValoresPorDefecto();
        this.getStage().close();
    }

    @FXML
    private void onActionTxfDescripcion(ActionEvent event) {
    }

    @FXML
    private void onActionTxfCantidadEstaciones(ActionEvent event) {
    }
    
}
