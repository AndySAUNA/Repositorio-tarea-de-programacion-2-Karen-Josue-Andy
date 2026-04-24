package cr.ac.una.tareaprog2.controller;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class CameraController extends Controller implements Initializable {

    @FXML
    private ImageView imageViewCamera;

    private Webcam webcam;
    private Thread cameraThread;
    private volatile boolean running = true;
    private Image capturedImage;
    private boolean imageCaptured = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarCamara();
    }
    @Override
    public void initialize() {
        
    }

    private void iniciarCamara() {
        try {
            webcam = Webcam.getDefault();
            if (webcam != null) {
                webcam.open();

                cameraThread = new Thread(() -> {
                    while (running) {
                        BufferedImage bufferedImage = webcam.getImage();
                        if (bufferedImage != null) {
                            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                            javafx.application.Platform.runLater(() -> {
                                if (!imageCaptured) {
                                    imageViewCamera.setImage(image);
                                }
                            });
                        }
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                });
                cameraThread.setDaemon(true);
                cameraThread.start();
            } else {
                mostrarAlerta("Error", "No se detectó una cámara en el sistema");
                cerrar();
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo iniciar la cámara: " + e.getMessage());
            cerrar();
        }
    }

    @FXML
    private void tomarFoto() {
        if (webcam != null && webcam.isOpen()) {
            BufferedImage bufferedImage = webcam.getImage();
            if (bufferedImage != null) {
                capturedImage = SwingFXUtils.toFXImage(bufferedImage, null);
                imageCaptured = true;
                imageViewCamera.setImage(capturedImage);
                mostrarAlerta("Éxito", "Foto capturada. Haz clic en Aceptar para guardar.");
            }
        }
    }

    public Image getCapturedImage() {
        return capturedImage;
    }

    public boolean isImageCaptured() {
        return imageCaptured;
    }

    @FXML
    private void cerrar() {
        running = false;
        if (cameraThread != null && cameraThread.isAlive()) {
            cameraThread.interrupt();
        }
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
        this.getStage().close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    
}