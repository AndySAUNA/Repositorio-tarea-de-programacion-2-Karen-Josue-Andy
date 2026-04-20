/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import cr.ac.una.tareaprog2.App;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import util.Mensaje;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class CambiarLogoController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button BtnCambiar;
    @FXML
    private ImageView btnCambiar;
    @FXML
    private ImageView imageViewCentral;
    
    private String rutaLogoSeleccionada;
    private String URLLogo = "file:data/Logo.png";
    private Image imagenAuxiliar;
    private Path direccionLogo = Paths.get("data", "Logo.png");
    private Path direccionAuxiliar;
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenAuxiliar = null;
        this.setNombreVista("Cambiar Logo");
        // TODO
    }   
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onDragEntered(DragEvent event) {
        System.out.println("on drag Entered");
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onDragDropped(DragEvent event) {// aqui se agrega la imagen para cambiar el logo
        Dragboard dragboard = event.getDragboard();
        boolean dropExitoso = false;
        if (dragboard.hasFiles() && !dragboard.getFiles().isEmpty()){
            File file = dragboard.getFiles().get(0);
            if(esImagenValida(file)){
                rutaLogoSeleccionada = file.getAbsolutePath();
                direccionAuxiliar = Paths.get(rutaLogoSeleccionada);
                imagenAuxiliar = new Image(file.toURI().toString());
                imageViewCentral.setImage(imagenAuxiliar);
                dropExitoso=true;
            }
        }
        if(dropExitoso){
            new Mensaje().showModal(Alert.AlertType.INFORMATION,"Exito", getStage(), "archivo agregado exitosamente");
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR,"ERROR", getStage(), "se necesista archivo tipo .png");
        }
        event.setDropCompleted(dropExitoso);
        event.consume();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    private boolean esImagenValida(File file){
        String nombre = file.getName().toLowerCase();
        return nombre.endsWith(".png");
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onDragDone(DragEvent event) {
        System.out.println("on drag Done");
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onDragOver(DragEvent event) {// este codigo es robado
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasFiles() && !dragboard.getFiles().isEmpty()) {
            File file = dragboard.getFiles().get(0);
            if (esImagenValida(file)) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        }
    event.consume();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onDragReleased(MouseDragEvent event) {
        System.out.println("on drag Released");
    }

    @FXML
    private void onActionBtnCambiar(ActionEvent event) {
        System.out.println("click en boton cambiar");
        if (direccionLogo == null || direccionAuxiliar == null){
            new Mensaje().showModal(Alert.AlertType.ERROR,"ERROR", getStage(), "se necesista archivo tipo .png seleccionado");
            System.out.println("if de no cambiar funciono");
            return;
        }
        try{
            System.out.println("se va a intentar cambiar el logo");
            Files.copy(direccionAuxiliar, direccionLogo, StandardCopyOption.REPLACE_EXISTING);
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION,"Exito", getStage(), "se ha cambiado el logo exitosamente, "
                    + "necesita reiniciar el programa para ver los cambios");
            this.getStage().close();
        }catch(Exception e){
            new Mensaje().showModal(Alert.AlertType.ERROR,"ERROR", getStage(), "no se pudo cambiar el logo");
        }
    }

    @FXML
    private void onActionBtnCambiar(MouseEvent event) {
    }

    
    
}
