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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
	
    private final int KEY_LENGTH = 100;
    
    LoginWindow lw = new LoginWindow();
    WindowPresetSwitchScene wpss = new WindowPresetSwitchScene();
    WindowPresetSwitchStage wp = new WindowPresetSwitchStage();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblKontoErstellen;

    @FXML
    private TextField txtForeName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUserName;
    
    @FXML
    private Button btnSendCode;//
    
    @FXML
    private TextField txtConfirmationCode;//
    
    @FXML
    private Button btnConfirm;
    
    @FXML
    private PasswordField txtPassword;//
    
    @FXML
    private PasswordField txtConfirmPassword;//

    @FXML
    private ImageView registerImage;//

    @FXML
    private Button btnRegister;//

    @FXML
    private Label lblMessage;//

    @FXML
    private Button btnBack;
    
    private String generatedKey;

    public void registerUser(ActionEvent event){
        String surname = txtName.getText();
        String foreName = txtForeName.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmpassword = txtConfirmPassword.getText();

        if(password.isEmpty() || confirmpassword.isEmpty()){
            lblMessage.setText("Bitte alle Felder ausfuellen!");
        }else if(!password.equals(confirmpassword)) {
        	lblMessage.setText("Die Passwörter stimmen nicht überein!");
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

    /*Diese Methode generiert eine Wiederherstellungsschluessel mit der Länge KEY_LENGTH (default = 100, Langeaenderung erfolgt durch Konstantwertaenderung)*/	
	private String generateKey() {
		final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_§$%&/()=#+*";
	    final SecureRandom RANDOM = new SecureRandom();
	    StringBuilder sb = new StringBuilder();
        for (int i = 0; i < KEY_LENGTH; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        //System.out.println(sb.toString());
        return sb.toString();
	}
	
	/*Diese Methode generiert eine Email, die zu einem Benutzer weggeschickt werden muss*/
	private void sendEmail(String receiver, String subject, String msg) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.setProperty("mail.smtp.user", "recovery.karteikarten@gmail.com");
        prop.setProperty("mail.smtp.password", "Lernmolwas!");
        prop.setProperty("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication("recovery.karteikarten@gmail.com","Lernmolwas!");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("recovery.karteikarten@gmail.com", "Karteikarten Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (javax.mail.SendFailedException e) {
        	e.printStackTrace();
        	//System.out.println(e.getInvalidAddresses());
        	//System.out.println(e.getValidUnsentAddresses());
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
    public void sendCode(ActionEvent event) {
    	String surname = txtName.getText();
        String foreName = txtForeName.getText();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        
        if(surname.isEmpty() || foreName.isEmpty() || userName.isEmpty() || email.isEmpty()){
            lblMessage.setText("Bitte alle Felder ausfuellen!");
        }else if(ServiceFacade.getInstance().checkRegister(userName, email) > 0){
            lblMessage.setText("Email wird schon benutzt!");
        }else if(ServiceFacade.getInstance().checkRegister(userName, email) == 0){
            lblMessage.setText("Benutzername wird schon benutzt!");
        }else{
        	generatedKey = generateKey();
        	//System.out.println(generatedKey);
            
            String msg = 	"<!DOCTYPE html>\r\n" + 
            				"<html>\r\n" + 
            				"<body>\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"<p>Hallo " + foreName + " " + surname + "!</p>" +
            				"<br>\r\n" +
		            		"<p>Du hast gerade ein Bestätigungscode angefordert!</p>\r\n" + 
		            		"<p>Kopier das Code <b>ins Bestätigungscode Feld</b> des Anmeldungsfensters und auf Bestätigen drucken!</p> \r\n" + 
		            		"<p>Dein Code lautet: <b><i>" + generatedKey + "</i></b></p> \r\n" + 
		            		"<br>\r\n" + 
		            		"<p>Mit freundlichen Grüßen</p>\r\n" + 
		            		"<p>Dein Karteikarten Team</p>\r\n" + 
		            		"</body>\r\n" + 
		            		"</html>\r\n";
            
            sendEmail(email, "Bestaetigungscode deiner Anmeldung bei Karteikarten", msg);
            txtName.setDisable(true);
            txtForeName.setDisable(true);
            txtUserName.setDisable(true);
            txtEmail.setDisable(true);
            txtConfirmationCode.setDisable(false);
            btnSendCode.setVisible(false);
            btnSendCode.setDisable(true);
            btnConfirm.setVisible(true);
            btnConfirm.setDisable(false);
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

    /*Diese Methode vergleicht den von Benutzer eingegebenen Schüssel und dem zu der Benutzeremail geschickten Schlüssel*/
	private void compareKey(ActionEvent event) {
		if (txtConfirmationCode.getText().equals(generatedKey)) {
			txtConfirmationCode.setVisible(false);
			txtConfirmationCode.setDisable(true);
			btnConfirm.setVisible(false);
			btnConfirm.setDisable(true);
			txtPassword.setVisible(true);
			txtPassword.setDisable(false);
			txtConfirmPassword.setVisible(true);
			txtConfirmPassword.setDisable(false);
			registerImage.setVisible(true);
			btnRegister.setVisible(true);
			btnRegister.setDisable(false);
		} else {
			lblMessage.setText("Leider stimmen der Schlüssel nicht überein!");
		}
	}
    
    /*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	btnSendCode.setOnAction(this::sendCode);
    	btnConfirm.setOnAction(this::compareKey);
        btnRegister.setOnAction(this::registerUser);
        btnBack.setOnAction(this::switchToLoginWindow);
    }
}