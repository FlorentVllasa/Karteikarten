package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    LoginWindow lw = new LoginWindow();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();

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

    @FXML
    private AnchorPane anchorPane;

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
            
            int userId = ServiceFacade.getInstance().addUser(user);
            
            if(userId > 0) {
            	ServiceFacade.getInstance().addFavoritelist("Wichtig zum Lernen!", userId);

                wp.createWindowNewStage("/fxml/functionsWindow.fxml", "Funktion waehlen!" ,new FunctionsController());
                closeRegisterWindow();
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
        wpss.createWindowSwitchScene("/fxml/LoginNew.fxml", new LoginController(), lw.getWindow());
    }

    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(this::switchToLoginWindow);
        btnRegister.setOnAction(this::registerUser);
    }
}