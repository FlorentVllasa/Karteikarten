package de.kksystem.karteikarten.view.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KategorieLoeschenController implements Initializable {
	    @FXML
	    private ComboBox<KategorieAuswaehlenController> KategorieLoeschenComboBox;
	    
	    
	    @FXML 
	    public ComboBox<KategorieAuswaehlenController> getKategorieLoeschenComboBox () {
	    	return KategorieLoeschenComboBox;
	    }

	    @FXML
	    private Button ButtonLoeschen;
	    
	    @FXML
	    public Button getButtonLoeschen() {
	    	return ButtonLoeschen;
	    }
	    
	    

	    @FXML
	    void LoeschenCheck(ActionEvent event) {

	    }
	    
	    @FXML
	    private AnchorPane anchorPane;

		public void closeDeleteCategorieWindow(ActionEvent event){
		Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
	    stageInfo.close();
	}
	
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			ButtonLoeschen.setOnAction(this::closeDeleteCategorieWindow);
	      
	}
	
	}


