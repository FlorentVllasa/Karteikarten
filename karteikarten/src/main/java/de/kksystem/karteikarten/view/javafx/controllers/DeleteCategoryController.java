package de.kksystem.karteikarten.view.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteCategoryController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ComboBox<String> cmbChooseCat;

	@FXML
	private Button btnDelete;

	@FXML
	void DeleteCheck(ActionEvent event) {

	}

	public void closeDeleteCategorieWindow(ActionEvent event){
		Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
		stageInfo.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//ButtonLoeschen.setOnAction(this::closeDeleteCategorieWindow);
	}
	
}