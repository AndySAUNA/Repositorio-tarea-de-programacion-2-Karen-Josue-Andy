/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Ficha;
import model.Sucursal;
import util.AppContext;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class EstacionExController extends Controller implements Initializable {
    
    String idSucursal = (String) AppContext.getInstance().get("sucursalSeleccionada");
    String idEstacion = (String) AppContext.getInstance().get("estacionSeleccionada");
    @FXML
    private MFXTableView<Ficha> tablillaFichas;
    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //-----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        
    }
    //-----------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private void onActionBtnLlamarFicha(ActionEvent event) {
    }

    @FXML
    private void onActionBtnLlamarSiguienteFicha(ActionEvent event) {
    }

    @FXML
    private void onActionBtnVerCliente(ActionEvent event) {
    }
    
}
