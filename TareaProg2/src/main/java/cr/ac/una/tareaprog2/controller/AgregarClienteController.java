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
            boolean result = Files.deleteIfExists(Paths.get(rutaFotoSeleccionada));
            if (result) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("File does not exist.");
            }
        } catch (IOException e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "ERROR"
                , getStage(), "Ocurrió un error eliminando la foto");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private void indicarRequeridos(){
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txfCedula,txfNombre,txfApellidos,txfContraseña,dpFechaNacimiento));
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
    private List<Cliente> cargarLista(){
        List<Cliente> lista = JsonUtil.cargarLista(UrlRutaClientes, Cliente.class);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
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
            //revisa que no haya otra instancia de ese nombre
            List<Cliente> clientes = cargarLista();
            String nombre = txfNombreSucursal.getText().trim();
            //para revisar que no se duplique el nombre
            for (Cliente suc : sucursales) {
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
            Cliente.setId(siguienteId(sucursales)); // el Id es automatico
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
    //-----------------------------------------------------------------------------------------------------------------------------------
}
