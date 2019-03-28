package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FunctionsController implements Initializable {
    private WindowPresetSwitchStage wp = new WindowPresetSwitchStage();
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
    private Label lblLoggedInMessage;
    
    @FXML
    private void printUserId(ActionEvent event) {
    	String username = UserData.getInstance().getUsername();
    	int userId = UserData.getInstance().getUserId();

    	System.out.println("Benutzername: " + username + " ID: " + userId);
    }

    @FXML
    public void changeCursorEnterLernenBild(MouseEvent event){
        ivLearn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene sceneInfo = ivLearn.getScene();
                sceneInfo.setCursor(Cursor.HAND);
            }
        });
    }

    public void changeCursorExitLernenBild(MouseEvent event){
        ivLearn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene sceneInfo = ivLearn.getScene();
                sceneInfo.setCursor(Cursor.DEFAULT);
            }
        });
    }

    public void changeCursorEnterErstellenBild(MouseEvent event){
        ivManager.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene sceneInfo = ivManager.getScene();
                sceneInfo.setCursor(Cursor.HAND);
            }
        });
    }

    public void changeCursorExitErstellenBild(MouseEvent event){
    	ivManager.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene sceneInfo = ivManager.getScene();
                sceneInfo.setCursor(Cursor.DEFAULT);
            }
        });
    }

    /*Wenn der auf das Erstellen Bild geklickt wird, öffnet sich das Erstellen Fenster*/
    public void switchSceneToCreateWindow(MouseEvent event){
        wp.createWindowNewStage("/fxml/createWindow.fxml", "Erstellen Fenster!", new CreateWindowController());

    }

    /*Wenn auf das Lernen Bild geklickt wird, öffnet sich das Fenster Kategorie auswählen*/
    public void switchSceneToChooseCategorieWindow(MouseEvent event){
        wp.createWindowNewStage("/fxml/chooseCategory.fxml", "Wähle deine Kategorie und Lektion!", new ChooseCategoryController());
    }

    /*Wenn auf Abmelden Button geklickt wird, schließt sich das Funktionen Fenster und öffnet das Login Fenster*/
    public void switchToPreviousWindowLogin(ActionEvent event){
        Stage stage = new Stage();
        try{
            lw.start(stage);
        }catch(IOException io){
            io.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        //wp.createWindowNewStage("/loginWindow.fxml", "Login Window", new LoginController());
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

    /*Hier werden die anklickbaren Button und Bilder ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	showLblLoggedInMessage();
        //abmeldenButton.setOnAction(this::switchToPreviousWindowLogin);
    	
    	btnLogOut.setOnAction(this::printUserId);
    	btnLogOut.setOnAction(this::switchToPreviousWindowLogin);
        ivManager.setOnMouseClicked(this::switchSceneToCreateWindow);
        ivLearn.setOnMouseClicked(this::switchSceneToChooseCategorieWindow);
        ivLearn.setOnMouseEntered(this::changeCursorEnterLernenBild);
        ivLearn.setOnMouseExited(this::changeCursorExitLernenBild);
        ivManager.setOnMouseEntered(this::changeCursorEnterErstellenBild);
        ivManager.setOnMouseExited(this::changeCursorExitErstellenBild);
    }
    //Farbe  #0B2161
}
