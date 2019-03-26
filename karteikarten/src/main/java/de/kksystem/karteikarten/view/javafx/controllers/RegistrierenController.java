package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrierenController implements Initializable {
    LoginWindow lw = new LoginWindow();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();

    @FXML
    private javafx.scene.control.PasswordField PasswordField;

    @FXML
    private TextField textFieldVorname;

    @FXML
    private TextField textfieldName;

    @FXML
    private TextField txtEmail;

    @FXML
    private ImageView registerImage;

    @FXML
    private Button btnNeuAnmelden;

    @FXML
    private Label lblKontoErstellen;

    @FXML
    private Button btnZurueck;

    /*Diese Methode wechselt die von der Registrieren Scene zur LogIn Scene*/
    public void switchToLoginWindow(ActionEvent event){
        wpss.createWindowSwitchScene("/fxml/loginWindow.fxml", new LoginController(), lw.getWindow());
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnZurueck.setOnAction(this::switchToLoginWindow);
    }
}
