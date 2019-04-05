package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.interfaces.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowUserDataController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private Label lblUserName;

    @FXML
    private PasswordField pwfPassword;

    @FXML
    private TextField txtPasswort;

    @FXML
    private CheckBox cbPassword;

    @FXML
    private Button btnShowPassword;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblSurName;

    @FXML
    private Label lblForeName;


    @FXML
    public void showUserData(){
        pwfPassword.setEditable(false);
        txtPasswort.setEditable(false);
        int userId = UserData.getInstance().getUserId();
        User user = ServiceFacade.getInstance().findUserById(userId);
        String userName = user.getUsername();
        String passWord = user.getPassword();
        String email = user.getEmail();
        String foreName = user.getForename();
        String surName = user.getSurname();
        lblUserName.setText(userName);
        pwfPassword.setText(passWord);
        lblEmail.setText(email);
        lblForeName.setText(foreName);
        lblSurName.setText(surName);

    }

    public void showPassword(){
        txtPasswort.managedProperty().bind(cbPassword.selectedProperty());
        txtPasswort.visibleProperty().bind(cbPassword.selectedProperty());

        pwfPassword.managedProperty().bind(cbPassword.selectedProperty().not());
        pwfPassword.visibleProperty().bind(cbPassword.selectedProperty().not());

        txtPasswort.textProperty().bindBidirectional(pwfPassword.textProperty());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPassword();
        showUserData();
    }
}
