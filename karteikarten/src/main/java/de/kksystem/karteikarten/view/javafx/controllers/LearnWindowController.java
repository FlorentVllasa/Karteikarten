package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.dao.classes.jdbc.LectionDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class LearnWindowController implements Initializable {

	@FXML
    private SplitPane splitPane;
	
	@FXML
	private Button btnShuffle;
	
	@FXML
	private Label lblTimer;

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
    private Button btnTrue;
    
    @FXML
    private Button btnFalse;

    @FXML
    private Button btnCloseWindow;
    
    private BooleanProperty lockedForReview = new SimpleBooleanProperty(false);
    private final BooleanProperty allowNewCard = new SimpleBooleanProperty(true);
    
    @FXML
    private void handleKeyPressed(KeyEvent event) {
    	if (!lockedForReview.getValue()) {
	    	if(event.getCode() == KeyCode.LEFT && currentIndex.getValue() > minIndex.getValue()) {
	    		prevQuestion(new ActionEvent());
	    	}
	    	
	    	if(event.getCode() == KeyCode.RIGHT && currentIndex.getValue() < maxIndex.getValue()) {
	    		nextQuestion(new ActionEvent());
	    	}
	    	
	    	if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.SPACE) {
	    		if (!btnShowAnswer.isDisabled() == true) {
	    			showAnswer(new ActionEvent());
	    		} else if (!btnShowPicture.isDisabled() == true) {
	    			showPicture(new ActionEvent());
	    		}
	    	}
    	}
    }
    
    @FXML
    private Button btnTimerOn;
    
    @FXML
    private Button btnTimerOff;
    
    @FXML
    private ComboBox<String> cmbTimerList;
    
    private List<IndexCard> cards;
    private List<IndexCard> wronganswerCards = new ArrayList<IndexCard>();

    private IntegerProperty currentIndex;
    private IntegerProperty minIndex;
    private IntegerProperty maxIndex;
    
    private int duration = 0;
    
	Timeline timer = new Timeline();

    private void getCards(){
        cards = UserData.getInstance().getLection();
    }
    
    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeLearnWindow(ActionEvent event){
        Stage stageInfo = (Stage) splitPane.getScene().getWindow();
        stageInfo.close();
    }

    private int seconds;
    
    private void showTimer(int duration) {
    	seconds = duration;
    	lblTimer.setTextFill(Color.GREEN);
    	timer.getKeyFrames().clear();
    	timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
    		seconds--;
    		int minute = seconds / 60;
    		int second = seconds % 60;
    		if (minute == 0 && second == 9) {
    			lblTimer.setTextFill(Color.RED);
    		}
    		lblTimer.setText(minute + ":" + second);
	    }));
    	timer.setCycleCount(duration);
    	timer.setOnFinished(this::showAnswer);
	    timer.play();
    }
    
    private void timerOff(ActionEvent event) {
    	if (cmbTimerList.getSelectionModel().getSelectedIndex() > 0) {
    		btnTimerOff.setDisable(true);
        	btnTimerOff.setVisible(false);
        	btnTimerOn.setDisable(false);
        	btnTimerOn.setVisible(true);
        	cmbTimerList.setDisable(true);
        	duration = cmbTimerList.getSelectionModel().getSelectedIndex() * 60;
    		showTimer(duration);
    	}
    }
    
    private void timerOn(ActionEvent event) {
    	btnTimerOff.setDisable(false);
    	btnTimerOff.setVisible(true);
    	btnTimerOn.setDisable(true);
    	btnTimerOn.setVisible(false);
    	cmbTimerList.setDisable(false);
    	timer.stop();
    	lblTimer.setText("");
    	duration = 0;
    }
    
    private void shuffleCards(ActionEvent event) {
    	Collections.shuffle(cards);
    	currentIndex.set(-1);;
    	nextQuestion(event);
    }
    
    private void showAnswer(int cardIndex) {
    	webFieldAnswer.getEngine().loadContent(cards.get(cardIndex).getAnswer());
    	btnShowAnswer.setVisible(false);
		btnShowAnswer.setDisable(true);
    	btnTrue.setDisable(false);
    	btnFalse.setDisable(false);
    	if (cards.get(currentIndex.getValue()).getPictureId() != 0) {
    		btnShowPicture.setVisible(true);
        	btnShowPicture.setDisable(false);
    	}
    }
    
    private void showAnswer(ActionEvent event){
    	lockedForReview.setValue(true); 
    	btnTimerOn.setDisable(true);
    	btnTimerOff.setDisable(true);
    	showAnswer(currentIndex.getValue());
    	timer.stop();
    }

    private void showQuestion(int cardIndex) {
    	if (duration != 0) {
    		showTimer(duration);
    	}
    	webFieldQuestion.getEngine().loadContent(cards.get(cardIndex).getQuestion());
    }
    
    private void prevQuestion(ActionEvent event) {
    	timer.stop();
    	currentIndex.set(currentIndex.getValue() - 1);
    	showQuestion(currentIndex.getValue());
    	webFieldAnswer.getEngine().loadContent("");
    	btnShowAnswer.setVisible(true);
		btnShowAnswer.setDisable(false);
    	btnShowPicture.setVisible(false);
    	btnShowPicture.setDisable(true);
    }
    
    private void nextQuestion(ActionEvent event) {
    	timer.stop();
    	currentIndex.set(currentIndex.getValue() + 1);
    	showQuestion(currentIndex.getValue());
    	webFieldAnswer.getEngine().loadContent("");
    	btnShowAnswer.setVisible(true);
		btnShowAnswer.setDisable(false);
    	btnShowPicture.setVisible(false);
    	btnShowPicture.setDisable(true);
    }

    private void showPicture(int cardIndex) {
    	btnShowPicture.setVisible(false);
    	btnShowPicture.setDisable(true);
    	Picture picture = ServiceFacade.getInstance().findPicture(cards.get(cardIndex).getPictureId());
    	createPopUpImage(picture.getFileLocation());
    }

    private void showPicture(ActionEvent event) {
    	showPicture(currentIndex.getValue());
    }

    private void createPopUpImage(String imageLocation) {
    	File imageFile = new File(imageLocation);
    	Image image = new Image(imageFile.toURI().toString());
    	ImageView imageView = new ImageView(image);
    	
    	BorderPane pane = new BorderPane();
        pane.setCenter(imageView);
        Scene scene = new Scene(pane);
        
        Stage stage = new Stage();
        stage.setTitle(imageLocation);
        stage.setScene(scene);
        stage.setOnCloseRequest(
            e -> {
                e.consume();
                btnShowPicture.setVisible(true);
            	btnShowPicture.setDisable(false);
                stage.close();
            }
        );
        stage.showAndWait();
    }
    
    private void answerTrue(ActionEvent event) {
    	lockedForReview.setValue(false);
    	btnTrue.setDisable(true);
    	btnFalse.setDisable(true);
    	if (duration > 0) {
    		btnTimerOn.setDisable(false);
    	} else {
    		btnTimerOff.setDisable(false);
    	}
    	if (!btnNext.isDisabled())
    		nextQuestion(event);
    	if (currentIndex.getValue() == maxIndex.getValue()){
    		if (wronganswerCards.size() > 0) {
	    		int learnChoice = JOptionPane.showConfirmDialog(null, "Willst du von deiner Fehlern nochmal lernen?", "Konfirm", JOptionPane.YES_NO_OPTION);
	    		if (learnChoice == 0) {
	    			UserData.getInstance().setLection(wronganswerCards);
	    			WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
	    			wp.createWindowNewStage("/fxml/learnWindow.fxml", "Lern mol was!", new LearnWindowController());
	    			Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	    	        stageInfo.close();
	    		} else {
	    		    System.out.print("no");
	    		    int quitChoice = JOptionPane.showConfirmDialog(null, "Willst du das Lernen beenden?", "Konfirm", JOptionPane.YES_NO_OPTION);
	    		    if (quitChoice == 0) {
	    			    Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	       	         	stageInfo.close();
	    		    }
	    		}
	    	} else {
	    		int quitChoice = JOptionPane.showConfirmDialog(null, "Du bist jetzt Profi! Willst du dein Lernen beenden?", "Konfirm", JOptionPane.YES_NO_OPTION);
	 		    if (quitChoice == 0) {
	 			    Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	    	        	stageInfo.close();
	 		    }
	    	}
    	}
    }
    
    private void answerFalse(ActionEvent event) {
    	if (!wronganswerCards.contains(cards.get(currentIndex.getValue())))
    		wronganswerCards.add(cards.get(currentIndex.getValue()));
    	lockedForReview.setValue(false);
    	btnTrue.setDisable(true);
    	btnFalse.setDisable(true);
    	if (duration > 0) {
    		btnTimerOn.setDisable(false);
    	} else {
    		btnTimerOff.setDisable(false);
    	}
    	if (!btnNext.isDisabled())
    		nextQuestion(event);
    	if (currentIndex.getValue() == maxIndex.getValue()) {
	    	if (wronganswerCards.size() > 0) {
	    		int learnChoice = JOptionPane.showConfirmDialog(null, "Willst du von deiner Fehlern nochmal lernen?", "Konfirm", JOptionPane.YES_NO_OPTION);
	    		if (learnChoice == 0) {
	    			UserData.getInstance().setLection(wronganswerCards);
	    			WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
	    			wp.createWindowNewStage("/fxml/learnWindow.fxml", "Lern mol was!", new LearnWindowController());
	    			Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	    	        stageInfo.close();
	    		} else {
	    		    System.out.print("no");
	    		    int quitChoice = JOptionPane.showConfirmDialog(null, "Du bist noch kein Profi. :( Willst du dein Lernen trotzdem beenden?", "Konfirm", JOptionPane.YES_NO_OPTION);
	    		    if (quitChoice == 0) {
	    			    Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	       	         	stageInfo.close();
	    		    }
	    		}
	    	} else {
	    		int quitChoice = JOptionPane.showConfirmDialog(null, "Du bist jetzt Profi! Willst du dein Lernen beenden?", "Konfirm", JOptionPane.YES_NO_OPTION);
	 		    if (quitChoice == 0) {
	 			    Stage stageInfo = (Stage) splitPane.getScene().getWindow();
	    	        	stageInfo.close();
	 		    }
	    	}
    	}
    }

    private void setDisableProperty() {
    	btnPrevious.disableProperty().bind(Bindings.or(currentIndex.isEqualTo(minIndex),lockedForReview.isEqualTo(allowNewCard)));
    	btnNext.disableProperty().bind(Bindings.or(currentIndex.isEqualTo(maxIndex),lockedForReview.isEqualTo(allowNewCard)));
    }
    
    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCards();
        
        cmbTimerList.getItems().addAll("Off" , "1 min" , "2 min" , "3 min" , "4 min" , "5 min" , "6 min" , "7 min" , "8 min" , "9 min");
        
        currentIndex = new SimpleIntegerProperty(this, "currentIndex", 0);
        minIndex = new SimpleIntegerProperty(this, "minIndex", 0);
        maxIndex = new SimpleIntegerProperty(this, "maxIndex", cards.size()-1);
        
        setDisableProperty();
        
        btnShuffle.setOnAction(this::shuffleCards);
        
        btnShowAnswer.setOnAction(this::showAnswer);
        btnShowPicture.setOnAction(this::showPicture);
        
        btnPrevious.setOnAction(this::prevQuestion);
        btnNext.setOnAction(this::nextQuestion);
        
        btnCloseWindow.setOnAction(this::closeLearnWindow);
        
        btnTimerOff.setOnAction(this::timerOff);
        btnTimerOn.setOnAction(this::timerOn);
        
        btnTrue.setOnAction(this::answerTrue);
        btnFalse.setOnAction(this::answerFalse);
        
        showQuestion(currentIndex.getValue());
    }
}