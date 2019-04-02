package de.kksystem.karteikarten.view.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserOptionsController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnChangeUsername;
    @FXML
    private Button btnChangePassword;
    @FXML
    private Button btnChangeEmail;
    @FXML
    private Button btnChangeName;
    @FXML
    private Button btnBack;

    @FXML
    public void switchToChangeUserNameWindow(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/changeUsernameWindow.fxml"));
            loader.setController(new ChangeUsernameController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setResizable(false);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    public void switchToChangePasswordWindow(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/changePasswordWindow.fxml"));
            loader.setController(new ChangePasswordController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setResizable(false);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    public void switchToChangeEmailWindow(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/changeEmailWindow.fxml"));
            loader.setController(new ChangeEmailController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setResizable(false);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    public void switchToChangeNameWindow(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/changeNameWindow.fxml"));
            loader.setController(new ChangeNameController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setResizable(false);
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    public void switchBackToFunctionsWindow(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/functionsWindow.fxml"));
            loader.setController(new FunctionsController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo =  (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnMouseClicked(this::switchBackToFunctionsWindow);
        btnChangeUsername.setOnAction(this::switchToChangeUserNameWindow);
        btnChangePassword.setOnAction(this::switchToChangePasswordWindow);
        btnChangeEmail.setOnAction(this::switchToChangeEmailWindow);
        btnChangeName.setOnAction(this::switchToChangeNameWindow);

    }
}
