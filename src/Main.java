import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Trabajo Práctico 1
 * Cátedra de Simulación
 * Universidad Tecnológica Nacional - Facultad Regional Córdoba
 *
 * Integrantes del grupo:
 * 79906	Cibello, Sofia Florencia
 * 80022	Gonzalez, Florencia
 * 77749	Parrucci, Lara Estefania
 * 79071	Pieve Roiger, Ignacio
 * 80215	Sala, Lorenzo
 * 79876	Spini, Leila Aylén
 */

public class Main extends Application {
    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pantallaPrincipal.fxml"));
        primaryStage.setTitle("UTN FRC - Simulación - TP1");
        primaryStage.setScene(new Scene(root, 700, 750));
        primaryStage.show();
        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
