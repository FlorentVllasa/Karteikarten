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

public class ChangeEmailController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TextField txtNewEmail;

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblMessage;

    private String newEmail;

    @FXML
    public void changeEmail(ActionEvent event){
        List<String> allUserEmails = ServiceFacade.getInstance().findAllUserEmails();
        newEmail = txtNewEmail.getText();
        String currentEmail = UserData.getInstance().getEmail();
        int userId = UserData.getInstance().getUserId();
        if(newEmail.isBlank()){
            lblMessage.setText("Bitte eine neue E-Mail eintragen!");
        }else if(newEmail.equals(currentEmail)){
            lblMessage.setText("Die eingegebene E-Mail wird schon verwendet!");
        }else if(checkEmail(allUserEmails) > 0){
            lblMessage.setText("Die eingegebene E-Mail wird bereits verwendet!");
        }else{
            ServiceFacade.getInstance().updateEmail(newEmail, userId);
            UserData.getInstance().setEmail(newEmail);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich");
            alert.setContentText("Email wurde abgeändert!");
            alert.show();
        }
    }

    public int checkEmail(List<String> allUserEmails){
        for (int i = 0; i < allUserEmails.size() ; i++) {
            if(newEmail.equals(allUserEmails.get(i))) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(this::changeEmail);
    }
}
