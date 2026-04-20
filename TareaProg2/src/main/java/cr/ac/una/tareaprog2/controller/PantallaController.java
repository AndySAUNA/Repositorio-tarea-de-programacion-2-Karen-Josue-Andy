package cr.ac.una.tareaprog2.controller;

import model.Llamado;  
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.AudioUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class PantallaController extends Controller implements Initializable {

    @FXML private Label lblUltimaFicha;
    @FXML private Label lblUltimaEstacion;
    @FXML private Label lblUltimaHora;
    @FXML private Label lblFechaHora;
    @FXML private Label lblAvisos;
    
    @FXML private VBox card1;
    @FXML private VBox card2;
    @FXML private VBox card3;
    @FXML private VBox card4;
    
    @FXML private Label ficha1;
    @FXML private Label ficha2;
    @FXML private Label ficha3;
    @FXML private Label ficha4;
    
    @FXML private Label estacion1;
    @FXML private Label estacion2;
    @FXML private Label estacion3;
    @FXML private Label estacion4;
    
    @FXML private Label hora1;
    @FXML private Label hora2;
    @FXML private Label hora3;
    @FXML private Label hora4;

    private Timeline reloj;
    private Timeline animacionAvisos;
    private Queue<Llamado> ultimosLlamados = new LinkedList<>();  
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarReloj();
        iniciarAnimacionAvisos();
        cargarDatosPrueba();
    }
    
    private void iniciarReloj() {
        reloj = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarFechaHora()));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }
    
    private void actualizarFechaHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        lblFechaHora.setText(LocalDateTime.now().format(formatter));
    }
    
    private void iniciarAnimacionAvisos() {
        animacionAvisos = new Timeline(new KeyFrame(Duration.seconds(0.03), e -> {
            String texto = lblAvisos.getText();
            if (texto != null && texto.length() > 0) {
                texto = texto.substring(1) + texto.charAt(0);
                lblAvisos.setText(texto);
            }
        }));
        animacionAvisos.setCycleCount(Timeline.INDEFINITE);
        animacionAvisos.play();
    }
    
    public void registrarLlamado(int numeroFicha, String nombreEstacion) {
        String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String fichaStr = "Ficha #" + numeroFicha;
        
      
        lblUltimaFicha.setText(fichaStr);
        lblUltimaEstacion.setText("Estación: " + nombreEstacion);
        lblUltimaHora.setText("Hora: " + horaActual);
        
        Llamado nuevo = new Llamado(fichaStr, nombreEstacion, horaActual);
        ultimosLlamados.add(nuevo);

        while (ultimosLlamados.size() > 4) {
            ultimosLlamados.poll();
        }
        
        actualizarCuadros();
        AudioUtil.reproducirLlamado(numeroFicha, nombreEstacion);
    }
    
    private void registrarLlamadoSilencioso(int numeroFicha, String nombreEstacion) {
    String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    String fichaStr = "Ficha #" + numeroFicha;

    lblUltimaFicha.setText(fichaStr);
    lblUltimaEstacion.setText("Estación: " + nombreEstacion);
    lblUltimaHora.setText("Hora: " + horaActual);

    Llamado nuevo = new Llamado(fichaStr, nombreEstacion, horaActual);
    ultimosLlamados.add(nuevo);

    while (ultimosLlamados.size() > 4) {
        ultimosLlamados.poll();
    }

    actualizarCuadros();
}
    
    private void actualizarCuadros() {
        Llamado[] llamados = ultimosLlamados.toArray(new Llamado[0]);
        
        if (llamados.length >= 1) {
            ficha1.setText(llamados[llamados.length - 1].getFicha());
            estacion1.setText(llamados[llamados.length - 1].getEstacion());
            hora1.setText(llamados[llamados.length - 1].getHora());
        }
        
        if (llamados.length >= 2) {
            ficha2.setText(llamados[llamados.length - 2].getFicha());
            estacion2.setText(llamados[llamados.length - 2].getEstacion());
            hora2.setText(llamados[llamados.length - 2].getHora());
        } else {
            limpiarCuadro(ficha2, estacion2, hora2);
        }
        
        if (llamados.length >= 3) {
            ficha3.setText(llamados[llamados.length - 3].getFicha());
            estacion3.setText(llamados[llamados.length - 3].getEstacion());
            hora3.setText(llamados[llamados.length - 3].getHora());
        } else {
            limpiarCuadro(ficha3, estacion3, hora3);
        }
        
        if (llamados.length >= 4) {
            ficha4.setText(llamados[llamados.length - 4].getFicha());
            estacion4.setText(llamados[llamados.length - 4].getEstacion());
            hora4.setText(llamados[llamados.length - 4].getHora());
        } else {
            limpiarCuadro(ficha4, estacion4, hora4);
        }
    }
    
    private void limpiarCuadro(Label ficha, Label estacion, Label hora) {
        ficha.setText("---");
        estacion.setText("---");
        hora.setText("---");
    }
    
    private void cargarDatosPrueba() {
        registrarLlamadoSilencioso(42, "Caja 1");
        registrarLlamadoSilencioso(43, "Caja 2");
        registrarLlamadoSilencioso(44, "Caja 1");
        registrarLlamadoSilencioso(45, "Caja 3");
        registrarLlamado(46, "Caja 2");
        
    }
    @Override
    public void initialize() {
        
    }
}