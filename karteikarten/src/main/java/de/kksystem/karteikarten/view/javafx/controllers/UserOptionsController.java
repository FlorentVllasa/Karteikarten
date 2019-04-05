package de.kksystem.karteikarten.view.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserOptionsController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnChangeUsername;
    @FXML
    private Button btnChangePassword;
    @FXML
    private Button btnChangeEmail;
    @FXML
    private Button btnChangeName;
    @FXML
    private Button btnShowUserData;
    @FXML
    private Button btnBack;

    @FXML
    public void switchToShowUserDataWindow(ActionEvent event){
        loadUi("/fxml/ShowUserDataWindow.fxml", new ShowUserDataController());
    }

    @FXML
    public void switchToChangeUserNameWindow(ActionEvent event){
        loadUi("/fxml/ChangeUserNameWindow.fxml", new ChangeUsernameController());
    }

    @FXML
    public void switchToChangePasswordWindow(ActionEvent event){
        loadUi("/fxml/ChangePasswordWindow.fxml", new ChangePasswordController());
    }

    @FXML
    public void switchToChangeEmailWindow(ActionEvent event){
        loadUi("/fxml/ChangeEmailWindow.fxml", new ChangeEmailController());
    }

    @FXML
    public void switchToChangeNameWindow(ActionEvent event){
        loadUi("/fxml/ChangeNameWindow.fxml", new ChangeNameController());
    }

    @FXML
    public void switchBackToFunctionsWindow(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/functionsWindow.fxml"));
            loader.setController(new FunctionsController());
            Parent root = loader.load();
            Stage stageInfo = (Stage) borderPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    public void loadUi(String path, Object controller){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.setController(controller);
            root = loader.load();
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
        borderPane.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnMouseClicked(this::switchBackToFunctionsWindow);
        btnChangeUsername.setOnAction(this::switchToChangeUserNameWindow);
        btnChangePassword.setOnAction(this::switchToChangePasswordWindow);
        btnChangeEmail.setOnAction(this::switchToChangeEmailWindow);
        btnChangeName.setOnAction(this::switchToChangeNameWindow);
        btnShowUserData.setOnAction(this::switchToShowUserDataWindow);

    }
}
