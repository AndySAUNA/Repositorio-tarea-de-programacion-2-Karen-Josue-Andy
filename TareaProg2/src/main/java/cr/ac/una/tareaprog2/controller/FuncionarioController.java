package cr.ac.una.tareaprog2.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class FuncionarioController extends Controller implements Initializable {

    @FXML
    private MFXTableView tablillaFichas;

    @FXML
    private MFXButton btnLlamarFicha;

    @FXML
    private MFXButton btnLlamarSiguienteFicha;

    @FXML
    private MFXButton btnVerCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // inicialización automática de JavaFX (FXML injection)
    }

    @Override
    public void initialize() {
        setNombreVista("Funcionario");
    }

}