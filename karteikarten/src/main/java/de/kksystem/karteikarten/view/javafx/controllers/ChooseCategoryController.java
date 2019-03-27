package de.kksystem.karteikarten.view.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button btnZurueck;

    @FXML
    private Button btnOk;


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
        btnZurueck.setOnAction(this::closeChooseCategorieWindow);
        btnOk.setOnAction(this::switchToLearnWindow);
    }
}
