package de.kksystem.karteikarten.view.javafx.controllers;

import java.util.List;
import java.util.ArrayList;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;

public class ChooseCategoryController implements Initializable {
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;
        
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnConfirm;
    
    @FXML
    private ComboBox<String> cmbChooseCat;
    
    @FXML
    private ComboBox<String> cmbChooseLec;
    
    @FXML
    private ComboBox<String> cmbChooseFav;
    
    private List<Lection> categoryLectionList;
    private List<Lection> favoriteLectionList;
    
    /*Diese Methode holt die Kategorieliste von der Datenbank*/
    public void getCategoryList() {
    	cmbChooseCat.getItems().clear();
    	cmbChooseCat.getItems().add("None");
    	List<Category> categoryList = UserData.getInstance().getCategoryList();
    	for (int i = 0; i < categoryList.size(); i++) {
    		cmbChooseCat.getItems().add(categoryList.get(i).getName());
    	}
    }
    
    /*Diese Methode holt die Lektionliste von der Datenbank*/
    public void getLectionList(int categoryId) {
    	cmbChooseLec.getItems().clear();
    	cmbChooseLec.getItems().add("None");
    	categoryLectionList = ServiceFacade.getInstance().findLectionByCategoryId(categoryId);
    	for (int i = 0; i < categoryLectionList.size(); i++) {
    		cmbChooseLec.getItems().add(categoryLectionList.get(i).getName());
    	}
    }
    
    /*Diese Methode holt die Favoritenliste von der Datenbank*/
    public void getFavoriteList() {
    	cmbChooseFav.getItems().clear();
    	cmbChooseFav.getItems().add("None");
    	favoriteLectionList = ServiceFacade.getInstance().findLectionByFavoritelistId(UserData.getInstance().getFavoriteList().get(0).getFavoritelistId());
    	for (int i = 0; i < favoriteLectionList.size(); i++) {
    		cmbChooseFav.getItems().add(favoriteLectionList.get(i).getName());
    	}
    }

    /*Diese Methode wechselt das Fenster zum Lernen Fenster in dem die zuvor erstellen Karteikarten verwendet werden*/
    public void switchToLearnWindow(ActionEvent event){
    	if ((cmbChooseCat.getSelectionModel().getSelectedIndex() > 0) && (cmbChooseLec.getSelectionModel().getSelectedIndex() > 0)) {
    		UserData.getInstance().setLection(categoryLectionList.get(cmbChooseLec.getSelectionModel().getSelectedIndex()-1));
    		wp.createWindowNewStage("/fxml/learnWindow.fxml", "Lern mol was!", new LearnWindowController());
            Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
            stageInfo.close();
    	} else if (cmbChooseFav.getSelectionModel().getSelectedIndex() > 0) {
    		UserData.getInstance().setLection(favoriteLectionList.get(cmbChooseFav.getSelectionModel().getSelectedIndex()-1));
    		wp.createWindowNewStage("/fxml/learnWindow.fxml", "Lern mol was!", new LearnWindowController());
            Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
            stageInfo.close();
    	} else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Attention");
    		alert.setHeaderText("Please choose a Lection from Category->Lection or your Favoritelist");
    		alert.showAndWait();
    	}
    }

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeChooseCategorieWindow(ActionEvent event){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }
    
    private void cmbChooseCatValueChanged(ActionEvent event) {
    	if (cmbChooseCat.getSelectionModel().getSelectedIndex() == 0) {
    		cmbChooseCat.setDisable(false);
    		cmbChooseLec.setDisable(true);
    		cmbChooseFav.setDisable(false);
    		cmbChooseLec.getItems().clear();
    	} else {
    		cmbChooseCat.setDisable(false);
    		cmbChooseLec.setDisable(false);
    		cmbChooseFav.setDisable(true);
    		int choosenCatId = cmbChooseCat.getSelectionModel().getSelectedIndex();
    		Category choosenCategory = UserData.getInstance().getCategoryList().get(choosenCatId - 1);
    		getLectionList(choosenCategory.getCategoryId());
    	}
    }
    
    private void cmbChooseFavValueChanged(ActionEvent event) {
    	if (cmbChooseFav.getSelectionModel().getSelectedIndex() == 0) {
    		cmbChooseCat.setDisable(false);
    		cmbChooseLec.setDisable(true);
    		cmbChooseFav.setDisable(false);
    		cmbChooseLec.getItems().clear();
    	} else {
    		cmbChooseCat.setDisable(true);
    		cmbChooseLec.setDisable(true);
    		cmbChooseFav.setDisable(false);
    	}
    }

    private void initializeListe() {
        getCategoryList();
        getFavoriteList();
        cmbChooseCat.setDisable(false);
		cmbChooseLec.setDisable(true);
		cmbChooseFav.setDisable(false);
    }
    
    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::closeChooseCategorieWindow);
        btnConfirm.setOnAction(this::switchToLearnWindow);
        cmbChooseCat.setOnAction(this::cmbChooseCatValueChanged);
        cmbChooseFav.setOnAction(this::cmbChooseFavValueChanged);
        initializeListe();
    }
    
}
