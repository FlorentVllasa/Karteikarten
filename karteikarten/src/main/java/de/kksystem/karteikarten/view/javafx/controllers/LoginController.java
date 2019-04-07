package de.kksystem.karteikarten.view.javafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {
    private WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    private WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    private LoginWindow lw = new LoginWindow();

    @FXML
    private StackPane stackPaneParent;
    
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
    private Label lblDateTime;
    
    @FXML
    private void logIn(ActionEvent event) {
    	String username = txtUsername.getText();
    	String password = txtPassword.getText();
    	
    	if(!username.isEmpty() && !password.isEmpty()) {
	    	if(ServiceFacade.getInstance().checkLogIn(username, password)) {
	            wp.createWindowNewStage("/fxml/functionsWindow.fxml", "Funktion wählen!", new FunctionsController());
	            closeLoginWindow();
	    	}else {
	    		lblLogInMessage.setText("Benutzername oder Passwort falsch.");
	    	}
    	}else {
    		lblLogInMessage.setText("Bitte Benutzername und Passwort eingeben.");
    	}
    }
    
    /*Diese Methode wechselt die Scene von der LogIn Scene in die Regristrieren Scene durch RegistrierenButton Klick */
    public void switchToForgotPasswordWindow(ActionEvent event){
        //wpss.createWindowSwitchScene("/fxml/ForgotPassword.fxml", new ForgotPasswordController(), lw.getWindow());
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ForgotPassword.fxml"));
            loader.setController(new ForgotPasswordController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stageInfo = (Stage) buttonForgotPassword.getScene().getWindow();
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }
    
    /*Diese Methode wechselt die Scene von der LogIn Scene in die Regristrieren Scene durch RegistrierenButton Klick */
    public void switchToRegistrationWindow(ActionEvent event){
        //wpss.createWindowSwitchScene("/fxml/registrationWindow.fxml", new RegistrationController(), lw.getWindow());
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/registrationWindow.fxml"));
            loader.setController(new RegistrationController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stageInfo = (Stage) buttonRegister.getScene().getWindow();
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    /*Diese Methode dient dazu das LoginWindow Fenster zu schließen nach erfolgreichem LoginWindow*/
    public void closeLoginWindow(){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }
    
    @FXML
    private void onEnterUsername(ActionEvent event) {
    	txtPassword.requestFocus();
    }
    
    @FXML
    private void onEnterPassword(ActionEvent event) {
    	logIn(event);
    }
    
    private void showTime() {
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	showTime();
        buttonLogin.setOnAction(this::logIn);
        buttonForgotPassword.setOnAction(this::switchToForgotPasswordWindow);
        buttonRegister.setOnAction(this::switchToRegistrationWindow);
        txtUsername.setOnAction(this::onEnterUsername);
        txtPassword.setOnAction(this::onEnterPassword);
    }
}