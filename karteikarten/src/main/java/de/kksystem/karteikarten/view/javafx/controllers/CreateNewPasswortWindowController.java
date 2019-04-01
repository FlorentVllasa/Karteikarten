package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewPasswortWindowController implements Initializable{
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
		
	CreateNewPasswortWindowController(String emailAdresse){
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
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		btnChangePassword.setOnAction(this::changePassword);
	}

}
