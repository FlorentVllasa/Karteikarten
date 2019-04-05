package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.dao.classes.jdbc.LectionDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import java.util.List;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;

public class LearnWindowController implements Initializable {

	@FXML
    private SplitPane splitPane;

    @FXML
    private WebView webFieldQuestion;

    @FXML
    private WebView webFieldAnswer;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnShowAnswer;
    
    @FXML
    private Button btnShowPicture;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnCloseWindow;
    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.LEFT && currentIndex.getValue() > minIndex.getValue()) {
    		prevQuestion(new ActionEvent());
    	}
    	
    	if(event.getCode() == KeyCode.RIGHT && currentIndex.getValue() < maxIndex.getValue()) {
    		nextQuestion(new ActionEvent());
    	}
    	
    	if(event.getCode() == KeyCode.UP) {
    		if (!btnShowAnswer.isDisabled() == true) {
    			showAnswer(new ActionEvent());
    		} else if (!btnShowPicture.isDisabled() == true) {
    			showPicture(new ActionEvent());
    		}
    	}
    }

    private List<IndexCard> cards;
    
    private List<IndexCard> wronganswerCards;

    private IntegerProperty currentIndex;
    private IntegerProperty minIndex;
    private IntegerProperty maxIndex;
    

    private void getCards(){
        int lektionId = UserData.getInstance().getLection().getLectionId();
        cards = ServiceFacade.getInstance().findAllIndexCardsByLectionId(lektionId);
    }
    
    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }
    
    private void showQuestion(int cardIndex) {
    	webFieldQuestion.getEngine().loadContent(cards.get(cardIndex).getQuestion());
    }
    
    private void showAnswer(int cardIndex) {
    	webFieldAnswer.getEngine().loadContent(cards.get(cardIndex).getAnswer());
    	btnShowAnswer.setVisible(false);
		btnShowAnswer.setDisable(true);
    	if (cards.get(currentIndex.getValue()).getPictureId() != 0) {
    		btnShowPicture.setVisible(true);
        	btnShowPicture.setDisable(false);
    	}
    }
    
    private void showPicture(int cardIndex) {
    	System.out.println("picture shown");
    }

    private void showAnswer(ActionEvent event){
    	showAnswer(currentIndex.getValue());
    }
    
    private void showPicture(ActionEvent event) {
    	showPicture(currentIndex.getValue());
    }
    
    private void prevQuestion(ActionEvent event) {
    	currentIndex.set(currentIndex.getValue() - 1);
    	showQuestion(currentIndex.getValue());
    	webFieldAnswer.getEngine().loadContent("");
    	btnShowAnswer.setVisible(true);
		btnShowAnswer.setDisable(false);
    	btnShowPicture.setVisible(false);
    	btnShowPicture.setDisable(true);
    }
    
    private void nextQuestion(ActionEvent event) {
    	currentIndex.set(currentIndex.getValue() + 1);
    	showQuestion(currentIndex.getValue());
    	webFieldAnswer.getEngine().loadContent("");
    	btnShowAnswer.setVisible(true);
		btnShowAnswer.setDisable(false);
    	btnShowPicture.setVisible(false);
    	btnShowPicture.setDisable(true);
    }

    private void setDisableProperty() {
    	btnPrevious.disableProperty().bind(currentIndex.isEqualTo(minIndex));
    	btnNext.disableProperty().bind(currentIndex.isEqualTo(maxIndex));
    }
    
    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCards();
        currentIndex = new SimpleIntegerProperty(this, "currentIndex", 0);
        minIndex = new SimpleIntegerProperty(this, "minIndex", 0);
        maxIndex = new SimpleIntegerProperty(this, "currentIndex", cards.size()-1);
        setDisableProperty();
        
        btnShowAnswer.setOnAction(this::showAnswer);
        btnShowPicture.setOnAction(this::showPicture);
        btnPrevious.setOnAction(this::prevQuestion);
        btnNext.setOnAction(this::nextQuestion);
        btnCloseWindow.setOnAction(this::closeLearnWindow);
        
        showQuestion(currentIndex.getValue());
    }
}