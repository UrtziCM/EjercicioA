package ejercicioa;

import controllers.EjercicioAController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EjercicioA extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	Parent root = FXMLLoader.load(this.getClass().getResource("/fxml/Encuesta.fxml"));
    	Scene scene = new Scene( root, 300, 275);
    	EjercicioAController controlador = new EjercicioAController(scene);
    	
    	
    	
        stage.setTitle("Encuesta");
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
		launch(args);
	}
}
