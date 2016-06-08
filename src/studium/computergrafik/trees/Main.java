package studium.computergrafik.trees;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Group eiffelTower = (Group) FXMLLoader.load(getClass().getResource(
				"baseplate.fxml"));
		Scene scene = new Scene(eiffelTower, 1024.0, 768.0, true, 
				SceneAntialiasing.BALANCED);
		Controller controller = new Controller(scene);
		
		primaryStage.setTitle("Eiffel Tower");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
