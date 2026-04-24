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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.Cliente;
import util.Formato;
import util.JsonUtil;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AgregarClienteController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXTextField txfApellidos;
    @FXML
    private ImageView ivFotoPerfil;
    @FXML
    private MFXButton btnTomaNuevaFoto;
    @FXML
    private MFXButton btnTomaNuevaFoto1;
    @FXML
    private MFXDatePicker dpFechaNacimiento;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField txfContraseña;
    
    private String UrlRutaClientes = "data/Clientes.json"; // url string con la ruta del json de clientes
    private Cliente cliente = new Cliente(); // objeto cliente para generar el objeto a guardar en json
    private String rutaFotoSeleccionada = "";// Url de la foto seleccionada para guardar con el cliente
    private List<Node> requeridos = new ArrayList();// para los requeridos del formulario
    /*
    
    Nota:Contraseña no ha sido implementada ni en el modelo de cliente ni en este controlador, es para despues
    
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setNombreVista("Crear cuenta");//establece el nombre de la ventana
        txfCedula.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txfApellidos.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(60));
        txfUsuario.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(60));
        txfContraseña.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(60));
        cargarValoresPorDefecto();
        // TODO
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //funcion revisa si existe la direccion del json, si no existe la crea (funciona, completo)
    private void asegurarDirectorioDatos(){
        File archivo = new File(UrlRutaClientes);
        File parent = archivo.getParentFile();
        if(parent != null && !parent.exists()){
            parent.mkdirs();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia el contenido del formulario (funciona, completo)
    private void limpiarFormulario(){
        txfCedula.clear();
        txfNombre.clear();
        txfApellidos.clear();
        txfUsuario.clear();
        txfContraseña.clear();
        Image image = new Image(
            getClass().getResource("/cr/ac/una/tareaprog2/resources/AgregarImagen.png").toExternalForm()
        );
        ivFotoPerfil.setImage(image);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia contenido del objeto cliente totalmente (funciona, completo)
    private void limpiarCliente(){
        cliente.setNombre("");
        cliente.setApellidos("");
        cliente.setCedula("");
        cliente.setRutaFoto("");
        cliente.setFechaNacimiento(LocalDate.now());
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //limpia formulario, limpia objeto cliente y revisa que direccion del archivo json existe y lo crea (funciona, completo)
    private void cargarValoresPorDefecto(){
        limpiarFormulario();//limpia formulario
        limpiarCliente();//limpia cliente
        asegurarDirectorioDatos();//asegura que direccion del archivo json existe
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML//da aviso si seguro que quiere cancelar, de caso ser si, borra todo y cierra ventana
    private void onActionCancelar(ActionEvent event) {
        new Mensaje().showModal(Alert.AlertType.WARNING,
                        "Cancelar", getStage(), "Esta seguro de querer cancelar el tramite?");
        cargarValoresPorDefecto();
        eliminarFotoSeleccionada();
        Image image = new Image("resources/AgregarImagen.png");
        ivFotoPerfil.setImage(image);
        this.getStage().close();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionTomarFotoNueva(ActionEvent event) {
        tomarFoto();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //codigo tomado de paula, para tomar la foto
    private void tomarFoto() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cr/ac/una/tareaprog2/view/CameraView.fxml"));
        Parent root = loader.load();
        
        CameraController cameraController = loader.getController();
        
        Stage cameraStage = new Stage();
        cameraStage.setTitle("Tomar Foto");
        cameraStage.setScene(new Scene(root));
        cameraStage.initModality(Modality.APPLICATION_MODAL);
        cameraStage.setResizable(false);
        
        cameraStage.showAndWait();
        
        if (cameraController.isImageCaptured()) {
            Image capturedImage = cameraController.getCapturedImage();
            ivFotoPerfil.setImage(capturedImage);
            
            // Guardar la imagen
            String nombreArchivo = "foto_" + System.currentTimeMillis() + ".png";
            String rutaCompleta = "fotos/" + nombreArchivo;
            
            File directorio = new File("fotos");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            File archivoFoto = new File(rutaCompleta);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(capturedImage, null);
            ImageIO.write(bufferedImage, "png", archivoFoto);
            
            rutaFotoSeleccionada = rutaCompleta;
        }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la cámara: " + e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //codigo tomado de paula, para tomar la foto
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //esto es para eliminar la foto si no se usa lol 
    private void eliminarFotoSeleccionada(){
        try {
            Files.deleteIfExists(Paths.get(rutaFotoSeleccionada));
        } catch (IOException e) {
            System.out.println("error eliminando foto seleccionada.");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void indicarRequeridos(){
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txfCedula,txfNombre,txfApellidos,dpFechaNacimiento));
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    // gracias por el codigo profe (｡•̀ᴗ-)✧
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
    //busca en el archivo json, extrae la lista de entidades y los retorna (funciona, completo)
    private List<Cliente> cargarListaClientes(){
        List<Cliente> lista = JsonUtil.cargarLista(UrlRutaClientes, Cliente.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //revisa que no haya otra instancia de la misma cedula, devuelve true si existe, devuelve false si no existe
    private boolean existenciaCedula(){
    List<Cliente> listaClientes = cargarListaClientes(); //crea lista local tipo cliente cargada del json de clientes
    String nombre = txfNombre.getText().trim(); //retura valor del textfield nombre y lo pone en la variable nombre
        //para revisar que no se duplique el nombre
        for (Cliente suc : listaClientes) {
            if (suc.getNombre() != null) {
                if (suc.getNombre().equalsIgnoreCase(nombre)) {
                    new Mensaje().showModal(Alert.AlertType.WARNING,
                        "Cedula ya existe!", getStage(), "Ya existe un cliente con esta cedula.");
                    return true;
                }
            }
        }
        return false;
   }
   //-----------------------------------------------------------------------------------------------------------------------------------
   //este metodo llena el cliente con los datos del formulario
    private void cargarDatosAEntidad(){
                cliente.setCedula(txfCedula.getText().trim());
                cliente.setNombre(txfNombre.getText().trim());
                cliente.setApellidos(txfApellidos.getText().trim());
                //cliente.setUsuario(txfUsuario.getText().trim());
                //cliente.setContraseña(txfcontraseña.getText().trim());
                cliente.setRutaFoto(rutaFotoSeleccionada);
                cliente.setFechaNacimiento(dpFechaNacimiento.getValue());
}
   //-----------------------------------------------------------------------------------------------------------------------------------
    private void GuardarCliente(){
        List<Cliente> listaClientes = cargarListaClientes();    //carga la lista de clientes al ram
        cargarDatosAEntidad();                                  //llena el objeto cliente con los datos del formulario
        listaClientes.add(cliente);                             //agrega el cliente a la lista en ram
        JsonUtil.guardarLista(UrlRutaClientes, listaClientes);  //sobreescribe la lista de clientes en el json
   }
   //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionCrearCuenta(ActionEvent event) {
        try{
            //revisa que los espacios requeridos esten llenos
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Sucursal"
                , getStage(), invalidos);
                return;
            }
                if(!existenciaCedula()){// si no hay ya una instancia de la cedula, se procede con crear la cuenta
                    GuardarCliente();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION,
                            "Guardar Cliente", getStage(), "Se ha creado el Cliente exitosamente!");
                    cargarValoresPorDefecto();
                    this.getStage().close();
                }
        }catch(Exception ex){
            Logger.getLogger(AgregarSucursalController.class.getName()).
                    log(Level.SEVERE,"Error Creando Cuenta.",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Errror crear cuenta", getStage(),
                    "Ocurrio un error creando la cuenta");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
}
