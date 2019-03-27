package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateWindowController implements Initializable {

    WindowPresetSwitchStage switchStageHelper = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnZurueck;

    @FXML
    private Button btnSpeichern;

    @FXML
    private MenuItem addCategoryMenuItem;

    @FXML
    private MenuItem editCategoryMenuItem;

    @FXML
    private MenuItem deleteCategoryMenuItem;

    public void switchToAddCategoryWindow(ActionEvent event){
        switchStageHelper.createWindowNewStage("/fxml/createCategoryWindow.fxml", "Kategorie erstellen!", new CreateCategoryController());
    }

    public void switchToEditCategoryWindow(ActionEvent event){
        switchStageHelper.createWindowNewStage("/fxml/editCategoryWindow.fxml", "Kategorie bearbeiten!", new EditCategoryController());
    }

    public void switchToDeleteCategoryWindow(ActionEvent event){
        switchStageHelper.createWindowNewStage("/fxml/deleteCategoryWindow.fxml", "Kategorie loeschen!", new DeleteCategoryController());
    }

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeCreateWindow(ActionEvent event){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnZurueck.setOnAction(this::closeCreateWindow);
        addCategoryMenuItem.setOnAction(this::switchToAddCategoryWindow);
        editCategoryMenuItem.setOnAction(this::switchToEditCategoryWindow);
        deleteCategoryMenuItem.setOnAction(this::switchToDeleteCategoryWindow);
    }
}
