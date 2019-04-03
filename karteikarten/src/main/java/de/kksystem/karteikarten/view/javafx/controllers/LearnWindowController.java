package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.interfaces.Lection;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;

public class LearnWindowController implements Initializable {





	@FXML
    private SplitPane splitPane;

    @FXML
    private TextField textFieldQuestion;

    @FXML
    private TextField textFieldAnswer;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnAnswer;

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnCloseWindow;

    @FXML
    void AnswerCheck(ActionEvent event) {

    }

    @FXML
    void BackCheck(ActionEvent event) {

    }

    @FXML
    void CloseWindowCheck(ActionEvent event) {

    }

    @FXML
    void ContinueCheck(ActionEvent event) {

    }
    
    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }


   public void btnAnswer(ActionEvent event){
       UserData.getInstance().getLection().getLectionId();

   }
    

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCloseWindow.setOnAction(this::closeLearnWindow);


    }
}