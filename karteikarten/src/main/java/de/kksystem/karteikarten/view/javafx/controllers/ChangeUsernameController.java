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
import java.security.Provider;
import java.util.List;
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

    private String newUsername;

    @FXML
    public void changeUserName(ActionEvent event){
        List<String> allUserNames = ServiceFacade.getInstance().findAllUserNames();
        newUsername = txtNewUsername.getText();
        int userId = UserData.getInstance().getUserId();
        String currentUsername = UserData.getInstance().getUsername();
        if(newUsername.isBlank()){
            lblMessage.setText("Bitte neuen Nutzernamen eingeben!");
        }else if(newUsername.equals(currentUsername)){
            lblMessage.setText("Bitte einen neuen Nutzernamen eingeben!");
        }else if(checkUserName(allUserNames) > 0){
            lblMessage.setText("Dieser Nutzername existiert bereits!");
        }else{
            ServiceFacade.getInstance().updateUsername(newUsername, userId);
            UserData.getInstance().setUsername(newUsername);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich");
            alert.setContentText("Nutzername wurde abgeändert!");
            alert.show();
        }

    }

    public int checkUserName(List<String> allUserNames){
        for (int i = 0; i < allUserNames.size() ; i++) {
            if(newUsername.equals(allUserNames.get(i))) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(this::changeUserName);
    }
}
