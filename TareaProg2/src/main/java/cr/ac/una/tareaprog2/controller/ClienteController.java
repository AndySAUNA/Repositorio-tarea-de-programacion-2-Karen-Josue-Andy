package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Cliente;
import util.JsonUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class ClienteController extends Controller implements Initializable {

   
    @FXML private MFXTextField txtNombre;
    @FXML private MFXTextField txtApellidos;
    @FXML private MFXTextField txtCedula;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ImageView imgFoto;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, Integer> colEdad;
    @FXML private Button btnTomarFoto;
    @FXML private Button btnSubirFoto;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;


    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private String rutaFotoSeleccionada;
    private Cliente clienteSeleccionado;
    private final String ARCHIVO_CLIENTES = "data/clientes.json";

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        cargarClientes();

        
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    cargarClienteEnFormulario(newSelection);
                }
            });
    }
        @Override
    public void initialize() {
       
    }

    
    private void cargarClientes() {
        List<Cliente> clientes = JsonUtil.cargarLista(ARCHIVO_CLIENTES, Cliente.class);
        listaClientes.clear();
        listaClientes.addAll(clientes);
        tablaClientes.setItems(listaClientes);
    }

    private void guardarClientes() {
        JsonUtil.guardarLista(ARCHIVO_CLIENTES, new java.util.ArrayList<>(listaClientes));
    }

    
    private void cargarClienteEnFormulario(Cliente c) {
        clienteSeleccionado = c;
        txtCedula.setText(c.getCedula());
        txtNombre.setText(c.getNombre());
        txtApellidos.setText(c.getApellidos());
        dpFechaNacimiento.setValue(c.getFechaNacimiento());
        
        if (c.getRutaFoto() != null && !c.getRutaFoto().isEmpty()) {
            try {
                Image image = new Image("file:" + c.getRutaFoto());
                imgFoto.setImage(image);
                rutaFotoSeleccionada = c.getRutaFoto();
            } catch (Exception e) {
                imgFoto.setImage(null);
            }
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellidos.clear();
        dpFechaNacimiento.setValue(null);
        imgFoto.setImage(null);
        rutaFotoSeleccionada = null;
        clienteSeleccionado = null;
        tablaClientes.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void subirFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png")
        );

        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            rutaFotoSeleccionada = archivo.getAbsolutePath();
            Image image = new Image("file:" + rutaFotoSeleccionada);
            imgFoto.setImage(image);
        }
    }

    @FXML
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
            imgFoto.setImage(capturedImage);
            
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

    @FXML
    private void guardarCliente() {
        if (txtCedula.getText().isEmpty()) {
            mostrarAlerta("Error", "La cédula es obligatoria");
            return;
        }
        if (txtNombre.getText().isEmpty()) {
            mostrarAlerta("Error", "El nombre es obligatorio");
            return;
        }
        
        boolean cedulaExiste = listaClientes.stream()
            .anyMatch(c -> c.getCedula().equals(txtCedula.getText()) 
                && (clienteSeleccionado == null || !c.equals(clienteSeleccionado)));

        if (cedulaExiste) {
            mostrarAlerta("Error", "Ya existe un cliente con esa cédula");
            return;
        }

        if (clienteSeleccionado == null) {
            Cliente nuevo = new Cliente();
            nuevo.setCedula(txtCedula.getText());
            nuevo.setNombre(txtNombre.getText());
            nuevo.setApellidos(txtApellidos.getText());
            nuevo.setFechaNacimiento(dpFechaNacimiento.getValue());
            nuevo.setRutaFoto(rutaFotoSeleccionada);
            listaClientes.add(nuevo);
        } else {
            clienteSeleccionado.setCedula(txtCedula.getText());
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setApellidos(txtApellidos.getText());
            clienteSeleccionado.setFechaNacimiento(dpFechaNacimiento.getValue());
            clienteSeleccionado.setRutaFoto(rutaFotoSeleccionada);
        }

        guardarClientes();
        tablaClientes.refresh();
        limpiarFormulario();
        mostrarAlerta("Éxito", "Cliente guardado correctamente");
    }

    @FXML
    private void eliminarCliente() {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar");
            confirm.setHeaderText(null);
            confirm.setContentText("¿Eliminar cliente " + seleccionado.getNombreCompleto() + "?");
            
            if (confirm.showAndWait().get() == ButtonType.OK) {
                listaClientes.remove(seleccionado);
                guardarClientes();
                limpiarFormulario();
                mostrarAlerta("Éxito", "Cliente eliminado");
            }
        } else {
            mostrarAlerta("Error", "Seleccione un cliente para eliminar");
        }
    }
    

}