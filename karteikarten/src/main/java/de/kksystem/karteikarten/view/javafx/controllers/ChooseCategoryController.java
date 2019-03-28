package de.kksystem.karteikarten.view.javafx.controllers;

import java.util.List;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.User;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;

public class ChooseCategoryController implements Initializable {
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    public AnchorPane getanchorPane() {
    	return anchorPane;
    }

    @FXML
    private Button btnBack;
    
    @FXML
    public Button getbtnBack() {
    	return btnBack;
    }

    @FXML
    private Button btnConfirm;
    
    @FXML
    public Button getbtnConfirm () {
    	return btnConfirm;
    }
    
    @FXML
    private ComboBox<String> cmbChooseCat;
    
    @FXML
    public ComboBox<String> getcmbChooseCat() {
    	return cmbChooseCat;
    }
    
    @FXML
    private ComboBox<String> cmbChooseLec;
    
    @FXML
    public ComboBox<String> getcmbChooseLec() {
    	return cmbChooseLec;
    }
    
    @FXML
    private ComboBox<String> cmbChooseFav;
    
    @FXML
    public ComboBox<String> cmbChooseFav() {
    	return cmbChooseFav;
    }
    
    
    /*Diese Methode holt die Kategorieliste von der Datenbank*/
    public void getCategoryList() {
    	List<Category> categoryList = ServiceFacade.getInstance().findAllCategories();
    }
    
    /*Diese Methode holt die Lektionliste von der Datenbank*/
    public void getLectionList() {
    	
    }
    
    /*Diese Methode holt die Favoritenliste von der Datenbank*/
    public void getFavoriteList() {
    	
    }

    /*Diese Methode wechselt das Fenster zum Lernen Fenster in dem die zuvor erstellen Karteikarten verwendet werden*/
    public void switchToLearnWindow(ActionEvent event){
        wp.createWindowNewStage("/fxml/learnWindow.fxml", "Lern mol was!", new LearnWindowController());
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeChooseCategorieWindow(ActionEvent event){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::closeChooseCategorieWindow);
        btnConfirm.setOnAction(this::switchToLearnWindow);
    }
}
