/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class AdminTramitesController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXComboBox<?> cboxSucursal;
    @FXML
    private MFXDatePicker txfFechaTramite;
    @FXML
    private MFXButton btnAgregarTramite;
    @FXML
    private MFXListView<?> lisvTramites1;
    @FXML
    private MFXListView<?> lisvTramite2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionCboxSucursal(ActionEvent event) {
    }

    @FXML
    private void onActionTxfTramite(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregarTramite(ActionEvent event) {
    }

    @FXML
    private void onActionLisvTramites1(MouseEvent event) {
    }

    
}
