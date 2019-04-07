package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FunctionsController implements Initializable {
    private WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    private WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Button btnLogOut;

    @FXML
    private ImageView ivLearn;

    @FXML
    private ImageView ivManager;

    @FXML
    private ImageView ivUserOptions;
    
    @FXML
    private Label lblLoggedInMessage;

    @FXML
    private Label lblDateTime;
    
    @FXML
    private ImageView ivStatistics;
    
    @FXML
    private void printUserId(ActionEvent event) {
    	String username = UserData.getInstance().getUsername();
    	int userId = UserData.getInstance().getUserId();

    	System.out.println("Benutzername: " + username + " ID: " + userId);
    }

    public void switchSceneToUserOptionsWindow(MouseEvent event){
        Stage stageInfo = (Stage) ivUserOptions.getScene().getWindow();
        wpss.createWindowSwitchScene("/fxml/UserOptionsWindow.fxml", new UserOptionsController(), stageInfo);
    }

    public void switchSceneToStatisticsWindow(MouseEvent event) {
    	wp.createWindowNewStage("/fxml/Statistics.fxml", "Statistiken Ihrer Lektionen und Karteikarten", new StatisticsController());
    }

    /*Wenn der auf das Erstellen Bild geklickt wird, öffnet sich das Erstellen Fenster*/
    public void switchSceneToCreateWindow(MouseEvent event){
        CreateWindowController controller = new CreateWindowController();
        wp.createWindowNewStage("/fxml/createWindow.fxml", "Karteimanager", controller);
        UserData.getInstance().setCreateWindowController(controller);
    }

    /*Wenn auf das Lernen Bild geklickt wird, öffnet sich das Fenster Kategorie auswählen*/
    public void switchSceneToChooseCategorieWindow(MouseEvent event){
        wp.createWindowNewStage("/fxml/chooseCategory.fxml", "Wähle deine Kategorie und Lektion", new ChooseCategoryController());
    }

    /*Wenn auf Abmelden Button geklickt wird, schließt sich das Funktionen Fenster und öffnet das Login Fenster*/
    public void switchToPreviousWindowLogin(ActionEvent event){
        wp.createWindowNewStage("/fxml/LoginNew.fxml", "Login Window", new LoginController());
        closeWindowFunktionen();
    }
    /*Diese Methode nimmt sich die Stage Information der Scene und schließt das Fenster daraufhin.
     * Dies wird erreicht in dem die Stage Information irgendeiner Komponente der Scene ermittelt wird und
     * dann in ein Stage Objekt umgewandelt wird.*/
    public void closeWindowFunktionen(){
        Stage stageInfo = (Stage) btnLogOut.getScene().getWindow();
        stageInfo.close();
    }
    
    public void showLblLoggedInMessage() {
    	String username = UserData.getInstance().getUsername();
    	lblLoggedInMessage.setText("Eingeloggt als " + username + ".");
    }

    private void showTime() {
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /*Hier werden die anklickbaren Button und Bilder ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	showTime();
    	showLblLoggedInMessage();
    	btnLogOut.setOnAction(this::switchToPreviousWindowLogin);
        ivManager.setOnMouseClicked(this::switchSceneToCreateWindow);
        ivLearn.setOnMouseClicked(this::switchSceneToChooseCategorieWindow);
        ivUserOptions.setOnMouseClicked(this::switchSceneToUserOptionsWindow);
        ivStatistics.setOnMouseClicked(this::switchSceneToStatisticsWindow);
    }
    //Farbe  #0B2161
}
