package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCategoryController implements Initializable {
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();

        @FXML
        private TextField textFieldName;
        
        @FXML
        public TextField gettextFieldName() {
        	return textFieldName;
        }
        
       
        @FXML
        private Button btnConfirm;
        
        @FXML
        public Button getButtonConfirm() {
        	return btnConfirm;
        }
        
        @FXML
        private AnchorPane anchorPane;


        @FXML
        void ConfirmCheck(ActionEvent event) {

        }


        
       
        public void closeCreateNewCategorieWindow(ActionEvent event){
			Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
            stageInfo.close();
        }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			  btnConfirm.setOnAction(this::closeCreateNewCategorieWindow);
		      
		}

	}
