package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.interfaces.IndexCard;

import java.sql.ResultSet;
import java.util.List;
import java.lang.String;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.sqlite.JDBC;

import java.net.URL;
import java.util.ResourceBundle;

public class LearnWindowController implements Initializable {

    ObservableList<Lection> list = FXCollections.observableArrayList();

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
    private Object Lection;

    private List<IndexCard> cards;

    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }

    private void btnAnswer(ActionEvent event){
        UserData.getInstance().getLection().getLectionId();

    }

    private void getCards(){
        int lektionId = UserData.getInstance().getLection().getLectionId();
        cards = ServiceFacade.getInstance().findAllIndexCardsByLectionId(lektionId);


    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCards();
        btnCloseWindow.setOnAction(this::closeLearnWindow);
        initTextField();


        btnAnswer();


    }

    private void btnAnswer() {
        UserData handler = new UserData();
        String ho = "SELECT LektionID FROM Lektion";
        ResultSet rs;
        rs = handler.setLection(ho);
        try{
          while(rs.next()){
              String answer = rs.getString("answer");
                list.add(new Lection(answer));
            }
        } catch (Exception ex){

        }
    }

    private void initTextField() {
        //textFieldAnswer.(new PropertyValueFactory<Lection, String>("answer"));

    }

    public static class Lection{
        private final SimpleStringProperty answer;

         Lection(String answer){
             this.answer = new SimpleStringProperty(answer);
         }

         public String getAnswer(){
             return answer.get();
         }
    }
}