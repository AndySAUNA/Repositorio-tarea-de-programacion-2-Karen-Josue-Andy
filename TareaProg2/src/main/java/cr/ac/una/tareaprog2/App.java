package cr.ac.una.tareaprog2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.FlowController;
import util.AudioUtil;

/**
 * JavaFX App
 */
public class App extends Application {
    

     private static String rol = "";

    @Override
    public void start(Stage stage) throws Exception {
        FlowController.getInstance().InitializeFlow(stage, null);
        
        switch (rol) {
            case "admin":
                FlowController.getInstance().goViewInWindow("AdminView");
                break;
            case "kiosco":
                FlowController.getInstance().goViewInWindow("KioskoView");
                break;
            case "funcionario":
                FlowController.getInstance().goViewInWindow("FuncionarioView");
                break;
            case "proyeccion":
                FlowController.getInstance().goViewInWindow("PantallaView");
                break;
                 default:
                FlowController.getInstance().goViewInWindow("MainView");
                break;
        }
    }

    @Override
    public void stop() {
        AudioUtil.shutdown();
        System.out.println("Aplicación cerrada");
    }
    public static void main(String[] args) {
        if (args.length > 0) {
            rol = args[0];
            System.out.println("Rol seleccionado: " + rol);
        }
        launch();
    }
}