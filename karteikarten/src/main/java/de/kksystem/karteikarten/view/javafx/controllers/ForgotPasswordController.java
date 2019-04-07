package de.kksystem.karteikarten.view.javafx.controllers;

import de.kksystem.karteikarten.facades.ServiceFacade;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchScene;
import de.kksystem.karteikarten.view.javafx.helperclasses.WindowPresetSwitchStage;
import de.kksystem.karteikarten.view.javafx.stages.LoginWindow;

import java.io.IOException;
import java.util.Properties;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import javax.mail.*;
import javax.mail.internet.*;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable{
	
	private final int KEY_LENGTH = 100;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txtFieldEmail;
    
    @FXML
    private TextField txtRecoveryKey;
    
    @FXML
    private Button btnSendEmail;
    
	@FXML
    private Button btnConfirm;
	
	@FXML
    private Button btnBack;
	
	private String generatedKey;
	
	private BooleanProperty keySentStatus = new SimpleBooleanProperty();
	private BooleanProperty keySentCompleted = new SimpleBooleanProperty();
	
	/*Diese Methode springt das Fenster auf das LoginWindow zurueck*/
	private void switchToLoginWindow(ActionEvent event){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/LoginNew.fxml"));
			loader.setController(new LoginController());
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stageInfo = (Stage) btnBack.getScene().getWindow();
			stageInfo.setScene(scene);
		}catch(IOException io){
			System.out.println(io.getMessage());
		}
    }
	
	/*Diese Methode erstellt ein neues Fenster zum Aendern des Passworts, falls die geschickte und eingegebene Schluesseln miteinander stimmen*/
	private void switchToCreateNewPasswordWindow() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/CreateNewPasswordWindow.fxml"));
			loader.setController(new CreateNewPasswordWindowController(txtFieldEmail.getText()));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stageInfo = (Stage) btnConfirm.getScene().getWindow();
			stageInfo.setScene(scene);
		}catch(IOException io){
			System.out.println(io.getMessage());
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
	
	/*Diese Methode schickt eine Wiederherstellungsschluessel falls Benutzer gefunden wurde.*/
	private void sendRecovery(ActionEvent event) {
		
		if (txtFieldEmail.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Achtung!");
    		alert.setHeaderText("Bitte füllen Sie das Emailfeld aus!");
    		alert.showAndWait();
		} else if (ServiceFacade.getInstance().findUserByEMail(txtFieldEmail.getText()) == null) {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText("Wir können Ihre Emailadresse leider nicht finden. Bitte Überprüfen Sie Ihre Emailadresse.");
    		alert.showAndWait();
		} else {
			generatedKey = generateKey();
			
			String firstName = ServiceFacade.getInstance().findUserByEMail(txtFieldEmail.getText()).getForename();
			String lastName = ServiceFacade.getInstance().findUserByEMail(txtFieldEmail.getText()).getSurname();
            
            String msg = 	"<!DOCTYPE html>\r\n" + 
            				"<html>\r\n" + 
            				"<body>\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"<p>Hallo " + firstName + " " + lastName + "!</p>" +
            				"<br>\r\n" +
		            		"<p>Du hast gerade einen Wiederherstellungschlüssel angefordert!</p>\r\n" + 
		            		"<p>Kopier den Schlüssel <b>ins zweite Feld</b> des Wiederherstellungsfensters und auf Bestätigen drucken!</p> \r\n" + 
		            		"<p>Dein Code lautet: <b><i>" + generatedKey + "</i></b></p> \r\n" + 
		            		"<br>\r\n" + 
		            		"<p>Mit freundlichen Grüßen</p>\r\n" + 
		            		"<p>Dein Karteikarten Team</p>\r\n" + 
		            		"</body>\r\n" + 
		            		"</html>\r\n";
            
            sendEmail(txtFieldEmail.getText(), "Wiederherstellen deines Karteikarten-Kontos", msg);
            keySentStatus.set(true);
		}
	}
	
	/*Diese Methode vergleicht den von Benutzer eingegebenen Schüssel und dem zu der Benutzeremail geschickten Schlüssel*/
	private void compareKey(ActionEvent event) {
		if (txtRecoveryKey.getText().equals(generatedKey)) {
			switchToCreateNewPasswordWindow();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText("Leider stimmen der Schlüssel nicht überein!");
    		alert.showAndWait();
		}
	}
	
	/*Diese Methode stellt fest, bei welchen Bedingungen sollen die Objekte deaktiviert werden*/
	private void setDisableProperty() {
		txtFieldEmail.disableProperty().bind(keySentStatus.isEqualTo(keySentCompleted));
		btnSendEmail.disableProperty().bind(txtRecoveryKey.disabledProperty().not());
		txtRecoveryKey.disableProperty().bind(txtFieldEmail.disabledProperty().not());
		btnConfirm.disableProperty().bind(btnSendEmail.disabledProperty().not());
	}
	
	/*Die Methode stellt eine Methode fest, wenn man Enter on EmailFeld drueckt.*/
	private void onEnterEmail(ActionEvent event) {
		sendRecovery(event);
	}
	
	/*Die Methode stellt eine Methode fest, wenn man Enter on RecoveryKeyFeld drueckt.*/
	private void onEnterRecoveryKey(ActionEvent event) {
		compareKey(event);
	}
	
	/*Hier werden die anklickbaren Button ihren jeweiligen Methoden zugewiesen und erforderliche Methode aufgerufen*/
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		keySentStatus.set(false);
		keySentCompleted.set(true);
		setDisableProperty();
		txtFieldEmail.setOnAction(this::onEnterEmail);
		txtRecoveryKey.setOnAction(this::onEnterRecoveryKey);
		btnSendEmail.setOnAction(this::sendRecovery);
		btnConfirm.setOnAction(this::compareKey);
		btnBack.setOnAction(this::switchToLoginWindow);
	}

}