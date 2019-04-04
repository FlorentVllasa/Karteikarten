package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswordWindowController implements Initializable{
	private String emailAdresse;
	
	LoginWindow lw = new LoginWindow();
	WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private PasswordField txtNewPassword;
    
    @FXML 
    private PasswordField txtConfirmationPassword;
    
    @FXML
    private Button btnChangePassword;
		
	CreateNewPasswordWindowController(String emailAdresse){
		this.emailAdresse = emailAdresse;
	}
	
	private void changePassword(ActionEvent event) {
		if (txtNewPassword.getText().isEmpty() || txtConfirmationPassword.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText("Bitte füllen Sie die beiden Passwortfelder aus!");
    		alert.showAndWait();
		} else if (!txtNewPassword.getText().equals(txtConfirmationPassword.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText("Leider stimmen Ihre Passwörter nicht überein!");
    		alert.showAndWait();
		} else {
			ServiceFacade.getInstance().updatePassword(txtNewPassword.getText(), ServiceFacade.getInstance().findUserByEMail(emailAdresse).getUserId());
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Erfolgreich!");
    		alert.setHeaderText("Ihr Passwort wurde geändert. \nBitte loggen Sie sich mit dem neuen Passwort ein.");
    		alert.showAndWait();
			switchToLoginWindow();
		}
	}

	public void switchToLoginWindow(){
		try{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/fxml/LoginNew.fxml"));
			fxmlLoader.setController(new LoginController());
			Parent root = fxmlLoader.load();
			Scene scene = btnChangePassword.getScene();

			//getting parent Container from Login Window
			StackPane stackPaneParent = (StackPane) scene.getRoot();
			stackPaneParent.getChildren().add(root);

			//setting the position of the scene to appear from left to right(negative Width Value)
			//Otherwise from right to left
			root.translateXProperty().set(-scene.getWidth());

			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.LINEAR);
			KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
			timeline.getKeyFrames().add(keyFrame);
			timeline.setOnFinished(ev -> {
				stackPaneParent.getChildren().remove(anchorPane);
			});
			timeline.play();
		}catch(IOException io){
			System.out.println(io.getMessage());
		}
	}
	
	private void onEnterNewPassword(ActionEvent event) {
		txtConfirmationPassword.requestFocus();
	}
	
	private void onEnterConfirmationPassword(ActionEvent event) {
		changePassword(event);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		txtNewPassword.setOnAction(this::onEnterNewPassword);
		txtConfirmationPassword.setOnAction(this::onEnterConfirmationPassword);
		btnChangePassword.setOnAction(this::changePassword);
	}

}
