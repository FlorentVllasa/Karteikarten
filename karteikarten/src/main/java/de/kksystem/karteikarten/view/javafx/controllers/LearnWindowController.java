package de.kksystem.karteikarten.view.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LearnWindowController implements Initializable {

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField textFieldFrage;

    @FXML
    private TextField textFieldAntwort;

    @FXML
    private Button buttonZuruck;

    @FXML
    private Button buttonAntwort;

    @FXML
    private Button buttonWeiter;

    @FXML
    private Button schlieseFester;

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schlieseFester.setOnAction(this::closeLearnWindow);
    }
}
