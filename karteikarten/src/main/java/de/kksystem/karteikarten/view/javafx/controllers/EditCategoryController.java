package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCategoryController implements Initializable {
	WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
	
	
	    @FXML
	    private TextField textFieldName;
	    
	    @FXML
	    public TextField gettextFieldName() {
	    	return textFieldName;
	    }

	    @FXML
	    private ComboBox<ChooseCategoryController> ComboBoxKategorie;
	    
	    @FXML
	    public ComboBox<ChooseCategoryController> getComboBoxKategorie() {
	    	return ComboBoxKategorie;
	    }

	    @FXML
	    private Button ButtonAusfuehren;
	 
	    @FXML
	    public Button getButtonAusführen () {
	    	return ButtonAusfuehren;
	    }
	    
	    @FXML
	    private AnchorPane anchorPane;

	    @FXML
	    void KategorieCheck(ActionEvent event) {

	    }

	

		public void closeEditCategorieWindow(ActionEvent event){
			Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
		    stageInfo.close();
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			  ButtonAusfuehren.setOnAction(this::closeEditCategorieWindow);
		      
}

}


