package de.kksystem.karteikarten.view.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class LearnWindowInformationController implements Initializable {

    @FXML
    private Button btnBack;
    
    @FXML
    private CheckBox cklShowHelpOnStart;

    @FXML
    private AnchorPane anchorPane;

    private void closeHelpWindow(ActionEvent event) {
    	
    }
    
    private void tickShowHelp(ActionEvent event) {
    	
    }
    
    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	btnBack.setOnAction(this::closeHelpWindow);
    	cklShowHelpOnStart.setOnAction(this::tickShowHelp);
    }
}
