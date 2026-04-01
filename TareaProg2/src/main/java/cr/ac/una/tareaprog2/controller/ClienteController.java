
package cr.ac.una.tareaprog2.controller;

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

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {

    // ========== COMPONENTES DEL FXML ==========
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ImageView imgFoto;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, Integer> colEdad;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private String rutaFotoSeleccionada;
    private Cliente clienteSeleccionado;
    private final String ARCHIVO_CLIENTES = "data/clientes.json";

    // Inicializar
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar las columnas de la tabla
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        
        cargarClientes();

        // Cuando selecciona un cliente en la tabla, se carga en el formulario
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    cargarClienteEnFormulario(newSelection);
                }
            });
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
 
    @FXML
    private void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Resources", "*.png")
        );

        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            rutaFotoSeleccionada = archivo.getAbsolutePath();
            Image image = new Image("file:" + rutaFotoSeleccionada);
            imgFoto.setImage(image);
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
            // ACTUALIZAR CLIENTE EXISTENTE
            clienteSeleccionado.setCedula(txtCedula.getText());
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setApellidos(txtApellidos.getText());
            clienteSeleccionado.setFechaNacimiento(dpFechaNacimiento.getValue());
            clienteSeleccionado.setRutaFoto(rutaFotoSeleccionada);
        }

        guardarClientes();
        tablaClientes.refresh();
        limpiarFormulario();
        mostrarAlerta("Éxito","Cliente guardado correctamente");
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
}
