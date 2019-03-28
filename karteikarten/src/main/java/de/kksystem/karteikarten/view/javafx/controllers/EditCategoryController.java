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


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField textFieldName;
    
    @FXML
    public TextField gettextFieldName() {
    	return textFieldName;
    }

    @FXML
    private ComboBox<String> cmbChooseCat;
    
    @FXML
    public ComboBox<String> getcmbChooseCat() {
    	return cmbChooseCat;
    }

    @FXML
    private Button btnRun;
    
    @FXML
    public Button getbtnRun() {
    	return btnRun;
    }

    @FXML
    void RunCheck(ActionEvent event) {

    }

	public void closeEditCategorieWindow(ActionEvent event){
		Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
		stageInfo.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ButtonAusfuehren.setOnAction(this::closeEditCategorieWindow);

	}

}