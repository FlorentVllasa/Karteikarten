package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeUsernameController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TextField txtNewUsername;

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblMessage;

    @FXML
    public void changeUserName(ActionEvent event){
        String newUsername = txtNewUsername.getText();
        int userId = UserData.getInstance().getUserId();
        String currentUsername = UserData.getInstance().getUsername();
        if(newUsername.isBlank()){
            lblMessage.setText("Bitte neuen Nutzernamen eingeben!");
        }else if(newUsername.equals(currentUsername)){
            lblMessage.setText("Bitte einen neuen Nutzernamen eingeben!");
        }else{
            ServiceFacade.getInstance().updateUsername(newUsername, userId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich");
            alert.setContentText("Benutzername wurde abgeändert.");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(this::changeUserName);
    }
}
