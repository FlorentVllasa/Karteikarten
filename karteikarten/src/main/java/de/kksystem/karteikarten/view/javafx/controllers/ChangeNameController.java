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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeNameController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField txtNewForeName;

    @FXML
    private TextField txtNewSurName;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnBack;

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
            ServiceFacade.getInstance().updateSurname(newSurName, userId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Erfolgreich!");
            alert.setContentText("Name und Vorname wurden angeändert!");
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
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnMouseClicked(this::switchToUserOptionsWindow);
        btnConfirm.setOnAction(this::changeName);
    }
}
