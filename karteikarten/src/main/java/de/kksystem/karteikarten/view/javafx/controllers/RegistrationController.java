package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.data.UserData;
import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    LoginWindow lw = new LoginWindow();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private PasswordField txtPassWord;

    @FXML
    private TextField txtForeName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private ImageView registerImage;

    @FXML
    private Button btnRegister;

    @FXML
    private Label lblKontoErstellen;

    @FXML
    private Button btnBack;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblMessage;

    public void registerUser(ActionEvent event){
        String surname = txtName.getText();
        String foreName = txtForeName.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassWord.getText();

        if(surname.isEmpty() || foreName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()){
            lblMessage.setText("Bitte alle Felder ausfuellen!");
        }else if(ServiceFacade.getInstance().checkRegister(userName, email) > 0){
            lblMessage.setText("Email wird schon benutzt!");
        }else if(ServiceFacade.getInstance().checkRegister(userName, email) == 0){
            lblMessage.setText("Benutzername wird schon benutzt!");
        }else{
            UserImpl user = new UserImpl(userName, email, password, surname, foreName, null);
            
            int newUserId = ServiceFacade.getInstance().addUser(user);
            
            if(newUserId > 0) {
            	UserData.getInstance().setUserId(newUserId);
            	UserData.getInstance().setUsername(user.getUsername());
            	UserData.getInstance().setEmail(email);
            	
            	// Neue Favoritenliste des neuen Nutzers in die Db eingefügt
            	int newFavoritelistId = ServiceFacade.getInstance().addFavoritelist(null, newUserId);
            	
            	if(newFavoritelistId > 0) {
                	// FavoritenlisteID des neuen Nutzers nun in UserData abgespeichert
                	UserData.getInstance().setFavoritelistId(newFavoritelistId);
                	
//                    wp.createWindowNewStage("/fxml/functionsWindow.fxml", "Funktion waehlen!", new FunctionsController());
//                    closeRegisterWindow();
                    try{
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxml/functionsWindow.fxml"));
                        fxmlLoader.setController(new FunctionsController());
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stageInfo = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        stageInfo.setResizable(false);
                        stageInfo.setScene(scene);
                    }catch(IOException io){
                        System.out.println(io.getMessage());
                    }
            	}else {
            		System.out.println("Leider ist etwas schiefgelaufen. Versuchen Sie es später erneut.");
            	}
            }else {
            	System.out.println("Leider ist etwas schiefgelaufen. Versuchen Sie es später erneut.");
            }
            
        }
    }

    public void closeRegisterWindow(){
        Stage stageInfo = (Stage) anchorPane.getScene().getWindow();
        stageInfo.close();
    }

    /*Diese Methode wechselt die von der Registrieren Scene zur LogIn Scene*/
    public void switchToLoginWindow(ActionEvent event){
        //wpss.createWindowSwitchScene("/fxml/LoginNew.fxml", new LoginController(), lw.getWindow());
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/LoginNew.fxml"));
            fxmlLoader.setController(new LoginController());
            Parent root = fxmlLoader.load();
            Scene scene = btnBack.getScene();

            //getting parent Container from Login Window
            StackPane stackPaneParent = (StackPane) scene.getRoot();
            stackPaneParent.getChildren().add(root);

            //setting the position of the scene to appear from left to right(negative Width Value)
            //Otherwise from right to left
            root.translateXProperty().set(-scene.getWidth());

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(ev -> {
                stackPaneParent.getChildren().remove(anchorPane);
            });
            timeline.play();
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::switchToLoginWindow);
        btnRegister.setOnAction(this::registerUser);
    }
}