/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import model.Ficha;
import model.Sucursal;
import util.Formato;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AgregarTramiteController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXCheckbox chkPreferencial;
    @FXML
    private MFXComboBox<Sucursal> cbSucursal;
    @FXML
    private MFXDatePicker dpFechaTramite;
    @FXML
    private MFXComboBox<String> cbHoraAtendido;
    
    
    private Ficha ficha = new Ficha(); // generar el objeto a guardar en json
    private List<Node> requeridos = new ArrayList();// para los requeridos del formulario
    
    //para manejar archivo y lista de Fichas
    private String UrlArchivoFichas = "data/Tramites.json"; // url string con la ruta del json
    private final ObservableList<Ficha> listaFichas = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de Sucursales
    private static final String UrlArchivoSucursales = "data/Datos.json";
    private final ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList();
    
    //para manejar archivo y lista de clientes
    private static final String UrlArchivoClientes = "data/Clientes.json";
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Crear Tramite");
        txfCedula.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        setFormulario();
        indicarRequeridos();
        cargarValoresPorDefecto();
    }    

    @Override
    public void initialize() {
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //establece los campos requeridos del formulario                        (funciona perfectamente)
    private void indicarRequeridos(){
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txfCedula,cbSucursal,dpFechaTramite,cbHoraAtendido));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    // gracias por el codigo profe (｡•̀ᴗ-)✧                                  (funciona perfectamente)
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
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia formulario, limpia objeto cliente y revisa que direccion del archivo json existe y lo crea (funciona perfectamente)
    private void cargarValoresPorDefecto(){
        limpiarFormulario();//limpia formulario
        limpiarFicha();//asegura una ficha limpia por defecto
        asegurarDirectorioDatos();//asegura que direccion del archivo json existe
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia contenido del objeto totalmente                                (funciona perfectamente)
    private void limpiarFicha(){
        ficha.setNumero(0);
        ficha.setCedulaCliente("");
        ficha.setIdTramite(0);
        ficha.setIdSucursal(0);
        ficha.setIdEstacion(0);
        ficha.setFechaHoraGeneracion(LocalDateTime.now());
        ficha.setFechaHoraLlamado(LocalDateTime.now());
        ficha.setEstado("ESPERA");
        ficha.setPreferencial(false);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia el contenido del formulario                                    (funciona perfectamente)
    private void limpiarFormulario(){
        txfCedula.clear();
        cbSucursal.clear();
        dpFechaTramite.clear();
        cbHoraAtendido.clear();
        chkPreferencial.setSelected(false);
        cbSucursal.getSelectionModel().clearSelection();
        cbSucursal.setValue(null);
        cbHoraAtendido.getSelectionModel().clearSelection();
        cbHoraAtendido.setValue(null);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //funcion revisa si existe la direccion del json, si no existe la crea  (funciona perfectamente)
    private void asegurarDirectorioDatos(){
        File archivo = new File(UrlArchivoFichas);
        File parent = archivo.getParentFile();
        if(parent != null && !parent.exists()){
            parent.mkdirs();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //setea todos los componentes del formulario                            (funciona perfectamente)
    private void setFormulario(){
        listaSucursales.setAll(cargarListaSucursales());
        cbSucursal.setItems(listaSucursales);
        setHoraAtendido();
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //setea el combo box de hora atendido para elegir una hora              (funciona perfectamente)
    private void setHoraAtendido(){
        ObservableList<String> horas = FXCollections.observableArrayList();
        LocalTime hora = LocalTime.of(8, 0);
        LocalTime fin = LocalTime.of(16, 0);
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);

        while (!hora.isAfter(fin)) {
            horas.add(hora.format(formatoHora));
            hora = hora.plusMinutes(30);
        }
        cbHoraAtendido.setItems(horas);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //busca en el archivo json, extrae la lista de entidades y los retorna  (funciona perfectamente)
    private List<Ficha> cargarListaFichas(){
        List<Ficha> lista = JsonUtil.cargarLista(UrlArchivoFichas, Ficha.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //busca en el archivo json, extrae la lista de entidades y los retorna  (funciona perfectamente)
    private List<Sucursal> cargarListaSucursales(){
        List<Sucursal> lista = JsonUtil.cargarLista(UrlArchivoSucursales, Sucursal.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //busca en el archivo json, extrae la lista de entidades y los retorna  (funciona perfectamente)
    private List<Cliente> cargarListaClientes(){
        List<Cliente> lista = JsonUtil.cargarLista(UrlArchivoClientes, Cliente.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //revisa si existe la cedula puesta en el formulario                    (funciona perfectamente)
    private boolean existeCedula(){
    List<Cliente> listaClientes = cargarListaClientes(); //crea lista en ram tipo cliente cargada del json de clientes
    String cedula = txfCedula.getText().trim(); //retura valor del textfield cedula y lo pone en una variable
        //para buscar la cedula
        Boolean existe = false;
        for (Cliente c : listaClientes) {
            if (c.getCedula() != null) {
                if (c.getCedula().equalsIgnoreCase(cedula)) {
                    existe = true;
                }
            }
        }
        if(!existe){
            new Mensaje().showModal(Alert.AlertType.WARNING,
                        "Cedula no existe!", getStage(), "no existe la cedula digitada.");
        }
        return existe;
   }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //este metodo asigna numero automaticamente a la ficha                  (funciona perfectamente)
    private int asignarNumero(){
        listaFichas.setAll(cargarListaFichas());
        int maxId = 0;
        for (Ficha s : listaFichas) {
            if (s.getNumero() > maxId) {
                maxId = s.getNumero();
            }
        }
        return maxId + 1;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    /*
    este metodo toma la fecha del dpFechaTramite y convierte a string el 
    cbHoraAtendido a hora, los combina y los retorna como LocalDateTime     (funciona perfectamente)
    */
    private LocalDateTime cargarFechaHora(){
        LocalDate fecha = dpFechaTramite.getValue();
        String horaTextual = cbHoraAtendido.getValue();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        LocalTime hora = LocalTime.parse(horaTextual, dtf);
        LocalDateTime fechaHora = LocalDateTime.of(fecha,hora);
        return fechaHora;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //este metodo llena la ficha con los datos del formulario               (funciona perfectamente)
    private void cargarDatosAEntidad(){
        ficha.setNumero(asignarNumero());
        ficha.setCedulaCliente(txfCedula.getText().trim());
        ficha.setIdSucursal(cbSucursal.getValue().getId());
        ficha.setPreferencial(chkPreferencial.isSelected());
        ficha.setFechaHoraLlamado(cargarFechaHora());
        
        ficha.setFechaHoraGeneracion(LocalDateTime.now());
        ficha.setEstado("ESPERA");
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //hace el proceso de agregar datos del cliente al json                  (funciona perfectamente)
    private void GuardarTramite(){
        List<Ficha> listaFichas = cargarListaFichas();          //carga la lista al ram del json
        cargarDatosAEntidad();                                  //llena el objeto cliente con los datos del formulario
        listaFichas.add(ficha);                                 //agrega la entidad a la lista en ram
        JsonUtil.guardarLista(UrlArchivoFichas, listaFichas);   //sobreescribe la lista en el json
   }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //este metodo es para imprimir el pdf
    private void imprimirPDf(){
        ficha.getCedulaCliente();
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML//                                                                 (funciona perfectamente)
    private void onActionAgregarTramite(ActionEvent event) {
        try{
            //revisa que los espacios requeridos esten llenos
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Tramite"
                , getStage(), invalidos);
                return;
            }
            if(existeCedula()){// revisa que existe la cedula
                GuardarTramite();
                new Mensaje().showModal(Alert.AlertType.INFORMATION,
                        "Guardar Tramite", getStage(), "Se ha creado el Tramite exitosamente!");
                    cargarValoresPorDefecto();
                    this.getStage().close();
                }
        }catch(Exception ex){
            Logger.getLogger(AgregarSucursalController.class.getName()).
                    log(Level.SEVERE,"Error Creando Cuenta.",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Errror creando tramite", getStage(),
                    "Ocurrio un error creando el tramite");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML//da aviso si seguro que quiere cancelar, de caso ser si, borra todo y cierra ventana (funciona perfectamente)
    private void onActionCancelar(ActionEvent event) {
        if (new Mensaje().showConfirmation("Cancelar", getStage(), 
                "esta seguro de cancelar el tramite?") == true){
            cargarValoresPorDefecto();
            this.getStage().close();
         }
    }
}
