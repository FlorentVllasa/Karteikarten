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

public class ChangeEmailController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TextField txtNewEmail;

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblMessage;

    @FXML
    public void changeEmail(ActionEvent event){
        String newEmail = txtNewEmail.getText();
        String currentEmail = UserData.getInstance().getEmail();
        int userId = UserData.getInstance().getUserId();
        if(newEmail.isBlank()){
            lblMessage.setText("Bitte eine neue E-Mail eintragen!");
        }else if(newEmail.equals(currentEmail)){
            lblMessage.setText("Die eingegebene E-Mail wird schon verwendet!");
        }else{
            ServiceFacade.getInstance().updateEmail(newEmail, userId);
            UserData.getInstance().setEmail(newEmail);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich");
            alert.setContentText("Email wurde abgeändert!");
            alert.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(this::changeEmail);
    }
}
