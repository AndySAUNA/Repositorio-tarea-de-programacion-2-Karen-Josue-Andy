/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareaprog2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Estacion;
import model.Ficha;

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
    private MFXTableView<Ficha> tableViewFicha;
    private final ObservableList<Ficha> listaFichas = FXCollections.observableArrayList();
    @FXML
    private MFXTableView<Estacion> tableViewEstacion;
    private final ObservableList<Estacion> listaEstaciones = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTablillaEstaciones();
        setupTablillaFichas();
        // TODO
    }    

    @Override
    public void initialize() {
        
    }
    private void setupTablillaEstaciones(){// funciona
        if(!tableViewEstacion.getTableColumns().isEmpty()){
            return;
        }
        MFXTableColumn<Estacion> colNumero = 
                new MFXTableColumn<>("Numero", true, Comparator.comparingInt(Estacion::getId));
        MFXTableColumn<Estacion> colNombre = 
                new MFXTableColumn<>("Nombre", true, Comparator.comparing(Estacion::getNombre));
        MFXTableColumn<Estacion> colPreferencial = 
                new MFXTableColumn<>("Preferencial", true, Comparator.comparing(Estacion::isPreferencial));
        
        colNumero.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> String.valueOf(Estacion.getId())));
        colNombre.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion::getNombre));
        colPreferencial.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> Estacion.isPreferencial()));
        
        tableViewEstacion.getTableColumns().setAll(colNumero,colNombre,colPreferencial);
        tableViewEstacion.setItems(listaEstaciones);
    }
    private void setupTablillaFichas(){// funciona
            if(!tableViewFicha.getTableColumns().isEmpty()){
                return;
            }
            MFXTableColumn<Ficha> colNumero = 
                    new MFXTableColumn<>("Numero", true, Comparator.comparingInt(Ficha::getNumero));
            MFXTableColumn<Ficha> colCedula = 
                    new MFXTableColumn<>("Cedula Cliente", true, Comparator.comparing(Ficha::getCedulaCliente));
            MFXTableColumn<Ficha> colPreferencial = 
                    new MFXTableColumn<>("Preferencial", true, Comparator.comparing(Ficha::isPreferencial));

            colNumero.setRowCellFactory(col -> new MFXTableRowCell<>(Ficha::getNumero));
            colCedula.setRowCellFactory(col -> new MFXTableRowCell<>(Ficha::getCedulaCliente));
            colPreferencial.setRowCellFactory(col -> new MFXTableRowCell<>(Estacion -> Estacion.isPreferencial()));

            tableViewFicha.getTableColumns().setAll(colNumero,colCedula,colPreferencial);
            tableViewFicha.setItems(listaFichas);
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


    
}
