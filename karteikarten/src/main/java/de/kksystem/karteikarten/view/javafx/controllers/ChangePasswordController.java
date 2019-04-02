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
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField pwField;

    @FXML
    private Button btnConfirm;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnBack;

    @FXML
    public void changePassword(ActionEvent event){
        String newPassword = pwField.getText();
        int userId = UserData.getInstance().getUserId();
        if(newPassword.isBlank()){
            lblMessage.setText("Bitte ein neues Passwort eingeben!");
        }else{
            ServiceFacade.getInstance().updatePassword(newPassword, userId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich!");
            alert.setContentText("Password wurde erfolgreich abgeändert!");
            alert.show();
        }
    }

    @FXML
    public void switchToUserOptionsWindow(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/userOptionsWindow.fxml"));
            loader.setController(new UserOptionsController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setResizable(false);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnMouseClicked(this::switchToUserOptionsWindow);
        btnConfirm.setOnAction(this::changePassword);
    }
}
