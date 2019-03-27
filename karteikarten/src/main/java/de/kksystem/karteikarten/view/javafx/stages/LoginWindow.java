package de.kksystem.karteikarten.view.javafx.stages;

import java.net.URL;

import de.kksystem.karteikarten.view.javafx.controllers.LoginController;
import javafx.application.Application;
//import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginWindow extends Application {
	private static Stage window;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		final URL fxmlUrl = getClass().getResource("/fxml/LoginNew.fxml");
		final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
		fxmlLoader.setController(new LoginController());
		final Parent root = fxmlLoader.load();
		Scene scene = new Scene(root, 600, 400);
		window.setScene(scene);
		window.setTitle("Login Window");
		window.show();
	}

	public Stage getWindow(){
		return window;
	}

	public static void main(String[] args){
		launch(args);
	}
}
