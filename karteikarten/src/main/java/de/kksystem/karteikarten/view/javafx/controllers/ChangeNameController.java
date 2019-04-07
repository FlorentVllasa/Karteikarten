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

public class ChangeNameController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TextField txtNewForeName;

    @FXML
    private TextField txtNewSurName;

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblMessage;

    @FXML
    public void changeName(ActionEvent event){
        String newForeName = txtNewForeName.getText();
        String newSurName = txtNewSurName.getText();
        int userId = UserData.getInstance().getUserId();
        if(newForeName.isBlank() && newSurName.isBlank()){
            lblMessage.setText("Bitte beide Felder ausfüllen!");
        }else if(newForeName.isBlank()){
            lblMessage.setText("Bitte Vornamen eigneben!");
        }else if(newSurName.isBlank()){
            lblMessage.setText("Bitte Nachnamen eingeben!");
        }else{
            ServiceFacade.getInstance().updateForename(newForeName, userId);
            UserData.getInstance().setForeName(newForeName);
            ServiceFacade.getInstance().updateSurname(newSurName, userId);
            UserData.getInstance().setSurname(newSurName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich!");
            alert.setContentText("Name und Vorname wurden angeändert!");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirm.setOnAction(this::changeName);
    }
}
