package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FunctionsController implements Initializable {
    private WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
    private WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    private LoginWindow lw = new LoginWindow();

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
    private void printUserId(ActionEvent event) {
    	String username = UserData.getInstance().getUsername();
    	int userId = UserData.getInstance().getUserId();

    	System.out.println("Benutzername: " + username + " ID: " + userId);
    }

    public void switchSceneToUserOptionsWindow(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/userOptionsWindow.fxml"));
            loader.setController(new UserOptionsController());
            Scene scene = new Scene(loader.load());
            Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageInfo.setScene(scene);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    /*Wenn der auf das Erstellen Bild geklickt wird, öffnet sich das Erstellen Fenster*/
    public void switchSceneToCreateWindow(MouseEvent event){
        CreateWindowController controller = new CreateWindowController();
        wp.createWindowNewStage("/fxml/createWindow.fxml", "Erstellen Fenster!", controller);
        UserData.getInstance().setCreateWindowController(controller);
    }

    /*Wenn auf das Lernen Bild geklickt wird, öffnet sich das Fenster Kategorie auswählen*/
    public void switchSceneToChooseCategorieWindow(MouseEvent event){
        wp.createWindowNewStage("/fxml/chooseCategory.fxml", "Wähle deine Kategorie und Lektion!", new ChooseCategoryController());
    }

    /*Wenn auf Abmelden Button geklickt wird, schließt sich das Funktionen Fenster und öffnet das Login Fenster*/
    public void switchToPreviousWindowLogin(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/LoginNew.fxml"));
            fxmlLoader.setController(new LoginController());
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            closeWindowFunktionen();
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
        //wp.createWindowNewStage("/loginWindow.fxml", "Login Window", new LoginController());
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
        //abmeldenButton.setOnAction(this::switchToPreviousWindowLogin);
    	btnLogOut.setOnAction(this::switchToPreviousWindowLogin);
        ivManager.setOnMouseClicked(this::switchSceneToCreateWindow);
        ivLearn.setOnMouseClicked(this::switchSceneToChooseCategorieWindow);
        ivUserOptions.setOnMouseClicked(this::switchSceneToUserOptionsWindow);
    }
    //Farbe  #0B2161
}
