package de.kksystem.karteikarten.view.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    private WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    private WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    private LoginWindow lw = new LoginWindow();
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonForgotPassword;

    @FXML
    private Button buttonRegister;
    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Label lblLogInMessage;
    
    
    @FXML
    private void logIn(ActionEvent event) {
    	String username = txtUsername.getText();
    	String password = txtPassword.getText();
    	
    	if(ServiceFacade.getInstance().checkLogIn(username, password)) {
    		System.out.println("OK");
            wp.createWindowNewStage("/fxml/functionsWindow.fxml", "Funktion wählen!", new FunctionsController());
            closePreviousWindowLogin();
    	}else {
    		lblLogInMessage.setText("Benutzername oder Passwort falsch.");
    	}
    }

    /*Diese Methode wechselt das Fenster vom LogIn Fenster zum Funktionen Fenster durch den Einloggen Button Klick*/
    public void switchTofunctionsWindow(ActionEvent event){
        wp.createWindowNewStage("/fxml/functionsWindow.fxml", "Funktion wählen!", new FunctionsController());
        closePreviousWindowLogin();
    }

    /*Diese Methode wechselt die Scene von der LogIn Scene in die Regristrieren Scene durch RegistrierenButton Klick */
    public void switchToRegistrationWindow(ActionEvent event){
        wpss.createWindowSwitchScene("/fxml/registrationWindow.fxml", new RegistrationController(), lw.getWindow());
    }

    /*Diese Methode dient dazu das LoginWindow Fenster zu schließen nach erfolgreichem LoginWindow*/
    public void closePreviousWindowLogin(){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.setOnAction(this::logIn);
        buttonRegister.setOnAction(this::switchToRegistrationWindow);
    }
}